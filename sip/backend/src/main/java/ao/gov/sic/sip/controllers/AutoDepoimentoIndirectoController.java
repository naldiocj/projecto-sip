package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoDepoimentoIndirectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoDepoimentoIndirectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoDepoimentoIndirectoController {
    private final static String AUTO_DEPOIMENTO_INDIRECTO_PATH = "/api/v1/autos-depoimentos-indirectos";
    private final static String AUTO_DEPOIMENTO_INDIRECTO_PATH_ID = AUTO_DEPOIMENTO_INDIRECTO_PATH + "/{autoDepoimentoIndirectoId}";

    private final AutoDepoimentoIndirectoService autoDepoimentoIndirectoService;

    @GetMapping(AUTO_DEPOIMENTO_INDIRECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoDepoimentoIndirectoDTO>>> getAllAutosDepoimentosIndirectos() {
        return ResponseEntity.ok(autoDepoimentoIndirectoService.getAll());
    }

    @GetMapping(AUTO_DEPOIMENTO_INDIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoDepoimentoIndirectoById(@PathVariable("autoDepoimentoIndirectoId") Long autoDepoimentoIndirectoId) {
        return ResponseEntity.ok(autoDepoimentoIndirectoService.getById(autoDepoimentoIndirectoId));
    }

    @PostMapping(AUTO_DEPOIMENTO_INDIRECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoDepoimentoIndirecto(@Validated @RequestBody AutoDepoimentoIndirectoDTO dto) {
        return ResponseEntity.ok(autoDepoimentoIndirectoService.create(dto));
    }

    @PostMapping(AUTO_DEPOIMENTO_INDIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoDepoimentoIndirectoById(@RequestBody AutoDepoimentoIndirectoDTO dto, @PathVariable("autoDepoimentoIndirectoId") Long autoDepoimentoIndirectoId) {
        return ResponseEntity.ok(autoDepoimentoIndirectoService.updateById(dto, autoDepoimentoIndirectoId));
    }

    @DeleteMapping(AUTO_DEPOIMENTO_INDIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoDepoimentoIndirectoById(@PathVariable("autoDepoimentoIndirectoId") Long autoDepoimentoIndirectoId) {
        return ResponseEntity.ok(autoDepoimentoIndirectoService.deleteById(autoDepoimentoIndirectoId));
    }
}
