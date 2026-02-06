package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoDeclaracaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoDeclaracaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoDeclaracaoController {
    private final static String AUTO_DECLARACAO_PATH = "/api/v1/autos-declaracoes";
    private final static String AUTO_DECLARACAO_PATH_ID = AUTO_DECLARACAO_PATH + "/{autoDeclaracaoId}";

    private final AutoDeclaracaoService autoDeclaracaoService;

    @GetMapping(AUTO_DECLARACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoDeclaracaoDTO>>> getAllAutosDeclaracoes() {
        return ResponseEntity.ok(autoDeclaracaoService.getAll());
    }

    @GetMapping(AUTO_DECLARACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoDeclaracaoById(@PathVariable("autoDeclaracaoId") Long autoDeclaracaoId) {
        return ResponseEntity.ok(autoDeclaracaoService.getById(autoDeclaracaoId));
    }

    @PostMapping(AUTO_DECLARACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoDeclaracao(@Validated @RequestBody AutoDeclaracaoDTO dto) {
        return ResponseEntity.ok(autoDeclaracaoService.create(dto));
    }

    @PostMapping(AUTO_DECLARACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoDeclaracaoById(@RequestBody AutoDeclaracaoDTO dto, @PathVariable("autoDeclaracaoId") Long autoDeclaracaoId) {
        return ResponseEntity.ok(autoDeclaracaoService.updateById(dto, autoDeclaracaoId));
    }

    @DeleteMapping(AUTO_DECLARACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoDeclaracaoById(@PathVariable("autoDeclaracaoId") Long autoDeclaracaoId) {
        return ResponseEntity.ok(autoDeclaracaoService.deleteById(autoDeclaracaoId));
    }
}
