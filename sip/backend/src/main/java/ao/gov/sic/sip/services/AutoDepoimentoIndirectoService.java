package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoDepoimentoIndirectoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoDepoimentoIndirectoService {
    Response<AutoDepoimentoIndirectoDTO> getById(Long id);

    Response<?> updateById(AutoDepoimentoIndirectoDTO dto, Long id);

    Response<?> create(AutoDepoimentoIndirectoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoDepoimentoIndirectoDTO>> getAll();
}
