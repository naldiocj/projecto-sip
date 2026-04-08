package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoDiligenciaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoDiligencia;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoDiligenciaMapper;
import ao.gov.sic.sip.repositories.AutoDiligenciaRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoDiligenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoDiligenciaServiceImpl implements AutoDiligenciaService {
    private final AutoDiligenciaRepository autoDiligenciaRepository;
    private final AutoDiligenciaMapper autoDiligenciaMapper;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoDiligenciaDTO> getById(Long id) {
        AutoDiligencia autoDiligencia = autoDiligenciaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de diligência não encontrado"));

        AutoDiligenciaDTO autoDiligenciaDTO = autoDiligenciaMapper.autoDiligenciaToAutoDiligenciaDTO(autoDiligencia);

        return Response.<AutoDiligenciaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de diligência encontrado com sucesso")
                .data(autoDiligenciaDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoDiligenciaDTO dto) {
        AutoDiligencia autoDiligencia = autoDiligenciaMapper.autoDiligenciaDTOToAutoDiligencia(dto);

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoDiligencia.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoDiligencia.setUser(user);
        }

        autoDiligenciaRepository.save(autoDiligencia);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de diligência criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoDiligenciaRepository.existsById(id)) {
            throw new NotFoundException("Auto de diligência não encontrado");
        }
        autoDiligenciaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de diligência eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoDiligenciaDTO dto, Long id) {
        autoDiligenciaRepository.findById(id).ifPresentOrElse(autoDiligencia -> {
            if (StringUtils.hasText(dto.getIntroducao())) {
                autoDiligencia.setIntroducao(dto.getIntroducao());
            }
            if (StringUtils.hasText(dto.getDesenvolvimento())) {
                autoDiligencia.setDesenvolvimento(dto.getDesenvolvimento());
            }
            if (StringUtils.hasText(dto.getConclusao())) {
                autoDiligencia.setConclusao(dto.getConclusao());
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoDiligencia.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoDiligencia.setUser(user);
            }
            autoDiligenciaRepository.save(autoDiligencia);
        }, () -> {
            throw new NotFoundException("Auto de diligência não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de diligência actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoDiligenciaDTO>> getAll() {
        List<AutoDiligenciaDTO> autosDiligencias = autoDiligenciaRepository.findAll()
                .stream().map(autoDiligenciaMapper::autoDiligenciaToAutoDiligenciaDTO)
                .toList();

        return Response.<List<AutoDiligenciaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosDiligencias.isEmpty() ? "Nenhum auto de diligência encontrado" : "Autos de diligências encontrados com sucesso")
                .data(autosDiligencias)
                .build();
    }
}
