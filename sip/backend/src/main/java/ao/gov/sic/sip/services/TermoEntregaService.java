package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.TermoEntregaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface TermoEntregaService {
    Response<TermoEntregaDTO> getById(Long id);

    Response<?> updateById(TermoEntregaDTO dto, Long id);

    Response<?> create(TermoEntregaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<TermoEntregaDTO>> getAll();
}
