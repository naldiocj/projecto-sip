package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.CapaProcessoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.CapaProcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CapaProcessoController {
    private final static String CAPA_PROCESSO_PATH = "/api/v1/capas-processos";
    private final static String CAPA_PROCESSO_PATH_ID = CAPA_PROCESSO_PATH + "/{capaProcessoId}";

    private final CapaProcessoService capaProcessoService;

    @GetMapping(CAPA_PROCESSO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<CapaProcessoDTO>>> getAllCapasProcessos() {
        return ResponseEntity.ok(capaProcessoService.getAll());
    }

    @GetMapping(CAPA_PROCESSO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getCapaProcessoById(@PathVariable("capaProcessoId") Long capaProcessoId) {
        return ResponseEntity.ok(capaProcessoService.getById(capaProcessoId));
    }

    @PostMapping(CAPA_PROCESSO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createCapaProcesso(@Validated @RequestBody CapaProcessoDTO dto) {
        return ResponseEntity.ok(capaProcessoService.create(dto));
    }

    @PostMapping(CAPA_PROCESSO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateCapaProcessoById(@RequestBody CapaProcessoDTO dto, @PathVariable("capaProcessoId") Long capaProcessoId) {
        return ResponseEntity.ok(capaProcessoService.updateById(dto, capaProcessoId));
    }

    @DeleteMapping(CAPA_PROCESSO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteCapaProcessoById(@PathVariable("capaProcessoId") Long capaProcessoId) {
        return ResponseEntity.ok(capaProcessoService.deleteById(capaProcessoId));
    }
}
