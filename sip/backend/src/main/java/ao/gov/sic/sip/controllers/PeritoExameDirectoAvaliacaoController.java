package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.PeritoExameDirectoAvaliacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.PeritoExameDirectoAvaliacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeritoExameDirectoAvaliacaoController {
    private final static String PERITO_EXAME_DIRECTO_AVALIACAO_PATH = "/api/v1/peritos-exames-directos-avaliacoes";
    private final static String PERITO_EXAME_DIRECTO_AVALIACAO_PATH_ID = PERITO_EXAME_DIRECTO_AVALIACAO_PATH + "/{peritoExameDirectoAvaliacaoId}";

    private final PeritoExameDirectoAvaliacaoService peritoExameDirectoAvaliacaoService;

    @GetMapping(PERITO_EXAME_DIRECTO_AVALIACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<PeritoExameDirectoAvaliacaoDTO>>> getAllPeritosExamesDirectosAvaliacoes() {
        return ResponseEntity.ok(peritoExameDirectoAvaliacaoService.getAll());
    }

    @GetMapping(PERITO_EXAME_DIRECTO_AVALIACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getPeritoExameDirectoAvaliacaoById(@PathVariable("peritoExameDirectoAvaliacaoId") Long peritoExameDirectoAvaliacaoId) {
        return ResponseEntity.ok(peritoExameDirectoAvaliacaoService.getById(peritoExameDirectoAvaliacaoId));
    }

    @PostMapping(PERITO_EXAME_DIRECTO_AVALIACAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createPeritoExameDirectoAvaliacao(@Validated @RequestBody PeritoExameDirectoAvaliacaoDTO dto) {
        return ResponseEntity.ok(peritoExameDirectoAvaliacaoService.create(dto));
    }

    @PostMapping(PERITO_EXAME_DIRECTO_AVALIACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updatePeritoExameDirectoAvaliacaoById(@RequestBody PeritoExameDirectoAvaliacaoDTO dto, @PathVariable("peritoExameDirectoAvaliacaoId") Long peritoExameDirectoAvaliacaoId) {
        return ResponseEntity.ok(peritoExameDirectoAvaliacaoService.updateById(dto, peritoExameDirectoAvaliacaoId));
    }

    @DeleteMapping(PERITO_EXAME_DIRECTO_AVALIACAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deletePeritoExameDirectoAvaliacaoById(@PathVariable("peritoExameDirectoAvaliacaoId") Long peritoExameDirectoAvaliacaoId) {
        return ResponseEntity.ok(peritoExameDirectoAvaliacaoService.deleteById(peritoExameDirectoAvaliacaoId));
    }
}
