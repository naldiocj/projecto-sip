package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.*;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.DirecaoMapper;
import ao.gov.sic.sip.mappers.InstrutorMapper;
import ao.gov.sic.sip.records.FileRecord;
import ao.gov.sic.sip.records.InstrutorCSVRecord;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.InstrutorCSVService;
import ao.gov.sic.sip.services.InstrutorService;
import ao.gov.sic.sip.services.StorageFileService;
import ao.gov.sic.sip.services.UserService;
import ao.gov.sic.sip.utils.ProcessoSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstrutorServiceImpl implements InstrutorService {
    private final InstrutorRepository instrutorRepository;
    private final InstrutorMapper instrutorMapper;
    private final PatenteRepository patenteRepository;
    private final CargoRepository cargoRepository;
    private final DirecaoRepository direcaoRepository;
    private final UserRepository userRepository;
    private final InstrutorCSVService instrutorCSVService;
    private final StorageFileService storageFileService;
    private final DirecaoMapper direcaoMapper;
    private final UserService userService;
    private final SecretariaRepository secretariaRepository;

    @Override
    public Response<InstrutorDTO> getById(Long id) {
        Instrutor instrutor = instrutorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Instrutor não encontrado"));

        InstrutorDTO instrutorDTO = instrutorMapper.instrutorToInstrutorDTO(instrutor);

        return Response.<InstrutorDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Instrutor encontrado com sucesso")
                .data(instrutorDTO)
                .build();
    }

    @Override
    public Response<?> create(InstrutorDTO dto) {
        Instrutor founded = instrutorRepository.findByNomeCompletoIgnoreCase(dto.getNomeCompleto());
        if (founded != null) {
            throw new RuntimeException("Instrutor já existe");
        }

        Instrutor instrutor = instrutorMapper.instrutorDTOToInstrutor(dto);

        if (dto.getPatenteId() != null) {
            Patente patente = patenteRepository.findById(dto.getPatenteId())
                    .orElseThrow(() -> new NotFoundException("Patente não encontrada"));
            instrutor.setPatente(patente);
        }

        if (dto.getCargoId() != null) {
            Cargo cargo = cargoRepository.findById(dto.getCargoId())
                    .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));
            instrutor.setCargo(cargo);
        }

        if (dto.getDirecaoId() != null) {
            Direcao direcao = direcaoRepository.findById(dto.getDirecaoId())
                    .orElseThrow(() -> new NotFoundException("Direção não encontrada"));
            instrutor.setDirecao(direcao);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            instrutor.setUser(user);
        }

        instrutorRepository.save(instrutor);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Instrutor criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!instrutorRepository.existsById(id)) {
            throw new NotFoundException("Instrutor não encontrado");
        }
        instrutorRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Instrutor eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> bulkInstrutoresByCsv(MultipartFile csvFile) {
        try {
            FileRecord fileRecord = storageFileService.save(csvFile);

            List<InstrutorCSVRecord> csvRecords = instrutorCSVService
                    .convertCSV(fileRecord.getFile());

            AtomicReference<Integer> atomicTotalReference = new AtomicReference<>(0);

            for (InstrutorCSVRecord record : csvRecords) {
                if (record != null) {

                    Instrutor founded = instrutorRepository.findByNomeCompletoIgnoreCase(record.getNomeCompleto());

                    if (founded == null) {
                        Instrutor instrutor = new Instrutor();
                        instrutor.setNomeCompleto(record.getNomeCompleto());
                        instrutorRepository.save(instrutor);
                        atomicTotalReference.set(atomicTotalReference.get() + 1);
                    }
                }
            }

            storageFileService.remove(fileRecord.getFileName());


            return Response.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(atomicTotalReference.get() == 0 ?
                            "Nenhum instrutor foi adicionado, verifique o ficheiro CSV" :
                            "Foram adicionados " + atomicTotalReference.get() + " instrutores")
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("e: ", e);
            throw new RuntimeException("Erro ao processar o ficheiro CSV");
        }
    }

    @Override
    public Response<?> updateById(InstrutorDTO dto, Long id) {
        instrutorRepository.findById(id).ifPresentOrElse(instrutor -> {
            if (StringUtils.hasText(dto.getNomeCompleto())) {
                instrutor.setNomeCompleto(dto.getNomeCompleto());
            }
            if (dto.getPatenteId() != null) {
                Patente patente = patenteRepository.findById(dto.getPatenteId())
                        .orElseThrow(() -> new NotFoundException("Patente não encontrada"));
                instrutor.setPatente(patente);
            }
            if (dto.getCargoId() != null) {
                Cargo cargo = cargoRepository.findById(dto.getCargoId())
                        .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));
                instrutor.setCargo(cargo);
            }
            if (dto.getDirecaoId() != null) {
                Direcao direcao = direcaoRepository.findById(dto.getDirecaoId())
                        .orElseThrow(() -> new NotFoundException("Direção não encontrada"));
                instrutor.setDirecao(direcao);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                instrutor.setUser(user);
            }
            instrutorRepository.save(instrutor);
        }, () -> {
            throw new NotFoundException("Instrutor não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Instrutor actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<InstrutorItemDTO>> getAll() {
        User user = userService.currentUser();

        // 1. Build the specification based on the term
        //Specification<Processo> spec = ProcessoSpecifications.hasTerm(term);

        // 2. Fetch filtered results directly from DB
        List<InstrutorItemDTO> instrutores = new ArrayList<>();

        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        boolean isSecretaria = user.getRoles().stream().anyMatch(role -> role.getName().equals("SECRETARIA"));
        boolean isSecretariaGeral = user.getRoles().stream().anyMatch(role -> role.getName().equals("SECRETARIA_GERAL"));
        boolean isDirector = user.getRoles().stream().anyMatch(role -> role.getName().equals("DIRECTOR"));


        if (isSecretariaGeral) {
            instrutores = instrutorRepository.findAll()
                    .stream()
                    .map(instrutorMapper::instrutorToInstrutorItemDTO)
                    .toList();
        } else if (isSecretaria || isDirector) {
            Secretaria secretaria = secretariaRepository.findAll().stream()
                    .filter(s ->
                            s.getUser().getId().equals(user.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Secretaria não encontrada"));

            instrutores = instrutorRepository.findAll()
                    .stream()
                    .filter(i -> i.getDirecao().getId().equals(secretaria.getDirecao().getId()))
                    .map(instrutorMapper::instrutorToInstrutorItemDTO)
                    .toList();
        } else if (isAdmin) {
            instrutores = instrutorRepository.findAll()
                    .stream().map(instrutorMapper::instrutorToInstrutorItemDTO)
                    .toList();
        }


        return Response.<List<InstrutorItemDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(instrutores.isEmpty() ? "Nenhum instrutor encontrado" : "Instrutores encontrados com sucesso")
                .data(instrutores)
                .build();
    }

    @Override
    public Response<List<InstrutorDetailDTO>> getAllPerDireccao(Long direccaoId) {
        List<InstrutorDetailDTO> instrutores = instrutorRepository.findAll()
                .stream()
                .filter(instrutor -> instrutor.getDirecao().getId().equals(direccaoId))
                .map(instrutor -> InstrutorDetailDTO.builder()
                        .createdAt(LocalDateTime.now())
                        .id(instrutor.getId())
                        .nomeCompleto(instrutor.getNomeCompleto())
                        .direcao(direcaoMapper.direcaoToDirecaoDTO(instrutor.getDirecao()))
                        .patente(instrutor.getPatente().getNome())
                        .cargo(instrutor.getCargo() != null ? instrutor.getCargo().getNome() : null)
                        .build())
                .toList();

        return Response.<List<InstrutorDetailDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(instrutores.isEmpty() ? "Nenhum instrutor encontrado" : "Instrutores encontrados com sucesso")
                .data(instrutores)
                .build();
    }
}
