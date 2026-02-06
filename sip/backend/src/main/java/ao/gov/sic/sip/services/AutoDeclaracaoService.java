package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoDeclaracaoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoDeclaracaoService {
    Response<AutoDeclaracaoDTO> getById(Long id);

    Response<?> updateById(AutoDeclaracaoDTO dto, Long id);

    Response<?> create(AutoDeclaracaoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoDeclaracaoDTO>> getAll();
}
