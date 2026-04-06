package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoReconhecimentoDescricaoPessoaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoReconhecimentoDescricaoPessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoReconhecimentoDescricaoPessoaController {
    private final static String AUTO_RECONHECIMENTO_DESCRICAO_PESSOA_PATH = "/api/v1/autos-reconhecimento-descricao-pessoas";
    private final static String AUTO_RECONHECIMENTO_DESCRICAO_PESSOA_PATH_ID = AUTO_RECONHECIMENTO_DESCRICAO_PESSOA_PATH + "/{autoReconhecimentoDescricaoPessoaId}";

    private final AutoReconhecimentoDescricaoPessoaService autoReconhecimentoDescricaoPessoaService;

    @GetMapping(AUTO_RECONHECIMENTO_DESCRICAO_PESSOA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoReconhecimentoDescricaoPessoaDTO>>> getAllAutosReconhecimentoDescricaoPessoas() {
        return ResponseEntity.ok(autoReconhecimentoDescricaoPessoaService.getAll());
    }

    @GetMapping(AUTO_RECONHECIMENTO_DESCRICAO_PESSOA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoReconhecimentoDescricaoPessoaById(@PathVariable("autoReconhecimentoDescricaoPessoaId") Long autoReconhecimentoDescricaoPessoaId) {
        return ResponseEntity.ok(autoReconhecimentoDescricaoPessoaService.getById(autoReconhecimentoDescricaoPessoaId));
    }

    @PostMapping(AUTO_RECONHECIMENTO_DESCRICAO_PESSOA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoReconhecimentoDescricaoPessoa(@Validated @RequestBody AutoReconhecimentoDescricaoPessoaDTO dto) {
        return ResponseEntity.ok(autoReconhecimentoDescricaoPessoaService.create(dto));
    }

    @PostMapping(AUTO_RECONHECIMENTO_DESCRICAO_PESSOA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoReconhecimentoDescricaoPessoaById(@RequestBody AutoReconhecimentoDescricaoPessoaDTO dto, @PathVariable("autoReconhecimentoDescricaoPessoaId") Long autoReconhecimentoDescricaoPessoaId) {
        return ResponseEntity.ok(autoReconhecimentoDescricaoPessoaService.updateById(dto, autoReconhecimentoDescricaoPessoaId));
    }

    @DeleteMapping(AUTO_RECONHECIMENTO_DESCRICAO_PESSOA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoReconhecimentoDescricaoPessoaById(@PathVariable("autoReconhecimentoDescricaoPessoaId") Long autoReconhecimentoDescricaoPessoaId) {
        return ResponseEntity.ok(autoReconhecimentoDescricaoPessoaService.deleteById(autoReconhecimentoDescricaoPessoaId));
    }
}
