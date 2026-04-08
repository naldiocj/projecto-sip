package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ParticipanteDTO;
import ao.gov.sic.sip.dtos.ParticipanteResDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.ParticipanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipanteController {
    private final static String PARTICIPANTE_PATH = "/api/v1/participantes";
    private final static String PARTICIPANTE_PATH_ID = PARTICIPANTE_PATH + "/{participanteId}";

    private final ParticipanteService participanteService;

    @GetMapping(PARTICIPANTE_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<ParticipanteResDTO>>> getAllParticipantes(@Param(value = "processoId") String numero) {
        boolean hasNumero = StringUtils.hasText(numero);
        String numeroConvertido = hasNumero ? numero.replaceFirst("-", "/") : null;
        return ResponseEntity.ok(participanteService.getAllByProcessoNumero(numeroConvertido));
    }

    @GetMapping(PARTICIPANTE_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getParticipanteById(@PathVariable("participanteId") Long participanteId) {
        return ResponseEntity.ok(participanteService.getById(participanteId));
    }

    @PostMapping(PARTICIPANTE_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createParticipante(@Validated @RequestBody ParticipanteDTO dto) {
        return ResponseEntity.ok(participanteService.create(dto));
    }

    @PostMapping(PARTICIPANTE_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateParticipanteById(@RequestBody ParticipanteDTO dto, @PathVariable("participanteId") Long participanteId) {
        return ResponseEntity.ok(participanteService.updateById(dto, participanteId));
    }

    @DeleteMapping(PARTICIPANTE_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteParticipanteById(@PathVariable("participanteId") Long participanteId) {
        return ResponseEntity.ok(participanteService.deleteById(participanteId));
    }
}
