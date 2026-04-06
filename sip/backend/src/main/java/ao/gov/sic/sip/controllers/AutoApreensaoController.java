package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.AutoApreensaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AutoApreensaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoApreensaoController {
    private final static String AUTO_APREENSAO_PATH = "/api/v1/autos-apreensoes";
    private final static String AUTO_APREENSAO_PATH_ID = AUTO_APREENSAO_PATH + "/{autoApreensaoId}";

    private final AutoApreensaoService autoApreensaoService;

    @GetMapping(AUTO_APREENSAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<AutoApreensaoDTO>>> getAllAutosApreensoes() {
        return ResponseEntity.ok(autoApreensaoService.getAll());
    }

    @GetMapping(AUTO_APREENSAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getAutoApreensaoById(@PathVariable("autoApreensaoId") Long autoApreensaoId) {
        return ResponseEntity.ok(autoApreensaoService.getById(autoApreensaoId));
    }

    @PostMapping(AUTO_APREENSAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAutoApreensao(@Validated @RequestBody AutoApreensaoDTO dto) {
        return ResponseEntity.ok(autoApreensaoService.create(dto));
    }

    @PostMapping(AUTO_APREENSAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateAutoApreensaoById(@RequestBody AutoApreensaoDTO dto, @PathVariable("autoApreensaoId") Long autoApreensaoId) {
        return ResponseEntity.ok(autoApreensaoService.updateById(dto, autoApreensaoId));
    }

    @DeleteMapping(AUTO_APREENSAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteAutoApreensaoById(@PathVariable("autoApreensaoId") Long autoApreensaoId) {
        return ResponseEntity.ok(autoApreensaoService.deleteById(autoApreensaoId));
    }
}
