package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.TestemunhaAutoQueixaDTO;
import ao.gov.sic.sip.services.TestemunhaAutoQueixaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestemunhaAutoQueixaController {
    private final static String TESTEMUNHA_AUTO_QUEIXA_PATH = "/api/v1/testemunhas-autos-queixas";
    private final static String TESTEMUNHA_AUTO_QUEIXA_PATH_ID = TESTEMUNHA_AUTO_QUEIXA_PATH + "/{testemunhaAutoQueixaId}";

    private final TestemunhaAutoQueixaService testemunhaAutoQueixaService;

    @GetMapping(TESTEMUNHA_AUTO_QUEIXA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<TestemunhaAutoQueixaDTO>>> getAllTestemunhasAutosQueixas() {
        return ResponseEntity.ok(testemunhaAutoQueixaService.getAll());
    }

    @GetMapping(TESTEMUNHA_AUTO_QUEIXA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getTestemunhaAutoQueixaById(@PathVariable("testemunhaAutoQueixaId") Long testemunhaAutoQueixaId) {
        return ResponseEntity.ok(testemunhaAutoQueixaService.getById(testemunhaAutoQueixaId));
    }

    @PostMapping(TESTEMUNHA_AUTO_QUEIXA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createTestemunhaAutoQueixa(@Validated @RequestBody TestemunhaAutoQueixaDTO dto) {
        return ResponseEntity.ok(testemunhaAutoQueixaService.create(dto));
    }

    @PostMapping(TESTEMUNHA_AUTO_QUEIXA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateTestemunhaAutoQueixaById(@RequestBody TestemunhaAutoQueixaDTO dto, @PathVariable("testemunhaAutoQueixaId") Long testemunhaAutoQueixaId) {
        return ResponseEntity.ok(testemunhaAutoQueixaService.updateById(dto, testemunhaAutoQueixaId));
    }

    @DeleteMapping(TESTEMUNHA_AUTO_QUEIXA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteTestemunhaAutoQueixaById(@PathVariable("testemunhaAutoQueixaId") Long testemunhaAutoQueixaId) {
        return ResponseEntity.ok(testemunhaAutoQueixaService.deleteById(testemunhaAutoQueixaId));
    }
}
