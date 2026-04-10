package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ProcessoDocumentoDTO;
import ao.gov.sic.sip.dtos.Response;

public interface ProcessoDocumentoService {
    Response<?> saveDocumento(ProcessoDocumentoDTO dto);
}
