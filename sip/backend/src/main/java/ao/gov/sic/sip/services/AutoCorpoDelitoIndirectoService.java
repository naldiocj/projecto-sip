package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoCorpoDelitoIndirectoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoCorpoDelitoIndirectoService {
    Response<AutoCorpoDelitoIndirectoDTO> getById(Long id);

    Response<?> updateById(AutoCorpoDelitoIndirectoDTO dto, Long id);

    Response<?> create(AutoCorpoDelitoIndirectoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoCorpoDelitoIndirectoDTO>> getAll();
}
