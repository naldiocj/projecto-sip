package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.QueixosoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface QueixosoService {
    Response<QueixosoDTO> getById(Long id);

    Response<?> updateById(QueixosoDTO dto, Long id);

    Response<?> create(QueixosoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<QueixosoDTO>> getAll();
}
