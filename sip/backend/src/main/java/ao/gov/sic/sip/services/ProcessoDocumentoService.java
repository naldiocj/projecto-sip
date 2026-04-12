package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ProcessoDocumentoDTO;
import ao.gov.sic.sip.dtos.ProcessoDocumentoItemDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.ProcessoDocumento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProcessoDocumentoService {
    Response<?> saveDocumento(ProcessoDocumentoDTO dto);
    Response<List<ProcessoDocumentoItemDTO>> getDocumentosByProcessoId(Long id);
}
