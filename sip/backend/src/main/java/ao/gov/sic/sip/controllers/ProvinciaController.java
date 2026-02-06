package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ProvinciaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.ProvinciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProvinciaController {
    private final static String PROVINCIA_PATH = "/api/v1/provincias";
    private final static String PROVINCIA_PATH_ID = PROVINCIA_PATH + "/{provinciaId}";

    private final ProvinciaService provinciaService;

    @GetMapping(PROVINCIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<ProvinciaDTO>>> getAllProvincias() {
        return ResponseEntity.ok(provinciaService.getAll());
    }

    @GetMapping(PROVINCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getProvinciaById(@PathVariable("provinciaId") Long provinciaId) {
        return ResponseEntity.ok(provinciaService.getById(provinciaId));
    }

    @PostMapping(PROVINCIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createProvincia(@Validated @RequestBody ProvinciaDTO dto) {
        return ResponseEntity.ok(provinciaService.create(dto));
    }

    @PostMapping(PROVINCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateProvinciaById(@RequestBody ProvinciaDTO dto, @PathVariable("provinciaId") Long provinciaId) {
        return ResponseEntity.ok(provinciaService.updateById(dto, provinciaId));
    }

    @DeleteMapping(PROVINCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteProvinciaById(@PathVariable("provinciaId") Long provinciaId) {
        return ResponseEntity.ok(provinciaService.deleteById(provinciaId));
    }
}
