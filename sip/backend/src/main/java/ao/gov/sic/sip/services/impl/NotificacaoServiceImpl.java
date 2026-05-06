package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.NotificacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Notificacao;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.NotificacaoMapper;
import ao.gov.sic.sip.repositories.NotificacaoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.NotificacaoService;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate; // Import SimpMessagingTemplate
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacaoServiceImpl implements NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final NotificacaoMapper notificacaoMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate; // Inject SimpMessagingTemplate

    @Override
    @Transactional
    public Response<NotificacaoDTO> create(NotificacaoDTO dto) {
        Notificacao notificacao = notificacaoMapper.toEntity(dto);

        User user;
        if (dto.getUserId() != null) {
            user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        } else {
            user = userService.currentUser();
            if (user == null) {
                throw new NotFoundException("Usuário não autenticado");
            }
        }
        notificacao.setUser(user);

        notificacao = notificacaoRepository.save(notificacao);
        NotificacaoDTO createdDto = notificacaoMapper.toDTO(notificacao);

        // Send real-time notification to the specific user
        messagingTemplate.convertAndSendToUser(
                user.getEmail(), // User identifier (e.g., email or username)
                "/queue/notifications", // Private queue for user-specific notifications
                createdDto
        );

        return Response.<NotificacaoDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Notificação criada com sucesso")
                .data(createdDto)
                .build();
    }

    @Override
    public Response<NotificacaoDTO> findById(Long id) {
        NotificacaoDTO dto = notificacaoRepository.findById(id)
                .map(notificacaoMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Notificação não encontrada"));
        return Response.<NotificacaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Notificação encontrada")
                .data(dto)
                .build();
    }

    @Override
    public Response<List<NotificacaoDTO>> findAll() {
        List<NotificacaoDTO> dtos = notificacaoRepository.findAll().stream()
                .map(notificacaoMapper::toDTO)
                .collect(Collectors.toList());
        return Response.<List<NotificacaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(dtos.isEmpty() ? "Nenhuma notificação encontrada" : "Notificações encontradas")
                .data(dtos)
                .build();
    }

    @Override
    public Response<List<NotificacaoDTO>> findMyNotifications() {
        User currentUser = userService.currentUser();
        if (currentUser == null) {
            throw new NotFoundException("Usuário não autenticado");
        }
        List<NotificacaoDTO> dtos = notificacaoRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId()).stream()
                .map(notificacaoMapper::toDTO)
                .collect(Collectors.toList());
        return Response.<List<NotificacaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(dtos.isEmpty() ? "Nenhuma notificação encontrada" : "Minhas notificações encontradas")
                .data(dtos)
                .build();
    }

    @Override
    public Response<List<NotificacaoDTO>> findMyUnreadNotifications() {
        User currentUser = userService.currentUser();
        if (currentUser == null) {
            throw new NotFoundException("Usuário não autenticado");
        }
        List<NotificacaoDTO> dtos = notificacaoRepository.findByUserIdAndLidaFalseOrderByCreatedAtDesc(currentUser.getId()).stream()
                .map(notificacaoMapper::toDTO)
                .collect(Collectors.toList());
        return Response.<List<NotificacaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(dtos.isEmpty() ? "Nenhuma notificação não lida encontrada" : "Minhas notificações não lidas encontradas")
                .data(dtos)
                .build();
    }

    @Override
    @Transactional
    public Response<NotificacaoDTO> markAsRead(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notificação não encontrada"));
        notificacao.setLida(true);
        notificacao = notificacaoRepository.save(notificacao);
        
        NotificacaoDTO updatedDto = notificacaoMapper.toDTO(notificacao);
        // Optionally send a WebSocket message about the updated notification
        messagingTemplate.convertAndSendToUser(
                notificacao.getUser().getEmail(),
                "/queue/notifications",
                updatedDto
        );

        return Response.<NotificacaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Notificação marcada como lida")
                .data(updatedDto)
                .build();
    }

    @Override
    @Transactional
    public Response<Void> delete(Long id) {
        if (!notificacaoRepository.existsById(id)) {
            throw new NotFoundException("Notificação não encontrada");
        }
        notificacaoRepository.deleteById(id);
        return Response.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Notificação eliminada com sucesso")
                .build();
    }
}
