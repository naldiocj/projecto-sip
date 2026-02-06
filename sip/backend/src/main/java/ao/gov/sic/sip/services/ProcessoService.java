package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ProcessoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface ProcessoService {
    Response<ProcessoDTO> getById(Long id);

    Response<?> updateById(ProcessoDTO dto, Long id);

    Response<?> create(ProcessoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<ProcessoDTO>> getAll();
}
