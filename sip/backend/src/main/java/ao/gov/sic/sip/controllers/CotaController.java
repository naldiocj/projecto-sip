package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.CotaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.CotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CotaController {
    private final static String COTA_PATH = "/api/v1/cotas";
    private final static String COTA_PATH_ID = COTA_PATH + "/{cotaId}";

    private final CotaService cotaService;

    @GetMapping(COTA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<CotaDTO>>> getAllCotas() {
        return ResponseEntity.ok(cotaService.getAll());
    }

    @GetMapping(COTA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getCotaById(@PathVariable("cotaId") Long cotaId) {
        return ResponseEntity.ok(cotaService.getById(cotaId));
    }

    @PostMapping(COTA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createCota(@Validated @RequestBody CotaDTO dto) {
        return ResponseEntity.ok(cotaService.create(dto));
    }

    @PostMapping(COTA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateCotaById(@RequestBody CotaDTO dto, @PathVariable("cotaId") Long cotaId) {
        return ResponseEntity.ok(cotaService.updateById(dto, cotaId));
    }

    @DeleteMapping(COTA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteCotaById(@PathVariable("cotaId") Long cotaId) {
        return ResponseEntity.ok(cotaService.deleteById(cotaId));
    }
}
