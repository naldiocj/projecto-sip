package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.PedidoComparenciaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.PedidoComparenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PedidoComparenciaController {
    private final static String PEDIDO_COMPARENCIA_PATH = "/api/v1/pedidos-comparencias";
    private final static String PEDIDO_COMPARENCIA_PATH_ID = PEDIDO_COMPARENCIA_PATH + "/{pedidoComparenciaId}";

    private final PedidoComparenciaService pedidoComparenciaService;

    @GetMapping(PEDIDO_COMPARENCIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<PedidoComparenciaDTO>>> getAllPedidosComparencias() {
        return ResponseEntity.ok(pedidoComparenciaService.getAll());
    }

    @GetMapping(PEDIDO_COMPARENCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getPedidoComparenciaById(@PathVariable("pedidoComparenciaId") Long pedidoComparenciaId) {
        return ResponseEntity.ok(pedidoComparenciaService.getById(pedidoComparenciaId));
    }

    @PostMapping(PEDIDO_COMPARENCIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createPedidoComparencia(@Validated @RequestBody PedidoComparenciaDTO dto) {
        return ResponseEntity.ok(pedidoComparenciaService.create(dto));
    }

    @PostMapping(PEDIDO_COMPARENCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updatePedidoComparenciaById(@RequestBody PedidoComparenciaDTO dto, @PathVariable("pedidoComparenciaId") Long pedidoComparenciaId) {
        return ResponseEntity.ok(pedidoComparenciaService.updateById(dto, pedidoComparenciaId));
    }

    @DeleteMapping(PEDIDO_COMPARENCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deletePedidoComparenciaById(@PathVariable("pedidoComparenciaId") Long pedidoComparenciaId) {
        return ResponseEntity.ok(pedidoComparenciaService.deleteById(pedidoComparenciaId));
    }
}
