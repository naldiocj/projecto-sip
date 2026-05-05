package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ArguidoAutoQueixaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.ArguidoAutoQueixaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArguidoAutoQueixaController {
    private final static String ARGUIDO_AUTO_QUEIXA_PATH = "/api/v1/arguidos-autos-queixas";
    private final static String ARGUIDO_AUTO_QUEIXA_PATH_ID = ARGUIDO_AUTO_QUEIXA_PATH + "/{arguidoAutoQueixaId}";

    private final ArguidoAutoQueixaService arguidoAutoQueixaService;

    @GetMapping(ARGUIDO_AUTO_QUEIXA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PGR', 'SECRETARIA', 'SECRETARIA_GERAL', 'PIQUETE')")
    public ResponseEntity<Response<List<ArguidoAutoQueixaDTO>>> getAllArguidosAutosQueixas() {
        return ResponseEntity.ok(arguidoAutoQueixaService.getAll());
    }

    @GetMapping(ARGUIDO_AUTO_QUEIXA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getArguidoAutoQueixaById(@PathVariable("arguidoAutoQueixaId") Long arguidoAutoQueixaId) {
        return ResponseEntity.ok(arguidoAutoQueixaService.getById(arguidoAutoQueixaId));
    }

    @PostMapping(ARGUIDO_AUTO_QUEIXA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createArguidoAutoQueixa(@Validated @RequestBody ArguidoAutoQueixaDTO dto) {
        return ResponseEntity.ok(arguidoAutoQueixaService.create(dto));
    }

    @PostMapping(ARGUIDO_AUTO_QUEIXA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateArguidoAutoQueixaById(@RequestBody ArguidoAutoQueixaDTO dto, @PathVariable("arguidoAutoQueixaId") Long arguidoAutoQueixaId) {
        return ResponseEntity.ok(arguidoAutoQueixaService.updateById(dto, arguidoAutoQueixaId));
    }

    @DeleteMapping(ARGUIDO_AUTO_QUEIXA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteArguidoAutoQueixaById(@PathVariable("arguidoAutoQueixaId") Long arguidoAutoQueixaId) {
        return ResponseEntity.ok(arguidoAutoQueixaService.deleteById(arguidoAutoQueixaId));
    }
}
