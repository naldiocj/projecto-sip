package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoReconhecimentoFisicoDirectoObjectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoReconhecimentoFisicoDirectoObjectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoReconhecimentoFisicoDirectoObjectoController {
    private final static String AUTO_RECONHECIMENTO_FISICO_DIRECTO_OBJECTO_PATH = "/api/v1/autos-reconhecimento-fisico-directo-objectos";
    private final static String AUTO_RECONHECIMENTO_FISICO_DIRECTO_OBJECTO_PATH_ID = AUTO_RECONHECIMENTO_FISICO_DIRECTO_OBJECTO_PATH + "/{autoReconhecimentoFisicoDirectoObjectoId}";

    private final AutoReconhecimentoFisicoDirectoObjectoService autoReconhecimentoFisicoDirectoObjectoService;

    @GetMapping(AUTO_RECONHECIMENTO_FISICO_DIRECTO_OBJECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoReconhecimentoFisicoDirectoObjectoDTO>>> getAllAutosReconhecimentoFisicoDirectoObjectos() {
        return ResponseEntity.ok(autoReconhecimentoFisicoDirectoObjectoService.getAll());
    }

    @GetMapping(AUTO_RECONHECIMENTO_FISICO_DIRECTO_OBJECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoReconhecimentoFisicoDirectoObjectoById(@PathVariable("autoReconhecimentoFisicoDirectoObjectoId") Long autoReconhecimentoFisicoDirectoObjectoId) {
        return ResponseEntity.ok(autoReconhecimentoFisicoDirectoObjectoService.getById(autoReconhecimentoFisicoDirectoObjectoId));
    }

    @PostMapping(AUTO_RECONHECIMENTO_FISICO_DIRECTO_OBJECTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoReconhecimentoFisicoDirectoObjecto(@Validated @RequestBody AutoReconhecimentoFisicoDirectoObjectoDTO dto) {
        return ResponseEntity.ok(autoReconhecimentoFisicoDirectoObjectoService.create(dto));
    }

    @PostMapping(AUTO_RECONHECIMENTO_FISICO_DIRECTO_OBJECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoReconhecimentoFisicoDirectoObjectoById(@RequestBody AutoReconhecimentoFisicoDirectoObjectoDTO dto, @PathVariable("autoReconhecimentoFisicoDirectoObjectoId") Long autoReconhecimentoFisicoDirectoObjectoId) {
        return ResponseEntity.ok(autoReconhecimentoFisicoDirectoObjectoService.updateById(dto, autoReconhecimentoFisicoDirectoObjectoId));
    }

    @DeleteMapping(AUTO_RECONHECIMENTO_FISICO_DIRECTO_OBJECTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoReconhecimentoFisicoDirectoObjectoById(@PathVariable("autoReconhecimentoFisicoDirectoObjectoId") Long autoReconhecimentoFisicoDirectoObjectoId) {
        return ResponseEntity.ok(autoReconhecimentoFisicoDirectoObjectoService.deleteById(autoReconhecimentoFisicoDirectoObjectoId));
    }
}
