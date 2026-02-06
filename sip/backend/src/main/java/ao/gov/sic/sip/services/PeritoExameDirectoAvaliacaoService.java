package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.PeritoExameDirectoAvaliacaoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface PeritoExameDirectoAvaliacaoService {
    Response<PeritoExameDirectoAvaliacaoDTO> getById(Long id);

    Response<?> updateById(PeritoExameDirectoAvaliacaoDTO dto, Long id);

    Response<?> create(PeritoExameDirectoAvaliacaoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<PeritoExameDirectoAvaliacaoDTO>> getAll();
}
