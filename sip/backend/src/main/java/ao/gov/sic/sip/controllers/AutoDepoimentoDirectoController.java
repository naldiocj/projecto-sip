package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoDepoimentoDirectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoDepoimentoDirectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoDepoimentoDirectoController {
    private final static String AUTO_DEPOIMENTO_DIRECTO_PATH = "/api/v1/autos-depoimentos-directos";
    private final static String AUTO_DEPOIMENTO_DIRECTO_PATH_ID = AUTO_DEPOIMENTO_DIRECTO_PATH + "/{autoDepoimentoDirectoId}";

    private final AutoDepoimentoDirectoService autoDepoimentoDirectoService;

    @GetMapping(AUTO_DEPOIMENTO_DIRECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoDepoimentoDirectoDTO>>> getAllAutosDepoimentosDirectos() {
        return ResponseEntity.ok(autoDepoimentoDirectoService.getAll());
    }

    @GetMapping(AUTO_DEPOIMENTO_DIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoDepoimentoDirectoById(@PathVariable("autoDepoimentoDirectoId") Long autoDepoimentoDirectoId) {
        return ResponseEntity.ok(autoDepoimentoDirectoService.getById(autoDepoimentoDirectoId));
    }

    @PostMapping(AUTO_DEPOIMENTO_DIRECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoDepoimentoDirecto(@Validated @RequestBody AutoDepoimentoDirectoDTO dto) {
        return ResponseEntity.ok(autoDepoimentoDirectoService.create(dto));
    }

    @PostMapping(AUTO_DEPOIMENTO_DIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoDepoimentoDirectoById(@RequestBody AutoDepoimentoDirectoDTO dto, @PathVariable("autoDepoimentoDirectoId") Long autoDepoimentoDirectoId) {
        return ResponseEntity.ok(autoDepoimentoDirectoService.updateById(dto, autoDepoimentoDirectoId));
    }

    @DeleteMapping(AUTO_DEPOIMENTO_DIRECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoDepoimentoDirectoById(@PathVariable("autoDepoimentoDirectoId") Long autoDepoimentoDirectoId) {
        return ResponseEntity.ok(autoDepoimentoDirectoService.deleteById(autoDepoimentoDirectoId));
    }
}
