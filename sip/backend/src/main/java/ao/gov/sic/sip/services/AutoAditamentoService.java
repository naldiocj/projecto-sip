package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoAditamentoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoAditamentoService {
    Response<AutoAditamentoDTO> getById(Long id);

    Response<?> updateById(AutoAditamentoDTO dto, Long id);

    Response<?> create(AutoAditamentoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoAditamentoDTO>> getAll();
}
