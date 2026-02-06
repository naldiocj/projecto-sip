package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoDepoimentoDirectoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoDepoimentoDirectoService {
    Response<AutoDepoimentoDirectoDTO> getById(Long id);

    Response<?> updateById(AutoDepoimentoDirectoDTO dto, Long id);

    Response<?> create(AutoDepoimentoDirectoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoDepoimentoDirectoDTO>> getAll();
}
