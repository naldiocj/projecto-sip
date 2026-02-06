package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.PeritoExameDirectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.PeritoExameDirectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeritoExameDirectoController {
    private final static String PERITO_EXAME_DIRECTO_PATH = "/api/v1/peritos-exames-directos";
    private final static String PERITO_EXAME_DIRECTO_PATH_ID = PERITO_EXAME_DIRECTO_PATH + "/{peritoExameDirectoId}";

    private final PeritoExameDirectoService peritoExameDirectoService;

    @GetMapping(PERITO_EXAME_DIRECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<PeritoExameDirectoDTO>>> getAllPeritosExamesDirectos() {
        return ResponseEntity.ok(peritoExameDirectoService.getAll());
    }

    @GetMapping(PERITO_EXAME_DIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getPeritoExameDirectoById(@PathVariable("peritoExameDirectoId") Long peritoExameDirectoId) {
        return ResponseEntity.ok(peritoExameDirectoService.getById(peritoExameDirectoId));
    }

    @PostMapping(PERITO_EXAME_DIRECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createPeritoExameDirecto(@Validated @RequestBody PeritoExameDirectoDTO dto) {
        return ResponseEntity.ok(peritoExameDirectoService.create(dto));
    }

    @PostMapping(PERITO_EXAME_DIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updatePeritoExameDirectoById(@RequestBody PeritoExameDirectoDTO dto, @PathVariable("peritoExameDirectoId") Long peritoExameDirectoId) {
        return ResponseEntity.ok(peritoExameDirectoService.updateById(dto, peritoExameDirectoId));
    }

    @DeleteMapping(PERITO_EXAME_DIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deletePeritoExameDirectoById(@PathVariable("peritoExameDirectoId") Long peritoExameDirectoId) {
        return ResponseEntity.ok(peritoExameDirectoService.deleteById(peritoExameDirectoId));
    }
}
