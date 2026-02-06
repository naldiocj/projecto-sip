package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoInterrogatorioEmAditamentoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoInterrogatorioEmAditamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoInterrogatorioEmAditamentoController {
    private final static String AUTO_INTERROGATORIO_EM_ADITAMENTO_PATH = "/api/v1/autos-interrogatorios-em-aditamentos";
    private final static String AUTO_INTERROGATORIO_EM_ADITAMENTO_PATH_ID = AUTO_INTERROGATORIO_EM_ADITAMENTO_PATH + "/{autoInterrogatorioEmAditamentoId}";

    private final AutoInterrogatorioEmAditamentoService autoInterrogatorioEmAditamentoService;

    @GetMapping(AUTO_INTERROGATORIO_EM_ADITAMENTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoInterrogatorioEmAditamentoDTO>>> getAllAutosInterrogatoriosEmAditamentos() {
        return ResponseEntity.ok(autoInterrogatorioEmAditamentoService.getAll());
    }

    @GetMapping(AUTO_INTERROGATORIO_EM_ADITAMENTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoInterrogatorioEmAditamentoById(@PathVariable("autoInterrogatorioEmAditamentoId") Long autoInterrogatorioEmAditamentoId) {
        return ResponseEntity.ok(autoInterrogatorioEmAditamentoService.getById(autoInterrogatorioEmAditamentoId));
    }

    @PostMapping(AUTO_INTERROGATORIO_EM_ADITAMENTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoInterrogatorioEmAditamento(@Validated @RequestBody AutoInterrogatorioEmAditamentoDTO dto) {
        return ResponseEntity.ok(autoInterrogatorioEmAditamentoService.create(dto));
    }

    @PostMapping(AUTO_INTERROGATORIO_EM_ADITAMENTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoInterrogatorioEmAditamentoById(@RequestBody AutoInterrogatorioEmAditamentoDTO dto, @PathVariable("autoInterrogatorioEmAditamentoId") Long autoInterrogatorioEmAditamentoId) {
        return ResponseEntity.ok(autoInterrogatorioEmAditamentoService.updateById(dto, autoInterrogatorioEmAditamentoId));
    }

    @DeleteMapping(AUTO_INTERROGATORIO_EM_ADITAMENTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoInterrogatorioEmAditamentoById(@PathVariable("autoInterrogatorioEmAditamentoId") Long autoInterrogatorioEmAditamentoId) {
        return ResponseEntity.ok(autoInterrogatorioEmAditamentoService.deleteById(autoInterrogatorioEmAditamentoId));
    }
}
