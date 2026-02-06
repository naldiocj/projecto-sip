package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.DirecaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Direcao;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.DirecaoMapper;
import ao.gov.sic.sip.records.DirecaoCSVRecord;
import ao.gov.sic.sip.records.FileRecord;
import ao.gov.sic.sip.repositories.DirecaoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.DirecaoCSVService;
import ao.gov.sic.sip.services.DirecaoService;
import ao.gov.sic.sip.services.StorageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirecaoServiceImpl implements DirecaoService {
    private final DirecaoRepository direcaoRepository;
    private final DirecaoMapper direcaoMapper;
    private final DirecaoCSVService direcaoCSVService;
    private final StorageFileService storageFileService;
    private final UserRepository userRepository;

    @Override
    public Response<DirecaoDTO> getDirecaoById(Long id) {
        Direcao direcao = direcaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Direção não encontrada"));

        DirecaoDTO direcaoDTO = direcaoMapper.direcaoToDirecaoDTO(direcao);

        return Response.<DirecaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Direção encontrada com sucesso")
                .data(direcaoDTO)
                .build();
    }

    @Override
    public Response<?> createDirecao(DirecaoDTO dto) {
        Direcao foundedDirecao = direcaoRepository.findAllByNome(dto.getNome());
        if (foundedDirecao != null) {
            throw new RuntimeException("Direção já existe");
        }

        Direcao direcao = direcaoMapper.direcaoDTOToDirecao(dto);
        direcaoRepository.save(direcao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Direção criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteDirecaoById(Long id) {
        if (!direcaoRepository.existsById(id)) {
            throw new NotFoundException("Direção não encontrada");
        }
        direcaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Direção eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> bulkDirecoesByCsv(MultipartFile csvFile) {
        try {
            FileRecord fileRecord = storageFileService.save(csvFile);

            List<DirecaoCSVRecord> csvRecords = direcaoCSVService
                    .convertCSV(fileRecord.getFile());

            AtomicReference<Integer> atomicTotalReference = new AtomicReference<>(0);

            for (DirecaoCSVRecord record : csvRecords) {
                if (record != null) {

                    Direcao foundedDirecao = direcaoRepository.findAllByNome(record.getNome());

                    if (foundedDirecao == null) {
                        Direcao direcao = new Direcao();
                        direcao.setNome(record.getNome());
                        direcao.setSigla(record.getSigla());
                        direcao.setDescricao(record.getDescricao());
                        direcaoRepository.save(direcao);
                        atomicTotalReference.set(atomicTotalReference.get() + 1);
                    }
                }
            }

            storageFileService.remove(fileRecord.getFileName());


            return Response.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(atomicTotalReference.get() == 0 ?
                            "Nenhuma direção foi adicionada, verifique o ficheiro CSV" :
                            "Foram adicionadas " + atomicTotalReference.get() + " direções")
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("e: ", e);
            throw new RuntimeException("Erro ao processar o ficheiro CSV");
        }
    }

    @Override
    public Response<?> updateDirecaoById(DirecaoDTO dto, Long id) {
        direcaoRepository.findById(id).ifPresentOrElse(direcao -> {
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                direcao.setUser(user);
            }

            if (StringUtils.hasText(dto.getNome())) {
                direcao.setNome(dto.getNome());
            }

            if (StringUtils.hasText(dto.getSigla())) {
                direcao.setSigla(dto.getSigla());
            }

            if (StringUtils.hasText(dto.getDescricao())) {
                direcao.setDescricao(dto.getDescricao());
            }

            if (StringUtils.hasText(dto.getNome()) || StringUtils.hasText(dto.getSigla())
                    || StringUtils.hasText(dto.getDescricao())) {
                direcao.setUpdatedAt(LocalDateTime.now());
            }
            direcaoRepository.save(direcao);
        }, () -> {
            throw new NotFoundException("Direção não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Direção actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<DirecaoDTO>> getAllDirecoes() {
        List<DirecaoDTO> direcoes = direcaoRepository.findAll()
                .stream().map(direcaoMapper::direcaoToDirecaoDTO)
                .toList();

        return Response.<List<DirecaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(direcoes.isEmpty() ? "Nenhuma direção encontrada" : "Direções encontradas com sucesso")
                .data(direcoes)
                .build();
    }
}
