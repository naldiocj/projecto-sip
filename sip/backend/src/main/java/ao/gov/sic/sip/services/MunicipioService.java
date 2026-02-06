package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.MunicipioDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface MunicipioService {
    Response<MunicipioDTO> getById(Long id);

    Response<?> updateById(MunicipioDTO dto, Long id);

    Response<?> create(MunicipioDTO dto);

    Response<?> deleteById(Long id);

    Response<List<MunicipioDTO>> getAll();
}
