package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.CartaPrecatoriaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.CartaPrecatoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartaPrecatoriaController {
    private final static String CARTA_PRECATORIA_PATH = "/api/v1/cartas-precatorias";
    private final static String CARTA_PRECATORIA_PATH_ID = CARTA_PRECATORIA_PATH + "/{cartaPrecatoriaId}";

    private final CartaPrecatoriaService cartaPrecatoriaService;

    @GetMapping(CARTA_PRECATORIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<CartaPrecatoriaDTO>>> getAllCartasPrecatorias() {
        return ResponseEntity.ok(cartaPrecatoriaService.getAll());
    }

    @GetMapping(CARTA_PRECATORIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getCartaPrecatoriaById(@PathVariable("cartaPrecatoriaId") Long cartaPrecatoriaId) {
        return ResponseEntity.ok(cartaPrecatoriaService.getById(cartaPrecatoriaId));
    }

    @PostMapping(CARTA_PRECATORIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createCartaPrecatoria(@Validated @RequestBody CartaPrecatoriaDTO dto) {
        return ResponseEntity.ok(cartaPrecatoriaService.create(dto));
    }

    @PostMapping(CARTA_PRECATORIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateCartaPrecatoriaById(@RequestBody CartaPrecatoriaDTO dto, @PathVariable("cartaPrecatoriaId") Long cartaPrecatoriaId) {
        return ResponseEntity.ok(cartaPrecatoriaService.updateById(dto, cartaPrecatoriaId));
    }

    @DeleteMapping(CARTA_PRECATORIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteCartaPrecatoriaById(@PathVariable("cartaPrecatoriaId") Long cartaPrecatoriaId) {
        return ResponseEntity.ok(cartaPrecatoriaService.deleteById(cartaPrecatoriaId));
    }
}
