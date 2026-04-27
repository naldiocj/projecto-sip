package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.DespachoDTO;
import java.util.List;

public interface DespachoService {
    DespachoDTO create(DespachoDTO dto);
    DespachoDTO update(Long id, DespachoDTO dto);
    DespachoDTO findById(Long id);
    List<DespachoDTO> findAll();
    List<DespachoDTO> findByProcessoId(Long processoId);
    void delete(Long id);
}
