package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.MagistradoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Magistrado;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.MagistradoMapper;
import ao.gov.sic.sip.records.FileRecord;
import ao.gov.sic.sip.records.MagistradoCSVRecord;
import ao.gov.sic.sip.repositories.MagistradoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.MagistradoCSVService;
import ao.gov.sic.sip.services.MagistradoService;
import ao.gov.sic.sip.services.StorageFileService;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class MagistradoServiceImpl implements MagistradoService {
    private final MagistradoRepository magistradoRepository;
    private final MagistradoMapper magistradoMapper;
    private final UserRepository userRepository;
    private final MagistradoCSVService magistradoCSVService;
    private final StorageFileService storageFileService;
    private final UserService userService;

    @Override
    public Response<MagistradoDTO> getById(Long id) {
        Magistrado magistrado = magistradoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Magistrado não encontrado"));

        MagistradoDTO magistradoDTO = magistradoMapper.magistradoToMagistradoDTO(magistrado);

        return Response.<MagistradoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Magistrado encontrado com sucesso")
                .data(magistradoDTO)
                .build();
    }

    @Override
    public Response<?> create(MagistradoDTO dto) {
        Magistrado founded = magistradoRepository.findByNomeIgnoreCase(dto.getNomeCompleto());
        if (founded != null) {
            throw new RuntimeException("Magistrado já existe");
        }

        Magistrado magistrado = magistradoMapper.magistradoDTOToMagistrado(dto);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            magistrado.setUser(user);
        }

        magistradoRepository.save(magistrado);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Magistrado criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!magistradoRepository.existsById(id)) {
            throw new NotFoundException("Magistrado não encontrado");
        }
        magistradoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Magistrado eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> bulkMagistradosByCsv(MultipartFile csvFile) {
        try {
            FileRecord fileRecord = storageFileService.save(csvFile);

            List<MagistradoCSVRecord> csvRecords = magistradoCSVService
                    .convertCSV(fileRecord.getFile());

            User currentUser = userService.currentUser();

            AtomicReference<Integer> atomicTotalReference = new AtomicReference<>(0);

            for (MagistradoCSVRecord record : csvRecords) {
                if (record != null) {

                    Magistrado founded = magistradoRepository.findByNomeIgnoreCase(record.getNome());

                    if (founded == null) {
                        Magistrado magistrado = new Magistrado();
                        magistrado.setNomeCompleto(record.getNome());
                        magistrado.setUser(currentUser);
                        magistradoRepository.save(magistrado);
                        atomicTotalReference.set(atomicTotalReference.get() + 1);
                    }
                }
            }

            storageFileService.remove(fileRecord.getFileName());


            return Response.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(atomicTotalReference.get() == 0 ?
                            "Nenhum magistrado foi adicionado, verifique o ficheiro CSV" :
                            "Foram adicionados " + atomicTotalReference.get() + " magistrados")
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("e: ", e);
            throw new RuntimeException("Erro ao processar o ficheiro CSV");
        }
    }

    @Override
    public Response<?> updateById(MagistradoDTO dto, Long id) {
        magistradoRepository.findById(id).ifPresentOrElse(magistrado -> {
            if (StringUtils.hasText(dto.getNomeCompleto())) {
                magistrado.setNomeCompleto(dto.getNomeCompleto());
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                magistrado.setUser(user);
            }
            magistradoRepository.save(magistrado);
        }, () -> {
            throw new NotFoundException("Magistrado não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Magistrado actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<MagistradoDTO>> getAll() {
        List<MagistradoDTO> magistrados = magistradoRepository.findAll()
                .stream().map(magistradoMapper::magistradoToMagistradoDTO)
                .toList();

        return Response.<List<MagistradoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(magistrados.isEmpty() ? "Nenhum magistrado encontrado" : "Magistrados encontrados com sucesso")
                .data(magistrados)
                .build();
    }
}
