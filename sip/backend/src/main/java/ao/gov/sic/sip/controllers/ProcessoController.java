package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ProcessoDTO;
import ao.gov.sic.sip.dtos.ProcessoResDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.ProcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProcessoController {
    private final static String PROCESSO_PATH = "/api/v1/processos";
    private final static String PROCESSO_PATH_ID = PROCESSO_PATH + "/{processoId}";
    private final static String PROCESSO_PATH_NUMERO = PROCESSO_PATH + "/{processoNumero}";

    private final ProcessoService processoService;

    @GetMapping(PROCESSO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<ProcessoResDTO>>> getAllProcessos(@Param("term") String term) {
        return ResponseEntity.ok(processoService.getAll(term));
    }

    @GetMapping(PROCESSO_PATH_NUMERO)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getProcessoByNumero(@PathVariable("processoNumero") String numero) {
        return ResponseEntity.ok(processoService.getByNumero(numero));
    }

    @PostMapping(PROCESSO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR', 'PIQUETE')")
    public ResponseEntity<?> createProcesso(@Validated @RequestBody ProcessoDTO dto) {
        return ResponseEntity.ok(processoService.create(dto));
    }

    @PutMapping(PROCESSO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR', 'PIQUETE')")
    public ResponseEntity<?> updateProcessoById(@RequestBody ProcessoDTO dto, @PathVariable("processoId") Long processoId) {
        return ResponseEntity.ok(processoService.updateById(dto, processoId));
    }

    @DeleteMapping(PROCESSO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteProcessoById(@PathVariable("processoId") Long processoId) {
        return ResponseEntity.ok(processoService.deleteById(processoId));
    }
}
