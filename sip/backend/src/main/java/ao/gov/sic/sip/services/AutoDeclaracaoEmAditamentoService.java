package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoDeclaracaoEmAditamentoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoDeclaracaoEmAditamentoService {
    Response<AutoDeclaracaoEmAditamentoDTO> getById(Long id);

    Response<?> updateById(AutoDeclaracaoEmAditamentoDTO dto, Long id);

    Response<?> create(AutoDeclaracaoEmAditamentoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoDeclaracaoEmAditamentoDTO>> getAll();
}
