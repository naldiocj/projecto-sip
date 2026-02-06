package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.RemessaCartaPrecatoriaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface RemessaCartaPrecatoriaService {
    Response<RemessaCartaPrecatoriaDTO> getById(Long id);

    Response<?> updateById(RemessaCartaPrecatoriaDTO dto, Long id);

    Response<?> create(RemessaCartaPrecatoriaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<RemessaCartaPrecatoriaDTO>> getAll();
}
