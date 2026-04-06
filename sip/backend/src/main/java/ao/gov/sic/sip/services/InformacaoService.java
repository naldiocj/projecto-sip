package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.InformacaoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface InformacaoService {
    Response<InformacaoDTO> getById(Long id);

    Response<?> updateById(InformacaoDTO dto, Long id);

    Response<?> create(InformacaoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<InformacaoDTO>> getAll();
}
