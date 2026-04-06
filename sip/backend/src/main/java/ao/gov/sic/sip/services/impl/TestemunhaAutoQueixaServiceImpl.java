package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.TestemunhaAutoQueixaDTO;
import ao.gov.sic.sip.entities.TestemunhaAutoQueixa;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.TestemunhaAutoQueixaMapper;
import ao.gov.sic.sip.repositories.TestemunhaAutoQueixaRepository;
import ao.gov.sic.sip.services.TestemunhaAutoQueixaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestemunhaAutoQueixaServiceImpl implements TestemunhaAutoQueixaService {
    private final TestemunhaAutoQueixaRepository testemunhaAutoQueixaRepository;
    private final TestemunhaAutoQueixaMapper testemunhaAutoQueixaMapper;

    @Override
    public Response<TestemunhaAutoQueixaDTO> getById(Long id) {
        TestemunhaAutoQueixa testemunhaAutoQueixa = testemunhaAutoQueixaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Testemunha de auto de queixa não encontrada"));

        TestemunhaAutoQueixaDTO testemunhaAutoQueixaDTO = testemunhaAutoQueixaMapper.testemunhaAutoQueixaToTestemunhaAutoQueixaDTO(testemunhaAutoQueixa);

        return Response.<TestemunhaAutoQueixaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Testemunha de auto de queixa encontrada com sucesso")
                .data(testemunhaAutoQueixaDTO)
                .build();
    }

    @Override
    public Response<?> create(TestemunhaAutoQueixaDTO dto) {
        TestemunhaAutoQueixa founded = testemunhaAutoQueixaRepository.findAllByNome(dto.getNome());
        if (founded != null) {
            throw new RuntimeException("Testemunha de auto de queixa já existe");
        }

        TestemunhaAutoQueixa testemunhaAutoQueixa = testemunhaAutoQueixaMapper.testemunhaAutoQueixaDTOToTestemunhaAutoQueixa(dto);
        testemunhaAutoQueixaRepository.save(testemunhaAutoQueixa);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Testemunha de auto de queixa criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!testemunhaAutoQueixaRepository.existsById(id)) {
            throw new NotFoundException("Testemunha de auto de queixa não encontrada");
        }
        testemunhaAutoQueixaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Testemunha de auto de queixa eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(TestemunhaAutoQueixaDTO dto, Long id) {
        testemunhaAutoQueixaRepository.findById(id).ifPresentOrElse(testemunhaAutoQueixa -> {
            if (StringUtils.hasText(dto.getNome())) {
                testemunhaAutoQueixa.setNome(dto.getNome());
            }
            testemunhaAutoQueixaRepository.save(testemunhaAutoQueixa);
        }, () -> {
            throw new NotFoundException("Testemunha de auto de queixa não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Testemunha de auto de queixa actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<TestemunhaAutoQueixaDTO>> getAll() {
        List<TestemunhaAutoQueixaDTO> testemunhasAutosQueixas = testemunhaAutoQueixaRepository.findAll()
                .stream().map(testemunhaAutoQueixaMapper::testemunhaAutoQueixaToTestemunhaAutoQueixaDTO)
                .toList();

        return Response.<List<TestemunhaAutoQueixaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(testemunhasAutosQueixas.isEmpty() ? "Nenhuma testemunha de auto de queixa encontrada" : "Testemunhas de autos de queixas encontradas com sucesso")
                .data(testemunhasAutosQueixas)
                .build();
    }
}
