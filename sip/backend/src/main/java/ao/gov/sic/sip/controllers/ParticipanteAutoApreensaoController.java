package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ParticipanteAutoApreensaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.ParticipanteAutoApreensaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipanteAutoApreensaoController {
    private final static String PARTICIPANTE_AUTO_APREENSAO_PATH = "/api/v1/participantes-autos-apreensoes";
    private final static String PARTICIPANTE_AUTO_APREENSAO_PATH_ID = PARTICIPANTE_AUTO_APREENSAO_PATH + "/{participanteAutoApreensaoId}";

    private final ParticipanteAutoApreensaoService participanteAutoApreensaoService;

    @GetMapping(PARTICIPANTE_AUTO_APREENSAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<ParticipanteAutoApreensaoDTO>>> getAllParticipantesAutosApreensoes() {
        return ResponseEntity.ok(participanteAutoApreensaoService.getAll());
    }

    @GetMapping(PARTICIPANTE_AUTO_APREENSAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getParticipanteAutoApreensaoById(@PathVariable("participanteAutoApreensaoId") Long participanteAutoApreensaoId) {
        return ResponseEntity.ok(participanteAutoApreensaoService.getById(participanteAutoApreensaoId));
    }

    @PostMapping(PARTICIPANTE_AUTO_APREENSAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createParticipanteAutoApreensao(@Validated @RequestBody ParticipanteAutoApreensaoDTO dto) {
        return ResponseEntity.ok(participanteAutoApreensaoService.create(dto));
    }

    @PostMapping(PARTICIPANTE_AUTO_APREENSAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateParticipanteAutoApreensaoById(@RequestBody ParticipanteAutoApreensaoDTO dto, @PathVariable("participanteAutoApreensaoId") Long participanteAutoApreensaoId) {
        return ResponseEntity.ok(participanteAutoApreensaoService.updateById(dto, participanteAutoApreensaoId));
    }

    @DeleteMapping(PARTICIPANTE_AUTO_APREENSAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteParticipanteAutoApreensaoById(@PathVariable("participanteAutoApreensaoId") Long participanteAutoApreensaoId) {
        return ResponseEntity.ok(participanteAutoApreensaoService.deleteById(participanteAutoApreensaoId));
    }
}
