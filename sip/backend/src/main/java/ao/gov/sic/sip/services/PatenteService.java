package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.PatenteDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface PatenteService {
    Response<PatenteDTO> getPatenteById(Long id);

    Response<List<PatenteDTO>> getAllPatentes();
}
