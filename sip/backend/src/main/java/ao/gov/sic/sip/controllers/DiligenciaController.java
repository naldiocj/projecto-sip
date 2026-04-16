package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.DiligenciaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.DiligenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diligencias")
@RequiredArgsConstructor
public class DiligenciaController {

    private final DiligenciaService diligenciaService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PGR')")
    public ResponseEntity<Response<List<DiligenciaDTO>>> getAll() {
        Response<List<DiligenciaDTO>> response = diligenciaService.getAll();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PGR')")
    public ResponseEntity<Response<DiligenciaDTO>> getById(@PathVariable Long id) {
        Response<DiligenciaDTO> response = diligenciaService.getById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/processo/{processoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PGR')")
    public ResponseEntity<Response<List<DiligenciaDTO>>> getByProcessoId(@PathVariable Long processoId) {
        Response<List<DiligenciaDTO>> response = diligenciaService.getAllByProcessoId(processoId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/processo/numero/{numero}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PGR')")
    public ResponseEntity<Response<List<DiligenciaDTO>>> getByProcessoNumero(@PathVariable String numero) {
        Response<List<DiligenciaDTO>> response = diligenciaService.getAllByProcessoNumero(numero);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR', 'PIQUETE')")
    public ResponseEntity<Response<?>> create(@RequestBody DiligenciaDTO dto) {
        Response<?> response = diligenciaService.create(dto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR')")
    public ResponseEntity<Response<?>> update(@PathVariable Long id, @RequestBody DiligenciaDTO dto) {
        Response<?> response = diligenciaService.updateById(dto, id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Response<?>> delete(@PathVariable Long id) {
        Response<?> response = diligenciaService.deleteById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}