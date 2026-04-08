package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoDiligenciaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoDiligenciaService {
    Response<AutoDiligenciaDTO> getById(Long id);

    Response<?> updateById(AutoDiligenciaDTO dto, Long id);

    Response<?> create(AutoDiligenciaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoDiligenciaDTO>> getAll();
}
