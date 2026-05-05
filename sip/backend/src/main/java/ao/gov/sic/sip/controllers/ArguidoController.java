package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ArguidoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.ArguidoService;
import ao.gov.sic.sip.utils.ProcessoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArguidoController {
    private final static String ARGUIDO_PATH = "/api/v1/arguidos";
    private final static String ARGUIDO_PATH_ID = ARGUIDO_PATH + "/{arguidoId}";

    private final ArguidoService arguidoService;

    @GetMapping(ARGUIDO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PGR', 'SECRETARIA', 'SECRETARIA_GERAL', 'PIQUETE')")
    public ResponseEntity<Response<List<ArguidoDTO>>> getAllArguidos() {
        return ResponseEntity.ok(arguidoService.getAll());
    }

    @GetMapping(ARGUIDO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PGR', 'SECRETARIA', 'SECRETARIA_GERAL')")
    public ResponseEntity<Response<?>> getArguidoById(@PathVariable("arguidoId") Long arguidoId) {
        return ResponseEntity.ok(arguidoService.getById(arguidoId));
    }

    @PostMapping(ARGUIDO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR')")
    public ResponseEntity<?> createArguido(@Validated @RequestBody ArguidoDTO dto) {
        dto.setProcessoNumero(ProcessoUtils.cvtToProcessoNumero(dto.getProcessoNumero()));
        return ResponseEntity.ok(arguidoService.create(dto));
    }

    @PostMapping(ARGUIDO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR')")
    public ResponseEntity<?> updateArguidoById(@RequestBody ArguidoDTO dto, @PathVariable("arguidoId") Long arguidoId) {
        return ResponseEntity.ok(arguidoService.updateById(dto, arguidoId));
    }

    @DeleteMapping(ARGUIDO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR')")
    public ResponseEntity<?> deleteArguidoById(@PathVariable("arguidoId") Long arguidoId) {
        return ResponseEntity.ok(arguidoService.deleteById(arguidoId));
    }
}
