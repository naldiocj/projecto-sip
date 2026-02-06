package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoExameDirectoAvaliacaoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoExameDirectoAvaliacaoService {
    Response<AutoExameDirectoAvaliacaoDTO> getById(Long id);

    Response<?> updateById(AutoExameDirectoAvaliacaoDTO dto, Long id);

    Response<?> create(AutoExameDirectoAvaliacaoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoExameDirectoAvaliacaoDTO>> getAll();
}
