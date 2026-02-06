package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.QueixosoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.QueixosoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QueixosoController {
    private final static String QUEIXOSO_PATH = "/api/v1/queixosos";
    private final static String QUEIXOSO_PATH_ID = QUEIXOSO_PATH + "/{queixosoId}";

    private final QueixosoService queixosoService;

    @GetMapping(QUEIXOSO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<QueixosoDTO>>> getAllQueixosos() {
        return ResponseEntity.ok(queixosoService.getAll());
    }

    @GetMapping(QUEIXOSO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getQueixosoById(@PathVariable("queixosoId") Long queixosoId) {
        return ResponseEntity.ok(queixosoService.getById(queixosoId));
    }

    @PostMapping(QUEIXOSO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createQueixoso(@Validated @RequestBody QueixosoDTO dto) {
        return ResponseEntity.ok(queixosoService.create(dto));
    }

    @PostMapping(QUEIXOSO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateQueixosoById(@RequestBody QueixosoDTO dto, @PathVariable("queixosoId") Long queixosoId) {
        return ResponseEntity.ok(queixosoService.updateById(dto, queixosoId));
    }

    @DeleteMapping(QUEIXOSO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteQueixosoById(@PathVariable("queixosoId") Long queixosoId) {
        return ResponseEntity.ok(queixosoService.deleteById(queixosoId));
    }
}
