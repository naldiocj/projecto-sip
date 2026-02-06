package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ProvinciaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface ProvinciaService {
    Response<ProvinciaDTO> getById(Long id);

    Response<?> updateById(ProvinciaDTO dto, Long id);

    Response<?> create(ProvinciaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<ProvinciaDTO>> getAll();
}
