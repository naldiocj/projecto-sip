package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.TermoEntregaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.TermoEntregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TermoEntregaController {
    private final static String TERMO_ENTREGA_PATH = "/api/v1/termos-entregas";
    private final static String TERMO_ENTREGA_PATH_ID = TERMO_ENTREGA_PATH + "/{termoEntregaId}";

    private final TermoEntregaService termoEntregaService;

    @GetMapping(TERMO_ENTREGA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<TermoEntregaDTO>>> getAllTermosEntregas() {
        return ResponseEntity.ok(termoEntregaService.getAll());
    }

    @GetMapping(TERMO_ENTREGA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getTermoEntregaById(@PathVariable("termoEntregaId") Long termoEntregaId) {
        return ResponseEntity.ok(termoEntregaService.getById(termoEntregaId));
    }

    @PostMapping(TERMO_ENTREGA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createTermoEntrega(@Validated @RequestBody TermoEntregaDTO dto) {
        return ResponseEntity.ok(termoEntregaService.create(dto));
    }

    @PostMapping(TERMO_ENTREGA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateTermoEntregaById(@RequestBody TermoEntregaDTO dto, @PathVariable("termoEntregaId") Long termoEntregaId) {
        return ResponseEntity.ok(termoEntregaService.updateById(dto, termoEntregaId));
    }

    @DeleteMapping(TERMO_ENTREGA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteTermoEntregaById(@PathVariable("termoEntregaId") Long termoEntregaId) {
        return ResponseEntity.ok(termoEntregaService.deleteById(termoEntregaId));
    }
}
