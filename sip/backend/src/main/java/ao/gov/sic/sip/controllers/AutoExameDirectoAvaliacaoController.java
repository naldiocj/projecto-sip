package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoExameDirectoAvaliacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoExameDirectoAvaliacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoExameDirectoAvaliacaoController {
    private final static String AUTO_EXAME_DIRECTO_AVALIACAO_PATH = "/api/v1/autos-exames-directos-avaliacoes";
    private final static String AUTO_EXAME_DIRECTO_AVALIACAO_PATH_ID = AUTO_EXAME_DIRECTO_AVALIACAO_PATH + "/{autoExameDirectoAvaliacaoId}";

    private final AutoExameDirectoAvaliacaoService autoExameDirectoAvaliacaoService;

    @GetMapping(AUTO_EXAME_DIRECTO_AVALIACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoExameDirectoAvaliacaoDTO>>> getAllAutosExamesDirectosAvaliacoes() {
        return ResponseEntity.ok(autoExameDirectoAvaliacaoService.getAll());
    }

    @GetMapping(AUTO_EXAME_DIRECTO_AVALIACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoExameDirectoAvaliacaoById(@PathVariable("autoExameDirectoAvaliacaoId") Long autoExameDirectoAvaliacaoId) {
        return ResponseEntity.ok(autoExameDirectoAvaliacaoService.getById(autoExameDirectoAvaliacaoId));
    }

    @PostMapping(AUTO_EXAME_DIRECTO_AVALIACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoExameDirectoAvaliacao(@Validated @RequestBody AutoExameDirectoAvaliacaoDTO dto) {
        return ResponseEntity.ok(autoExameDirectoAvaliacaoService.create(dto));
    }

    @PostMapping(AUTO_EXAME_DIRECTO_AVALIACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoExameDirectoAvaliacaoById(@RequestBody AutoExameDirectoAvaliacaoDTO dto, @PathVariable("autoExameDirectoAvaliacaoId") Long autoExameDirectoAvaliacaoId) {
        return ResponseEntity.ok(autoExameDirectoAvaliacaoService.updateById(dto, autoExameDirectoAvaliacaoId));
    }

    @DeleteMapping(AUTO_EXAME_DIRECTO_AVALIACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoExameDirectoAvaliacaoById(@PathVariable("autoExameDirectoAvaliacaoId") Long autoExameDirectoAvaliacaoId) {
        return ResponseEntity.ok(autoExameDirectoAvaliacaoService.deleteById(autoExameDirectoAvaliacaoId));
    }
}
