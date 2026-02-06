package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoInterrogatorioEmAditamentoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoInterrogatorioEmAditamentoService {
    Response<AutoInterrogatorioEmAditamentoDTO> getById(Long id);

    Response<?> updateById(AutoInterrogatorioEmAditamentoDTO dto, Long id);

    Response<?> create(AutoInterrogatorioEmAditamentoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoInterrogatorioEmAditamentoDTO>> getAll();
}
