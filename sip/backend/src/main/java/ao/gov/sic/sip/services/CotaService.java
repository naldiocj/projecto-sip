package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.CotaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface CotaService {
    Response<CotaDTO> getById(Long id);

    Response<?> updateById(CotaDTO dto, Long id);

    Response<?> create(CotaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<CotaDTO>> getAll();
}
