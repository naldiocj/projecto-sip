package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ProcessoDTO;
import ao.gov.sic.sip.dtos.ProcessoDetailDTO;
import ao.gov.sic.sip.dtos.ProcessoResDTO;
import ao.gov.sic.sip.dtos.Response;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface ProcessoService {
    Response<ProcessoDetailDTO> getById(Long id);

    Response<ProcessoDetailDTO> getByNumero(String numero);

    Response<?> updateById(ProcessoDTO dto, Long id);

    Response<?> create(ProcessoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<ProcessoResDTO>> getAll(String term);

    void patchProcessoById(Long id, JsonNode patch);
}
