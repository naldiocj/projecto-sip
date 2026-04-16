package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.*;

import java.util.List;

public interface ProcessoService {
    Response<ProcessoDetailDTO> getById(Long id);

    Response<ProcessoDetailDTO> getByNumero(String numero);

    Response<?> updateById(ProcessoDTO dto, Long id);

    Response<?> create(ProcessoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<ProcessoResDTO>> getAll(String term);

    void patchProcessoById(Long id, UpdateProcessoDTO dto);
}
