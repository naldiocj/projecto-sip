package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.CreateDetidoDTO;
import ao.gov.sic.sip.dtos.DetidoDTO;
import ao.gov.sic.sip.dtos.Response;
import java.util.List;

public interface DetidoService {
    Response<DetidoDTO> create(CreateDetidoDTO dto);
    Response<DetidoDTO> update(Long id, DetidoDTO dto);
    Response<DetidoDTO> findById(Long id);
    Response<List<DetidoDTO>> findAll();
    Response<Void> delete(Long id);
}
