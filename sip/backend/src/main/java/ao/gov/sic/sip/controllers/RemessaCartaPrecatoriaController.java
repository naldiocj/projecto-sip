package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.RemessaCartaPrecatoriaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.RemessaCartaPrecatoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RemessaCartaPrecatoriaController {
    private final static String REMESSA_CARTA_PRECATORIA_PATH = "/api/v1/remessas-cartas-precatorias";
    private final static String REMESSA_CARTA_PRECATORIA_PATH_ID = REMESSA_CARTA_PRECATORIA_PATH + "/{remessaCartaPrecatoriaId}";

    private final RemessaCartaPrecatoriaService remessaCartaPrecatoriaService;

    @GetMapping(REMESSA_CARTA_PRECATORIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<RemessaCartaPrecatoriaDTO>>> getAllRemessasCartasPrecatorias() {
        return ResponseEntity.ok(remessaCartaPrecatoriaService.getAll());
    }

    @GetMapping(REMESSA_CARTA_PRECATORIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getRemessaCartaPrecatoriaById(@PathVariable("remessaCartaPrecatoriaId") Long remessaCartaPrecatoriaId) {
        return ResponseEntity.ok(remessaCartaPrecatoriaService.getById(remessaCartaPrecatoriaId));
    }

    @PostMapping(REMESSA_CARTA_PRECATORIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createRemessaCartaPrecatoria(@Validated @RequestBody RemessaCartaPrecatoriaDTO dto) {
        return ResponseEntity.ok(remessaCartaPrecatoriaService.create(dto));
    }

    @PostMapping(REMESSA_CARTA_PRECATORIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateRemessaCartaPrecatoriaById(@RequestBody RemessaCartaPrecatoriaDTO dto, @PathVariable("remessaCartaPrecatoriaId") Long remessaCartaPrecatoriaId) {
        return ResponseEntity.ok(remessaCartaPrecatoriaService.updateById(dto, remessaCartaPrecatoriaId));
    }

    @DeleteMapping(REMESSA_CARTA_PRECATORIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteRemessaCartaPrecatoriaById(@PathVariable("remessaCartaPrecatoriaId") Long remessaCartaPrecatoriaId) {
        return ResponseEntity.ok(remessaCartaPrecatoriaService.deleteById(remessaCartaPrecatoriaId));
    }
}
