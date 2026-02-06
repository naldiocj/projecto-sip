package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoExameDirectoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoExameDirectoService {
    Response<AutoExameDirectoDTO> getById(Long id);

    Response<?> updateById(AutoExameDirectoDTO dto, Long id);

    Response<?> create(AutoExameDirectoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoExameDirectoDTO>> getAll();
}
