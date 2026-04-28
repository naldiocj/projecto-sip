package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.RemessaDTO;
import ao.gov.sic.sip.dtos.Response;
import java.util.List;

public interface RemessaService {
    Response<RemessaDTO> create(RemessaDTO dto);
    Response<RemessaDTO> update(Long id, RemessaDTO dto);
    Response<RemessaDTO> findById(Long id);
    Response<List<RemessaDTO>> findAll();
    Response<Void> delete(Long id);
}
