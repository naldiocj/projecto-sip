package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.PeritoExameDirectoAvaliacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.PeritoExameDirectoAvaliacao;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.PeritoExameDirectoAvaliacaoMapper;
import ao.gov.sic.sip.repositories.PeritoExameDirectoAvaliacaoRepository;
import ao.gov.sic.sip.services.PeritoExameDirectoAvaliacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeritoExameDirectoAvaliacaoServiceImpl implements PeritoExameDirectoAvaliacaoService {
    private final PeritoExameDirectoAvaliacaoRepository peritoExameDirectoAvaliacaoRepository;
    private final PeritoExameDirectoAvaliacaoMapper peritoExameDirectoAvaliacaoMapper;

    @Override
    public Response<PeritoExameDirectoAvaliacaoDTO> getById(Long id) {
        PeritoExameDirectoAvaliacao peritoExameDirectoAvaliacao = peritoExameDirectoAvaliacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Perito de exame directo de avaliação não encontrado"));

        PeritoExameDirectoAvaliacaoDTO peritoExameDirectoAvaliacaoDTO = peritoExameDirectoAvaliacaoMapper.peritoExameDirectoAvaliacaoToPeritoExameDirectoAvaliacaoDTO(peritoExameDirectoAvaliacao);

        return Response.<PeritoExameDirectoAvaliacaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Perito de exame directo de avaliação encontrado com sucesso")
                .data(peritoExameDirectoAvaliacaoDTO)
                .build();
    }

    @Override
    public Response<?> create(PeritoExameDirectoAvaliacaoDTO dto) {
        PeritoExameDirectoAvaliacao founded = peritoExameDirectoAvaliacaoRepository.findAllByNome(dto.getNome());
        if (founded != null) {
            throw new RuntimeException("Perito de exame directo de avaliação já existe");
        }

        PeritoExameDirectoAvaliacao peritoExameDirectoAvaliacao = peritoExameDirectoAvaliacaoMapper.peritoExameDirectoAvaliacaoDTOToPeritoExameDirectoAvaliacao(dto);
        peritoExameDirectoAvaliacaoRepository.save(peritoExameDirectoAvaliacao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Perito de exame directo de avaliação criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!peritoExameDirectoAvaliacaoRepository.existsById(id)) {
            throw new NotFoundException("Perito de exame directo de avaliação não encontrado");
        }
        peritoExameDirectoAvaliacaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Perito de exame directo de avaliação eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(PeritoExameDirectoAvaliacaoDTO dto, Long id) {
        peritoExameDirectoAvaliacaoRepository.findById(id).ifPresentOrElse(peritoExameDirectoAvaliacao -> {
            if (StringUtils.hasText(dto.getNome())) {
                peritoExameDirectoAvaliacao.setNome(dto.getNome());
            }
            peritoExameDirectoAvaliacaoRepository.save(peritoExameDirectoAvaliacao);
        }, () -> {
            throw new NotFoundException("Perito de exame directo de avaliação não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Perito de exame directo de avaliação actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<PeritoExameDirectoAvaliacaoDTO>> getAll() {
        List<PeritoExameDirectoAvaliacaoDTO> peritosExamesDirectosAvaliacoes = peritoExameDirectoAvaliacaoRepository.findAll()
                .stream().map(peritoExameDirectoAvaliacaoMapper::peritoExameDirectoAvaliacaoToPeritoExameDirectoAvaliacaoDTO)
                .toList();

        return Response.<List<PeritoExameDirectoAvaliacaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(peritosExamesDirectosAvaliacoes.isEmpty() ? "Nenhum perito de exame directo de avaliação encontrado" : "Peritos de exames directos de avaliações encontrados com sucesso")
                .data(peritosExamesDirectosAvaliacoes)
                .build();
    }
}
