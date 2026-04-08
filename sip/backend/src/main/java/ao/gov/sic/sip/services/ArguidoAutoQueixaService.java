package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ArguidoAutoQueixaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface ArguidoAutoQueixaService {
    Response<ArguidoAutoQueixaDTO> getById(Long id);

    Response<?> updateById(ArguidoAutoQueixaDTO dto, Long id);

    Response<?> create(ArguidoAutoQueixaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<ArguidoAutoQueixaDTO>> getAll();
}
