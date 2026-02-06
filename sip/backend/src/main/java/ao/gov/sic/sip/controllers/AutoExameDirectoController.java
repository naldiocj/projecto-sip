package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoExameDirectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoExameDirectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoExameDirectoController {
    private final static String AUTO_EXAME_DIRECTO_PATH = "/api/v1/autos-exames-directos";
    private final static String AUTO_EXAME_DIRECTO_PATH_ID = AUTO_EXAME_DIRECTO_PATH + "/{autoExameDirectoId}";

    private final AutoExameDirectoService autoExameDirectoService;

    @GetMapping(AUTO_EXAME_DIRECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoExameDirectoDTO>>> getAllAutosExamesDirectos() {
        return ResponseEntity.ok(autoExameDirectoService.getAll());
    }

    @GetMapping(AUTO_EXAME_DIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoExameDirectoById(@PathVariable("autoExameDirectoId") Long autoExameDirectoId) {
        return ResponseEntity.ok(autoExameDirectoService.getById(autoExameDirectoId));
    }

    @PostMapping(AUTO_EXAME_DIRECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoExameDirecto(@Validated @RequestBody AutoExameDirectoDTO dto) {
        return ResponseEntity.ok(autoExameDirectoService.create(dto));
    }

    @PostMapping(AUTO_EXAME_DIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoExameDirectoById(@RequestBody AutoExameDirectoDTO dto, @PathVariable("autoExameDirectoId") Long autoExameDirectoId) {
        return ResponseEntity.ok(autoExameDirectoService.updateById(dto, autoExameDirectoId));
    }

    @DeleteMapping(AUTO_EXAME_DIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoExameDirectoById(@PathVariable("autoExameDirectoId") Long autoExameDirectoId) {
        return ResponseEntity.ok(autoExameDirectoService.deleteById(autoExameDirectoId));
    }
}
