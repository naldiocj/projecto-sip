package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoCorpoDelitoIndirectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoCorpoDelitoIndirectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoCorpoDelitoIndirectoController {
    private final static String AUTO_CORPO_DELITO_INDIRECTO_PATH = "/api/v1/autos-corpo-delitos-indirectos";
    private final static String AUTO_CORPO_DELITO_INDIRECTO_PATH_ID = AUTO_CORPO_DELITO_INDIRECTO_PATH + "/{autoCorpoDelitoIndirectoId}";

    private final AutoCorpoDelitoIndirectoService autoCorpoDelitoIndirectoService;

    @GetMapping(AUTO_CORPO_DELITO_INDIRECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<AutoCorpoDelitoIndirectoDTO>>> getAllAutosCorpoDelitosIndirectos() {
        return ResponseEntity.ok(autoCorpoDelitoIndirectoService.getAll());
    }

    @GetMapping(AUTO_CORPO_DELITO_INDIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getAutoCorpoDelitoIndirectoById(@PathVariable("autoCorpoDelitoIndirectoId") Long autoCorpoDelitoIndirectoId) {
        return ResponseEntity.ok(autoCorpoDelitoIndirectoService.getById(autoCorpoDelitoIndirectoId));
    }

    @PostMapping(AUTO_CORPO_DELITO_INDIRECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoCorpoDelitoIndirecto(@Validated @RequestBody AutoCorpoDelitoIndirectoDTO dto) {
        return ResponseEntity.ok(autoCorpoDelitoIndirectoService.create(dto));
    }

    @PostMapping(AUTO_CORPO_DELITO_INDIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoCorpoDelitoIndirectoById(@RequestBody AutoCorpoDelitoIndirectoDTO dto, @PathVariable("autoCorpoDelitoIndirectoId") Long autoCorpoDelitoIndirectoId) {
        return ResponseEntity.ok(autoCorpoDelitoIndirectoService.updateById(dto, autoCorpoDelitoIndirectoId));
    }

    @DeleteMapping(AUTO_CORPO_DELITO_INDIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoCorpoDelitoIndirectoById(@PathVariable("autoCorpoDelitoIndirectoId") Long autoCorpoDelitoIndirectoId) {
        return ResponseEntity.ok(autoCorpoDelitoIndirectoService.deleteById(autoCorpoDelitoIndirectoId));
    }
}
