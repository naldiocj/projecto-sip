package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.MandadoDTO;
import ao.gov.sic.sip.dtos.Response;
import java.util.List;

public interface MandadoService {
    Response<MandadoDTO> create(MandadoDTO dto);
    Response<MandadoDTO> update(Long id, MandadoDTO dto);
    Response<MandadoDTO> findById(Long id);
    Response<List<MandadoDTO>> findAll();
    Response<Void> delete(Long id);
}
