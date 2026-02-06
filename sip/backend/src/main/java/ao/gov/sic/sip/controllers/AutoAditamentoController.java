package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoAditamentoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoAditamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoAditamentoController {
    private final static String AUTO_ADITAMENTO_PATH = "/api/v1/autos-aditamentos";
    private final static String AUTO_ADITAMENTO_PATH_ID = AUTO_ADITAMENTO_PATH + "/{autoAditamentoId}";

    private final AutoAditamentoService autoAditamentoService;

    @GetMapping(AUTO_ADITAMENTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoAditamentoDTO>>> getAllAutosAditamentos() {
        return ResponseEntity.ok(autoAditamentoService.getAll());
    }

    @GetMapping(AUTO_ADITAMENTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoAditamentoById(@PathVariable("autoAditamentoId") Long autoAditamentoId) {
        return ResponseEntity.ok(autoAditamentoService.getById(autoAditamentoId));
    }

    @PostMapping(AUTO_ADITAMENTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoAditamento(@Validated @RequestBody AutoAditamentoDTO dto) {
        return ResponseEntity.ok(autoAditamentoService.create(dto));
    }

    @PostMapping(AUTO_ADITAMENTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoAditamentoById(@RequestBody AutoAditamentoDTO dto, @PathVariable("autoAditamentoId") Long autoAditamentoId) {
        return ResponseEntity.ok(autoAditamentoService.updateById(dto, autoAditamentoId));
    }

    @DeleteMapping(AUTO_ADITAMENTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoAditamentoById(@PathVariable("autoAditamentoId") Long autoAditamentoId) {
        return ResponseEntity.ok(autoAditamentoService.deleteById(autoAditamentoId));
    }
}
