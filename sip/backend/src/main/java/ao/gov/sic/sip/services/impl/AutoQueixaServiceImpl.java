package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoQueixaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoQueixaMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.AutoQueixaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoQueixaServiceImpl implements AutoQueixaService {
    private final AutoQueixaRepository autoQueixaRepository;
    private final AutoQueixaMapper autoQueixaMapper;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;
    private final ArguidoAutoQueixaRepository arguidoAutoQueixaRepository;
    private final TestemunhaAutoQueixaRepository testemunhaAutoQueixaRepository;

    @Override
    public Response<AutoQueixaDTO> getById(Long id) {
        AutoQueixa autoQueixa = autoQueixaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de queixa não encontrado"));

        AutoQueixaDTO autoQueixaDTO = autoQueixaMapper.autoQueixaToAutoQueixaDTO(autoQueixa);

        return Response.<AutoQueixaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de queixa encontrado com sucesso")
                .data(autoQueixaDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoQueixaDTO dto) {
        AutoQueixa autoQueixa = autoQueixaMapper.autoQueixaDTOToAutoQueixa(dto);

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoQueixa.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoQueixa.setUser(user);
        }

        if (dto.getArguidosQueixasIds() != null && !dto.getArguidosQueixasIds().isEmpty()) {
            List<ArguidoAutoQueixa> arguidos = arguidoAutoQueixaRepository.findAllById(dto.getArguidosQueixasIds());
            autoQueixa.setArguidosQueixas(arguidos);
        }

        if (dto.getTestemunhaQueixasIds() != null && !dto.getTestemunhaQueixasIds().isEmpty()) {
            List<TestemunhaAutoQueixa> testemunhas = testemunhaAutoQueixaRepository.findAllById(dto.getTestemunhaQueixasIds());
            autoQueixa.setTestemunhaQueixas(testemunhas);
        }

        autoQueixaRepository.save(autoQueixa);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de queixa criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoQueixaRepository.existsById(id)) {
            throw new NotFoundException("Auto de queixa não encontrado");
        }
        autoQueixaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de queixa eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoQueixaDTO dto, Long id) {
        autoQueixaRepository.findById(id).ifPresentOrElse(autoQueixa -> {
            if (dto.getDataEmissao() != null) {
                autoQueixa.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoQueixa.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoQueixa.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoQueixa.setUser(user);
            }
            if (dto.getArguidosQueixasIds() != null) {
                List<ArguidoAutoQueixa> arguidos = arguidoAutoQueixaRepository.findAllById(dto.getArguidosQueixasIds());
                autoQueixa.setArguidosQueixas(arguidos);
            }
            if (dto.getTestemunhaQueixasIds() != null) {
                List<TestemunhaAutoQueixa> testemunhas = testemunhaAutoQueixaRepository.findAllById(dto.getTestemunhaQueixasIds());
                autoQueixa.setTestemunhaQueixas(testemunhas);
            }
            autoQueixaRepository.save(autoQueixa);
        }, () -> {
            throw new NotFoundException("Auto de queixa não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de queixa actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoQueixaDTO>> getAll() {
        List<AutoQueixaDTO> autosQueixas = autoQueixaRepository.findAll()
                .stream().map(autoQueixaMapper::autoQueixaToAutoQueixaDTO)
                .toList();

        return Response.<List<AutoQueixaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosQueixas.isEmpty() ? "Nenhum auto de queixa encontrado" : "Autos de queixas encontrados com sucesso")
                .data(autosQueixas)
                .build();
    }
}
