package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoReconstituicaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoReconstituicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoReconstituicaoController {
    private final static String AUTO_RECONSTITUICAO_PATH = "/api/v1/autos-reconstituicoes";
    private final static String AUTO_RECONSTITUICAO_PATH_ID = AUTO_RECONSTITUICAO_PATH + "/{autoReconstituicaoId}";

    private final AutoReconstituicaoService autoReconstituicaoService;

    @GetMapping(AUTO_RECONSTITUICAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<AutoReconstituicaoDTO>>> getAllAutosReconstituicoes() {
        return ResponseEntity.ok(autoReconstituicaoService.getAll());
    }

    @GetMapping(AUTO_RECONSTITUICAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getAutoReconstituicaoById(@PathVariable("autoReconstituicaoId") Long autoReconstituicaoId) {
        return ResponseEntity.ok(autoReconstituicaoService.getById(autoReconstituicaoId));
    }

    @PostMapping(AUTO_RECONSTITUICAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoReconstituicao(@Validated @RequestBody AutoReconstituicaoDTO dto) {
        return ResponseEntity.ok(autoReconstituicaoService.create(dto));
    }

    @PostMapping(AUTO_RECONSTITUICAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoReconstituicaoById(@RequestBody AutoReconstituicaoDTO dto, @PathVariable("autoReconstituicaoId") Long autoReconstituicaoId) {
        return ResponseEntity.ok(autoReconstituicaoService.updateById(dto, autoReconstituicaoId));
    }

    @DeleteMapping(AUTO_RECONSTITUICAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoReconstituicaoById(@PathVariable("autoReconstituicaoId") Long autoReconstituicaoId) {
        return ResponseEntity.ok(autoReconstituicaoService.deleteById(autoReconstituicaoId));
    }
}
