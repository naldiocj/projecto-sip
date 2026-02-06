package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.TipoCrimeDTO;
import ao.gov.sic.sip.entities.TipoCrime;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.TipoCrimeMapper;
import ao.gov.sic.sip.records.FileRecord;
import ao.gov.sic.sip.records.TipoCrimeCSVRecord;
import ao.gov.sic.sip.repositories.TipoCrimeRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.StorageFileService;
import ao.gov.sic.sip.services.TipoCrimeCSVService;
import ao.gov.sic.sip.services.TipoCrimeService;
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
public class TipoCrimeServiceImpl implements TipoCrimeService {
    private final TipoCrimeRepository tipoCrimeRepository;
    private final TipoCrimeMapper tipoCrimeMapper;
    private final UserRepository userRepository;
    private final TipoCrimeCSVService tipoCrimeCSVService;
    private final StorageFileService storageFileService;
    private final UserService userService;

    @Override
    public Response<TipoCrimeDTO> getById(Long id) {
        TipoCrime tipoCrime = tipoCrimeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de crime não encontrado"));

        TipoCrimeDTO tipoCrimeDTO = tipoCrimeMapper.tipoCrimeToTipoCrimeDTO(tipoCrime);

        return Response.<TipoCrimeDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Tipo de crime encontrado com sucesso")
                .data(tipoCrimeDTO)
                .build();
    }

    @Override
    public Response<?> create(TipoCrimeDTO dto) {
        TipoCrime founded = tipoCrimeRepository.findAllByArtigoPenal(dto.getArtigoPenal());
        if (founded != null) {
            throw new RuntimeException("Tipo de crime já existe");
        }

        TipoCrime tipoCrime = tipoCrimeMapper.tipoCrimeDTOToTipoCrime(dto);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            tipoCrime.setUser(user);
        }

        tipoCrimeRepository.save(tipoCrime);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Tipo de crime criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!tipoCrimeRepository.existsById(id)) {
            throw new NotFoundException("Tipo de crime não encontrado");
        }
        tipoCrimeRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Tipo de crime eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> bulkTipoCrimesByCsv(MultipartFile csvFile) {
        try {
            FileRecord fileRecord = storageFileService.save(csvFile);

            List<TipoCrimeCSVRecord> csvRecords = tipoCrimeCSVService
                    .convertCSV(fileRecord.getFile());

            AtomicReference<Integer> atomicTotalReference = new AtomicReference<>(0);

            for (TipoCrimeCSVRecord record : csvRecords) {
                if (record != null) {

                    TipoCrime founded = tipoCrimeRepository.findAllByArtigoPenal(record.getArtigoPenal());
                    User currentUser = userService.currentUser();

                    if (founded == null) {
                        TipoCrime tipoCrime = new TipoCrime();
                        tipoCrime.setArtigoPenal(record.getArtigoPenal());
                        tipoCrime.setDescricao(record.getDescricao());
                        tipoCrime.setUser(currentUser);
                        tipoCrimeRepository.save(tipoCrime);
                        atomicTotalReference.set(atomicTotalReference.get() + 1);
                    }
                }
            }

            storageFileService.remove(fileRecord.getFileName());


            return Response.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(atomicTotalReference.get() == 0 ?
                            "Nenhum tipo de crime foi adicionado, verifique o ficheiro CSV" :
                            "Foram adicionados " + atomicTotalReference.get() + " tipos de crimes")
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("e: ", e);
            throw new RuntimeException("Erro ao processar o ficheiro CSV");
        }
    }

    @Override
    public Response<?> updateById(TipoCrimeDTO dto, Long id) {
        tipoCrimeRepository.findById(id).ifPresentOrElse(tipoCrime -> {
            if (StringUtils.hasText(dto.getArtigoPenal())) {
                tipoCrime.setArtigoPenal(dto.getArtigoPenal());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                tipoCrime.setDescricao(dto.getDescricao());
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                tipoCrime.setUser(user);
            }
            tipoCrimeRepository.save(tipoCrime);
        }, () -> {
            throw new NotFoundException("Tipo de crime não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Tipo de crime actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<TipoCrimeDTO>> getAll() {
        List<TipoCrimeDTO> tipoCrimes = tipoCrimeRepository.findAll()
                .stream().map(tipoCrimeMapper::tipoCrimeToTipoCrimeDTO)
                .toList();

        return Response.<List<TipoCrimeDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(tipoCrimes.isEmpty() ? "Nenhum tipo de crime encontrado" : "Tipos de crimes encontrados com sucesso")
                .data(tipoCrimes)
                .build();
    }
}
