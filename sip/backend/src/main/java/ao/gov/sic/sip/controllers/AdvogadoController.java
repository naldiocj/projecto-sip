package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AdvogadoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AdvogadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdvogadoController {
    private final static String ADVOGADO_PATH = "/api/v1/advogados";
    private final static String ADVOGADO_PATH_ID = ADVOGADO_PATH + "/{advogadoId}";

    private final AdvogadoService advogadoService;

    @GetMapping(ADVOGADO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AdvogadoDTO>>> getAllAdvogados() {
        return ResponseEntity.ok(advogadoService.getAll());
    }

    @GetMapping(ADVOGADO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAdvogadoById(@PathVariable("advogadoId") Long advogadoId) {
        return ResponseEntity.ok(advogadoService.getById(advogadoId));
    }

    @PostMapping(ADVOGADO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAdvogado(@Validated @RequestBody AdvogadoDTO dto) {
        return ResponseEntity.ok(advogadoService.create(dto));
    }

    @PostMapping(ADVOGADO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAdvogadoById(@RequestBody AdvogadoDTO dto, @PathVariable("advogadoId") Long advogadoId) {
        return ResponseEntity.ok(advogadoService.updateById(dto, advogadoId));
    }

    @DeleteMapping(ADVOGADO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAdvogadoById(@PathVariable("advogadoId") Long advogadoId) {
        return ResponseEntity.ok(advogadoService.deleteById(advogadoId));
    }
}
