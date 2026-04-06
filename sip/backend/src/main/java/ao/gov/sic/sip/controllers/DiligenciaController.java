package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.DiligenciaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.DiligenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiligenciaController {
    private final static String DILIGENCIA_PATH = "/api/v1/diligencias";
    private final static String DILIGENCIA_PATH_ID = DILIGENCIA_PATH + "/{diligenciaId}";

    private final DiligenciaService diligenciaService;

    @GetMapping(DILIGENCIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<DiligenciaDTO>>> getAllDiligencias(@Param(value = "numero") String numero) {

        boolean hasNumero = StringUtils.hasText(numero);
        String numeroConvertido = hasNumero ? numero.replaceFirst("-", "/") : null;

        return ResponseEntity.ok(hasNumero ? diligenciaService.getAllByProcessoNumero(numeroConvertido) : diligenciaService.getAll());
    }

    @GetMapping(DILIGENCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getDiligenciaById(@PathVariable("diligenciaId") Long diligenciaId) {
        return ResponseEntity.ok(diligenciaService.getById(diligenciaId));
    }

    @PostMapping(DILIGENCIA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR')")
    public ResponseEntity<?> createDiligencia(@Validated @RequestBody DiligenciaDTO dto) {
        return ResponseEntity.ok(diligenciaService.create(dto));
    }

    @PostMapping(DILIGENCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR')")
    public ResponseEntity<?> updateDiligenciaById(@RequestBody DiligenciaDTO dto, @PathVariable("diligenciaId") Long diligenciaId) {
        return ResponseEntity.ok(diligenciaService.updateById(dto, diligenciaId));
    }

    @DeleteMapping(DILIGENCIA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR')")
    public ResponseEntity<?> deleteDiligenciaById(@PathVariable("diligenciaId") Long diligenciaId) {
        return ResponseEntity.ok(diligenciaService.deleteById(diligenciaId));
    }
}
