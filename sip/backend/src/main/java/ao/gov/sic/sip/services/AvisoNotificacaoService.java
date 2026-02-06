package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AvisoNotificacaoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AvisoNotificacaoService {
    Response<AvisoNotificacaoDTO> getById(Long id);

    Response<?> updateById(AvisoNotificacaoDTO dto, Long id);

    Response<?> create(AvisoNotificacaoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AvisoNotificacaoDTO>> getAll();
}
