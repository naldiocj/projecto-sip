package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.PeritoExameDirectoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface PeritoExameDirectoService {
    Response<PeritoExameDirectoDTO> getById(Long id);

    Response<?> updateById(PeritoExameDirectoDTO dto, Long id);

    Response<?> create(PeritoExameDirectoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<PeritoExameDirectoDTO>> getAll();
}
