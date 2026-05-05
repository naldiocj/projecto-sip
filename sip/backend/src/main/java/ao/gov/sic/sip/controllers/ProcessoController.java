package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ProcessoDTO;
import ao.gov.sic.sip.dtos.ProcessoResDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.UpdateProcessoDTO;
import ao.gov.sic.sip.services.ProcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/processos")
@RequiredArgsConstructor
public class ProcessoController {
    private final ProcessoService processoService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PGR', 'SECRETARIA', 'SECRETARIA_GERAL', 'PIQUETE')")
    public ResponseEntity<Response<List<ProcessoResDTO>>> getAllProcessos(
            @RequestParam(name = "term", required = false) String term) {
        Response<List<ProcessoResDTO>> response = processoService.getAll(term);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{processoNumero}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PGR', 'SECRETARIA', 'SECRETARIA_GERAL', 'PIQUETE')")
    public ResponseEntity<Response<?>> getProcessoByNumero(
            @PathVariable("processoNumero") String processoNumero) {
        Response<?> response = processoService.getByNumero(processoNumero);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SECRETARIA_GERAL')")
    public ResponseEntity<Response<?>> createProcesso(@Validated @RequestBody ProcessoDTO dto) {
        Response<?> response = processoService.create(dto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{processoId}")
    @PreAuthorize("hasAnyAuthority('SECRETARIA', 'SECRETARIA_GERAL')")
    public ResponseEntity<Response<?>> updateProcessoById(
            @RequestBody ProcessoDTO dto, 
            @PathVariable("processoId") Long processoId) {
        Response<?> response = processoService.updateById(dto, processoId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{processoId}")
    @PreAuthorize("hasAnyAuthority('SECRETARIA', 'SECRETARIA_GERAL')")
    public ResponseEntity<Response<?>> deleteProcessoById(
            @PathVariable("processoId") Long processoId) {
        Response<?> response = processoService.deleteById(processoId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{processoId}")
    @PreAuthorize("hasAnyAuthority('SECRETARIA', 'SECRETARIA_GERAL')")
    public ResponseEntity<Response<?>> patchProcessoById(@PathVariable("processoId") Long processoId, @RequestBody UpdateProcessoDTO dto) {
        processoService.patchProcessoById(processoId, dto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
