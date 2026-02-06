package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ArguidoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface ArguidoService {
    Response<ArguidoDTO> getById(Long id);

    Response<?> updateById(ArguidoDTO dto, Long id);

    Response<?> create(ArguidoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<ArguidoDTO>> getAll();
}
