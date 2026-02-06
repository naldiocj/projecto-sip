package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoReconhecimentoFisicoDirectoPessoaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoReconhecimentoFisicoDirectoPessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoReconhecimentoFisicoDirectoPessoaController {
    private final static String AUTO_RECONHECIMENTO_FISICO_DIRECTO_PESSOA_PATH = "/api/v1/autos-reconhecimento-fisico-directo-pessoas";
    private final static String AUTO_RECONHECIMENTO_FISICO_DIRECTO_PESSOA_PATH_ID = AUTO_RECONHECIMENTO_FISICO_DIRECTO_PESSOA_PATH + "/{autoReconhecimentoFisicoDirectoPessoaId}";

    private final AutoReconhecimentoFisicoDirectoPessoaService autoReconhecimentoFisicoDirectoPessoaService;

    @GetMapping(AUTO_RECONHECIMENTO_FISICO_DIRECTO_PESSOA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoReconhecimentoFisicoDirectoPessoaDTO>>> getAllAutosReconhecimentoFisicoDirectoPessoas() {
        return ResponseEntity.ok(autoReconhecimentoFisicoDirectoPessoaService.getAll());
    }

    @GetMapping(AUTO_RECONHECIMENTO_FISICO_DIRECTO_PESSOA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoReconhecimentoFisicoDirectoPessoaById(@PathVariable("autoReconhecimentoFisicoDirectoPessoaId") Long autoReconhecimentoFisicoDirectoPessoaId) {
        return ResponseEntity.ok(autoReconhecimentoFisicoDirectoPessoaService.getById(autoReconhecimentoFisicoDirectoPessoaId));
    }

    @PostMapping(AUTO_RECONHECIMENTO_FISICO_DIRECTO_PESSOA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoReconhecimentoFisicoDirectoPessoa(@Validated @RequestBody AutoReconhecimentoFisicoDirectoPessoaDTO dto) {
        return ResponseEntity.ok(autoReconhecimentoFisicoDirectoPessoaService.create(dto));
    }

    @PostMapping(AUTO_RECONHECIMENTO_FISICO_DIRECTO_PESSOA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoReconhecimentoFisicoDirectoPessoaById(@RequestBody AutoReconhecimentoFisicoDirectoPessoaDTO dto, @PathVariable("autoReconhecimentoFisicoDirectoPessoaId") Long autoReconhecimentoFisicoDirectoPessoaId) {
        return ResponseEntity.ok(autoReconhecimentoFisicoDirectoPessoaService.updateById(dto, autoReconhecimentoFisicoDirectoPessoaId));
    }

    @DeleteMapping(AUTO_RECONHECIMENTO_FISICO_DIRECTO_PESSOA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoReconhecimentoFisicoDirectoPessoaById(@PathVariable("autoReconhecimentoFisicoDirectoPessoaId") Long autoReconhecimentoFisicoDirectoPessoaId) {
        return ResponseEntity.ok(autoReconhecimentoFisicoDirectoPessoaService.deleteById(autoReconhecimentoFisicoDirectoPessoaId));
    }
}
