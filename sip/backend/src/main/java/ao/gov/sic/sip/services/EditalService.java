package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.EditalDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface EditalService {
    Response<EditalDTO> getById(Long id);

    Response<?> updateById(EditalDTO dto, Long id);

    Response<?> create(EditalDTO dto);

    Response<?> deleteById(Long id);

    Response<List<EditalDTO>> getAll();
}
