package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AvisoNotificacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AvisoNotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AvisoNotificacaoController {
    private final static String AVISO_NOTIFICACAO_PATH = "/api/v1/avisos-notificacoes";
    private final static String AVISO_NOTIFICACAO_PATH_ID = AVISO_NOTIFICACAO_PATH + "/{avisoNotificacaoId}";

    private final AvisoNotificacaoService avisoNotificacaoService;

    @GetMapping(AVISO_NOTIFICACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AvisoNotificacaoDTO>>> getAllAvisosNotificacoes() {
        return ResponseEntity.ok(avisoNotificacaoService.getAll());
    }

    @GetMapping(AVISO_NOTIFICACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAvisoNotificacaoById(@PathVariable("avisoNotificacaoId") Long avisoNotificacaoId) {
        return ResponseEntity.ok(avisoNotificacaoService.getById(avisoNotificacaoId));
    }

    @PostMapping(AVISO_NOTIFICACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAvisoNotificacao(@Validated @RequestBody AvisoNotificacaoDTO dto) {
        return ResponseEntity.ok(avisoNotificacaoService.create(dto));
    }

    @PostMapping(AVISO_NOTIFICACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAvisoNotificacaoById(@RequestBody AvisoNotificacaoDTO dto, @PathVariable("avisoNotificacaoId") Long avisoNotificacaoId) {
        return ResponseEntity.ok(avisoNotificacaoService.updateById(dto, avisoNotificacaoId));
    }

    @DeleteMapping(AVISO_NOTIFICACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAvisoNotificacaoById(@PathVariable("avisoNotificacaoId") Long avisoNotificacaoId) {
        return ResponseEntity.ok(avisoNotificacaoService.deleteById(avisoNotificacaoId));
    }
}
