package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.ArguidoAutoQueixaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.ArguidoAutoQueixa;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ArguidoAutoQueixaMapper;
import ao.gov.sic.sip.repositories.ArguidoAutoQueixaRepository;
import ao.gov.sic.sip.services.ArguidoAutoQueixaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArguidoAutoQueixaServiceImpl implements ArguidoAutoQueixaService {
    private final ArguidoAutoQueixaRepository arguidoAutoQueixaRepository;
    private final ArguidoAutoQueixaMapper arguidoAutoQueixaMapper;

    @Override
    public Response<ArguidoAutoQueixaDTO> getById(Long id) {
        ArguidoAutoQueixa arguidoAutoQueixa = arguidoAutoQueixaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Arguido de auto de queixa não encontrado"));

        ArguidoAutoQueixaDTO arguidoAutoQueixaDTO = arguidoAutoQueixaMapper.arguidoAutoQueixaToArguidoAutoQueixaDTO(arguidoAutoQueixa);

        return Response.<ArguidoAutoQueixaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Arguido de auto de queixa encontrado com sucesso")
                .data(arguidoAutoQueixaDTO)
                .build();
    }

    @Override
    public Response<?> create(ArguidoAutoQueixaDTO dto) {
        ArguidoAutoQueixa founded = arguidoAutoQueixaRepository.findAllByNome(dto.getNome());
        if (founded != null) {
            throw new RuntimeException("Arguido de auto de queixa já existe");
        }

        ArguidoAutoQueixa arguidoAutoQueixa = arguidoAutoQueixaMapper.arguidoAutoQueixaDTOToArguidoAutoQueixa(dto);
        arguidoAutoQueixaRepository.save(arguidoAutoQueixa);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Arguido de auto de queixa criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!arguidoAutoQueixaRepository.existsById(id)) {
            throw new NotFoundException("Arguido de auto de queixa não encontrado");
        }
        arguidoAutoQueixaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Arguido de auto de queixa eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(ArguidoAutoQueixaDTO dto, Long id) {
        arguidoAutoQueixaRepository.findById(id).ifPresentOrElse(arguidoAutoQueixa -> {
            if (StringUtils.hasText(dto.getNome())) {
                arguidoAutoQueixa.setNome(dto.getNome());
            }
            arguidoAutoQueixaRepository.save(arguidoAutoQueixa);
        }, () -> {
            throw new NotFoundException("Arguido de auto de queixa não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Arguido de auto de queixa actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<ArguidoAutoQueixaDTO>> getAll() {
        List<ArguidoAutoQueixaDTO> arguidosAutosQueixas = arguidoAutoQueixaRepository.findAll()
                .stream().map(arguidoAutoQueixaMapper::arguidoAutoQueixaToArguidoAutoQueixaDTO)
                .toList();

        return Response.<List<ArguidoAutoQueixaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(arguidosAutosQueixas.isEmpty() ? "Nenhum arguido de auto de queixa encontrado" : "Arguidos de autos de queixas encontrados com sucesso")
                .data(arguidosAutosQueixas)
                .build();
    }
}
