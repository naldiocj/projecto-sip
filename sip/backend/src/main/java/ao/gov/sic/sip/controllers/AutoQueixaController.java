package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoQueixaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoQueixaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoQueixaController {
    private final static String AUTO_QUEIXA_PATH = "/api/v1/autos-queixas";
    private final static String AUTO_QUEIXA_PATH_ID = AUTO_QUEIXA_PATH + "/{autoQueixaId}";

    private final AutoQueixaService autoQueixaService;

    @GetMapping(AUTO_QUEIXA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<AutoQueixaDTO>>> getAllAutosQueixas() {
        return ResponseEntity.ok(autoQueixaService.getAll());
    }

    @GetMapping(AUTO_QUEIXA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getAutoQueixaById(@PathVariable("autoQueixaId") Long autoQueixaId) {
        return ResponseEntity.ok(autoQueixaService.getById(autoQueixaId));
    }

    @PostMapping(AUTO_QUEIXA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoQueixa(@Validated @RequestBody AutoQueixaDTO dto) {
        return ResponseEntity.ok(autoQueixaService.create(dto));
    }

    @PostMapping(AUTO_QUEIXA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoQueixaById(@RequestBody AutoQueixaDTO dto, @PathVariable("autoQueixaId") Long autoQueixaId) {
        return ResponseEntity.ok(autoQueixaService.updateById(dto, autoQueixaId));
    }

    @DeleteMapping(AUTO_QUEIXA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoQueixaById(@PathVariable("autoQueixaId") Long autoQueixaId) {
        return ResponseEntity.ok(autoQueixaService.deleteById(autoQueixaId));
    }
}
