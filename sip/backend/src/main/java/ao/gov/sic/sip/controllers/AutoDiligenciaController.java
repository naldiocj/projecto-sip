package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoDiligenciaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoDiligenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoDiligenciaController {
    private final static String AUTO_DILIGENCIA_PATH = "/api/v1/autos-diligencias";
    private final static String AUTO_DILIGENCIA_PATH_ID = AUTO_DILIGENCIA_PATH + "/{autoDiligenciaId}";

    private final AutoDiligenciaService autoDiligenciaService;

    @GetMapping(AUTO_DILIGENCIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<AutoDiligenciaDTO>>> getAllAutosDiligencias() {
        return ResponseEntity.ok(autoDiligenciaService.getAll());
    }

    @GetMapping(AUTO_DILIGENCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getAutoDiligenciaById(@PathVariable("autoDiligenciaId") Long autoDiligenciaId) {
        return ResponseEntity.ok(autoDiligenciaService.getById(autoDiligenciaId));
    }

    @PostMapping(AUTO_DILIGENCIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoDiligencia(@Validated @RequestBody AutoDiligenciaDTO dto) {
        return ResponseEntity.ok(autoDiligenciaService.create(dto));
    }

    @PostMapping(AUTO_DILIGENCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoDiligenciaById(@RequestBody AutoDiligenciaDTO dto, @PathVariable("autoDiligenciaId") Long autoDiligenciaId) {
        return ResponseEntity.ok(autoDiligenciaService.updateById(dto, autoDiligenciaId));
    }

    @DeleteMapping(AUTO_DILIGENCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoDiligenciaById(@PathVariable("autoDiligenciaId") Long autoDiligenciaId) {
        return ResponseEntity.ok(autoDiligenciaService.deleteById(autoDiligenciaId));
    }
}
