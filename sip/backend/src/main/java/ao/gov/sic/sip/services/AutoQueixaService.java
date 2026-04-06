package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoQueixaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoQueixaService {
    Response<AutoQueixaDTO> getById(Long id);

    Response<?> updateById(AutoQueixaDTO dto, Long id);

    Response<?> create(AutoQueixaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoQueixaDTO>> getAll();
}
