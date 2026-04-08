package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoDeclaracaoEmAditamentoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoDeclaracaoEmAditamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoDeclaracaoEmAditamentoController {
    private final static String AUTO_DECLARACAO_EM_ADITAMENTO_PATH = "/api/v1/autos-declaracoes-em-aditamentos";
    private final static String AUTO_DECLARACAO_EM_ADITAMENTO_PATH_ID = AUTO_DECLARACAO_EM_ADITAMENTO_PATH + "/{autoDeclaracaoEmAditamentoId}";

    private final AutoDeclaracaoEmAditamentoService autoDeclaracaoEmAditamentoService;

    @GetMapping(AUTO_DECLARACAO_EM_ADITAMENTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<AutoDeclaracaoEmAditamentoDTO>>> getAllAutosDeclaracoesEmAditamentos() {
        return ResponseEntity.ok(autoDeclaracaoEmAditamentoService.getAll());
    }

    @GetMapping(AUTO_DECLARACAO_EM_ADITAMENTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getAutoDeclaracaoEmAditamentoById(@PathVariable("autoDeclaracaoEmAditamentoId") Long autoDeclaracaoEmAditamentoId) {
        return ResponseEntity.ok(autoDeclaracaoEmAditamentoService.getById(autoDeclaracaoEmAditamentoId));
    }

    @PostMapping(AUTO_DECLARACAO_EM_ADITAMENTO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoDeclaracaoEmAditamento(@Validated @RequestBody AutoDeclaracaoEmAditamentoDTO dto) {
        return ResponseEntity.ok(autoDeclaracaoEmAditamentoService.create(dto));
    }

    @PostMapping(AUTO_DECLARACAO_EM_ADITAMENTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoDeclaracaoEmAditamentoById(@RequestBody AutoDeclaracaoEmAditamentoDTO dto, @PathVariable("autoDeclaracaoEmAditamentoId") Long autoDeclaracaoEmAditamentoId) {
        return ResponseEntity.ok(autoDeclaracaoEmAditamentoService.updateById(dto, autoDeclaracaoEmAditamentoId));
    }

    @DeleteMapping(AUTO_DECLARACAO_EM_ADITAMENTO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoDeclaracaoEmAditamentoById(@PathVariable("autoDeclaracaoEmAditamentoId") Long autoDeclaracaoEmAditamentoId) {
        return ResponseEntity.ok(autoDeclaracaoEmAditamentoService.deleteById(autoDeclaracaoEmAditamentoId));
    }
}
