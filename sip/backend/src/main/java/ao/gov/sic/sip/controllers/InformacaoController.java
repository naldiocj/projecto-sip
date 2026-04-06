package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.InformacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.InformacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InformacaoController {
    private final static String INFORMACAO_PATH = "/api/v1/informacoes";
    private final static String INFORMACAO_PATH_ID = INFORMACAO_PATH + "/{informacaoId}";

    private final InformacaoService informacaoService;

    @GetMapping(INFORMACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<InformacaoDTO>>> getAllInformacoes() {
        return ResponseEntity.ok(informacaoService.getAll());
    }

    @GetMapping(INFORMACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getInformacaoById(@PathVariable("informacaoId") Long informacaoId) {
        return ResponseEntity.ok(informacaoService.getById(informacaoId));
    }

    @PostMapping(INFORMACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createInformacao(@Validated @RequestBody InformacaoDTO dto) {
        return ResponseEntity.ok(informacaoService.create(dto));
    }

    @PostMapping(INFORMACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateInformacaoById(@RequestBody InformacaoDTO dto, @PathVariable("informacaoId") Long informacaoId) {
        return ResponseEntity.ok(informacaoService.updateById(dto, informacaoId));
    }

    @DeleteMapping(INFORMACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteInformacaoById(@PathVariable("informacaoId") Long informacaoId) {
        return ResponseEntity.ok(informacaoService.deleteById(informacaoId));
    }
}
