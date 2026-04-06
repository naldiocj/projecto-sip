package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.DiligenciaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface DiligenciaService {
    Response<DiligenciaDTO> getById(Long id);

    Response<?> updateById(DiligenciaDTO dto, Long id);

    Response<?> create(DiligenciaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<DiligenciaDTO>> getAll();

    Response<List<DiligenciaDTO>> getAllByProcessoId(Long processoId);

    Response<List<DiligenciaDTO>> getAllByProcessoNumero(String numero);
}
