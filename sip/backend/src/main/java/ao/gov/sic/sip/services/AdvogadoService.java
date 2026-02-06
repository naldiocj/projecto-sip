package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AdvogadoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AdvogadoService {
    Response<AdvogadoDTO> getById(Long id);

    Response<?> updateById(AdvogadoDTO dto, Long id);

    Response<?> create(AdvogadoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AdvogadoDTO>> getAll();
}
