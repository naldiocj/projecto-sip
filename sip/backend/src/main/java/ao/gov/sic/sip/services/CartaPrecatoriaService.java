package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.CartaPrecatoriaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface CartaPrecatoriaService {
    Response<CartaPrecatoriaDTO> getById(Long id);

    Response<?> updateById(CartaPrecatoriaDTO dto, Long id);

    Response<?> create(CartaPrecatoriaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<CartaPrecatoriaDTO>> getAll();
}
