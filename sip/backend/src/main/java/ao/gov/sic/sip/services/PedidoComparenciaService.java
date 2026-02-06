package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.PedidoComparenciaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface PedidoComparenciaService {
    Response<PedidoComparenciaDTO> getById(Long id);

    Response<?> updateById(PedidoComparenciaDTO dto, Long id);

    Response<?> create(PedidoComparenciaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<PedidoComparenciaDTO>> getAll();
}
