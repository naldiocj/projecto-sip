package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.NotificacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import java.util.List;

public interface NotificacaoService {
    Response<NotificacaoDTO> create(NotificacaoDTO dto);
    Response<NotificacaoDTO> findById(Long id);
    Response<List<NotificacaoDTO>> findAll();
    Response<List<NotificacaoDTO>> findMyNotifications();
    Response<List<NotificacaoDTO>> findMyUnreadNotifications();
    Response<NotificacaoDTO> markAsRead(Long id);
    Response<Void> delete(Long id);
}
