package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoReconhecimentoFisicoDirectoObjectoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoReconhecimentoFisicoDirectoObjectoService {
    Response<AutoReconhecimentoFisicoDirectoObjectoDTO> getById(Long id);

    Response<?> updateById(AutoReconhecimentoFisicoDirectoObjectoDTO dto, Long id);

    Response<?> create(AutoReconhecimentoFisicoDirectoObjectoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoReconhecimentoFisicoDirectoObjectoDTO>> getAll();
}
