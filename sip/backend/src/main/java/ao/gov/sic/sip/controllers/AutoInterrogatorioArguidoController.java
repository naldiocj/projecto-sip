package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoInterrogatorioArguidoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoInterrogatorioArguidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoInterrogatorioArguidoController {
    private final static String AUTO_INTERROGATORIO_ARGUIDO_PATH = "/api/v1/autos-interrogatorios-arguidos";
    private final static String AUTO_INTERROGATORIO_ARGUIDO_PATH_ID = AUTO_INTERROGATORIO_ARGUIDO_PATH + "/{autoInterrogatorioArguidoId}";

    private final AutoInterrogatorioArguidoService autoInterrogatorioArguidoService;

    @GetMapping(AUTO_INTERROGATORIO_ARGUIDO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoInterrogatorioArguidoDTO>>> getAllAutosInterrogatoriosArguidos() {
        return ResponseEntity.ok(autoInterrogatorioArguidoService.getAll());
    }

    @GetMapping(AUTO_INTERROGATORIO_ARGUIDO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoInterrogatorioArguidoById(@PathVariable("autoInterrogatorioArguidoId") Long autoInterrogatorioArguidoId) {
        return ResponseEntity.ok(autoInterrogatorioArguidoService.getById(autoInterrogatorioArguidoId));
    }

    @PostMapping(AUTO_INTERROGATORIO_ARGUIDO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoInterrogatorioArguido(@Validated @RequestBody AutoInterrogatorioArguidoDTO dto) {
        return ResponseEntity.ok(autoInterrogatorioArguidoService.create(dto));
    }

    @PostMapping(AUTO_INTERROGATORIO_ARGUIDO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoInterrogatorioArguidoById(@RequestBody AutoInterrogatorioArguidoDTO dto, @PathVariable("autoInterrogatorioArguidoId") Long autoInterrogatorioArguidoId) {
        return ResponseEntity.ok(autoInterrogatorioArguidoService.updateById(dto, autoInterrogatorioArguidoId));
    }

    @DeleteMapping(AUTO_INTERROGATORIO_ARGUIDO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoInterrogatorioArguidoById(@PathVariable("autoInterrogatorioArguidoId") Long autoInterrogatorioArguidoId) {
        return ResponseEntity.ok(autoInterrogatorioArguidoService.deleteById(autoInterrogatorioArguidoId));
    }
}
