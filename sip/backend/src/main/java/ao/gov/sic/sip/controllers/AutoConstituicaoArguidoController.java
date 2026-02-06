package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoConstituicaoArguidoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoConstituicaoArguidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoConstituicaoArguidoController {
    private final static String AUTO_CONSTITUICAO_ARGUIDO_PATH = "/api/v1/autos-constituicoes-arguidos";
    private final static String AUTO_CONSTITUICAO_ARGUIDO_PATH_ID = AUTO_CONSTITUICAO_ARGUIDO_PATH + "/{autoConstituicaoArguidoId}";

    private final AutoConstituicaoArguidoService autoConstituicaoArguidoService;

    @GetMapping(AUTO_CONSTITUICAO_ARGUIDO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<AutoConstituicaoArguidoDTO>>> getAllAutosConstituicoesArguidos() {
        return ResponseEntity.ok(autoConstituicaoArguidoService.getAll());
    }

    @GetMapping(AUTO_CONSTITUICAO_ARGUIDO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getAutoConstituicaoArguidoById(@PathVariable("autoConstituicaoArguidoId") Long autoConstituicaoArguidoId) {
        return ResponseEntity.ok(autoConstituicaoArguidoService.getById(autoConstituicaoArguidoId));
    }

    @PostMapping(AUTO_CONSTITUICAO_ARGUIDO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoConstituicaoArguido(@Validated @RequestBody AutoConstituicaoArguidoDTO dto) {
        return ResponseEntity.ok(autoConstituicaoArguidoService.create(dto));
    }

    @PostMapping(AUTO_CONSTITUICAO_ARGUIDO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoConstituicaoArguidoById(@RequestBody AutoConstituicaoArguidoDTO dto, @PathVariable("autoConstituicaoArguidoId") Long autoConstituicaoArguidoId) {
        return ResponseEntity.ok(autoConstituicaoArguidoService.updateById(dto, autoConstituicaoArguidoId));
    }

    @DeleteMapping(AUTO_CONSTITUICAO_ARGUIDO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoConstituicaoArguidoById(@PathVariable("autoConstituicaoArguidoId") Long autoConstituicaoArguidoId) {
        return ResponseEntity.ok(autoConstituicaoArguidoService.deleteById(autoConstituicaoArguidoId));
    }
}
