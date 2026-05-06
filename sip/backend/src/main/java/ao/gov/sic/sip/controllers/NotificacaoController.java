package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.NotificacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @PostMapping
    public ResponseEntity<Response<NotificacaoDTO>> create(@RequestBody NotificacaoDTO dto) {
        return new ResponseEntity<>(notificacaoService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<NotificacaoDTO>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(notificacaoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<NotificacaoDTO>>> findAll() {
        return ResponseEntity.ok(notificacaoService.findAll());
    }

    @GetMapping("/my")
    public ResponseEntity<Response<List<NotificacaoDTO>>> findMyNotifications() {
        return ResponseEntity.ok(notificacaoService.findMyNotifications());
    }

    @GetMapping("/my/unread")
    public ResponseEntity<Response<List<NotificacaoDTO>>> findMyUnreadNotifications() {
        return ResponseEntity.ok(notificacaoService.findMyUnreadNotifications());
    }

    @PutMapping("/{id}/mark-as-read")
    public ResponseEntity<Response<NotificacaoDTO>> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificacaoService.markAsRead(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(notificacaoService.delete(id));
    }
}
