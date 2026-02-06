package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.DirectorDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface DirectorService {
    Response<DirectorDTO> getById(Long id);

    Response<?> updateById(DirectorDTO dto, Long id);

    Response<?> create(DirectorDTO dto);

    Response<?> deleteById(Long id);

    Response<List<DirectorDTO>> getAll();
}
