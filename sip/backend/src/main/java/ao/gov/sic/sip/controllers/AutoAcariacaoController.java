package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoAcariacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoAcariacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoAcariacaoController {
    private final static String AUTO_ACARIACAO_PATH = "/api/v1/autos-acariacoes";
    private final static String AUTO_ACARIACAO_PATH_ID = AUTO_ACARIACAO_PATH + "/{autoAcariacaoId}";

    private final AutoAcariacaoService autoAcariacaoService;

    @GetMapping(AUTO_ACARIACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoAcariacaoDTO>>> getAllAutosAcariacoes() {
        return ResponseEntity.ok(autoAcariacaoService.getAll());
    }

    @GetMapping(AUTO_ACARIACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoAcariacaoById(@PathVariable("autoAcariacaoId") Long autoAcariacaoId) {
        return ResponseEntity.ok(autoAcariacaoService.getById(autoAcariacaoId));
    }

    @PostMapping(AUTO_ACARIACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoAcariacao(@Validated @RequestBody AutoAcariacaoDTO dto) {
        return ResponseEntity.ok(autoAcariacaoService.create(dto));
    }

    @PostMapping(AUTO_ACARIACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoAcariacaoById(@RequestBody AutoAcariacaoDTO dto, @PathVariable("autoAcariacaoId") Long autoAcariacaoId) {
        return ResponseEntity.ok(autoAcariacaoService.updateById(dto, autoAcariacaoId));
    }

    @DeleteMapping(AUTO_ACARIACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoAcariacaoById(@PathVariable("autoAcariacaoId") Long autoAcariacaoId) {
        return ResponseEntity.ok(autoAcariacaoService.deleteById(autoAcariacaoId));
    }
}
