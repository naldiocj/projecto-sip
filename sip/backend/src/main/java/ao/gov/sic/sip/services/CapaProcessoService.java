package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.CapaProcessoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface CapaProcessoService {
    Response<CapaProcessoDTO> getById(Long id);

    Response<?> updateById(CapaProcessoDTO dto, Long id);

    Response<?> create(CapaProcessoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<CapaProcessoDTO>> getAll();
}
