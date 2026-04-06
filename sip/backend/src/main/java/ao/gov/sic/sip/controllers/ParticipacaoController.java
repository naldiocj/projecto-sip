package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ParticipacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.ParticipacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipacaoController {
    private final static String PARTICIPACAO_PATH = "/api/v1/participacoes";
    private final static String PARTICIPACAO_PATH_ID = PARTICIPACAO_PATH + "/{participacaoId}";
    private final static String PARTICIPACAO_PATH_ID_UPLOAD = PARTICIPACAO_PATH_ID + "/upload";

    private final ParticipacaoService participacaoService;

    @GetMapping(PARTICIPACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<ParticipacaoDTO>>> getAllParticipacoes() {
        return ResponseEntity.ok(participacaoService.getAll());
    }

    @GetMapping(PARTICIPACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getParticipacaoById(@PathVariable("participacaoId") Long participacaoId) {
        return ResponseEntity.ok(participacaoService.getById(participacaoId));
    }

    @PostMapping(PARTICIPACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createParticipacao(@Validated @RequestBody ParticipacaoDTO dto) {
        return ResponseEntity.ok(participacaoService.create(dto));
    }

    @PostMapping(PARTICIPACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateParticipacaoById(@RequestBody ParticipacaoDTO dto, @PathVariable("participacaoId") Long participacaoId) {
        return ResponseEntity.ok(participacaoService.updateById(dto, participacaoId));
    }

    @DeleteMapping(PARTICIPACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteParticipacaoById(@PathVariable("participacaoId") Long participacaoId) {
        return ResponseEntity.ok(participacaoService.deleteById(participacaoId));
    }

    @PostMapping(PARTICIPACAO_PATH_ID_UPLOAD)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> addFile(@RequestBody MultipartFile file, @PathVariable("participacaoId") Long participacaoId) {
        return ResponseEntity.ok(participacaoService.addFile(file, participacaoId));
    }
}
