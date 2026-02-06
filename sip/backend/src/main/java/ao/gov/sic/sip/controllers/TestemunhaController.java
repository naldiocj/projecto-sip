package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.TestemunhaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.TestemunhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestemunhaController {
    private final static String TESTEMUNHA_PATH = "/api/v1/testemunhas";
    private final static String TESTEMUNHA_PATH_ID = TESTEMUNHA_PATH + "/{testemunhaId}";

    private final TestemunhaService testemunhaService;

    @GetMapping(TESTEMUNHA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<TestemunhaDTO>>> getAllTestemunhas() {
        return ResponseEntity.ok(testemunhaService.getAll());
    }

    @GetMapping(TESTEMUNHA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getTestemunhaById(@PathVariable("testemunhaId") Long testemunhaId) {
        return ResponseEntity.ok(testemunhaService.getById(testemunhaId));
    }

    @PostMapping(TESTEMUNHA_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createTestemunha(@Validated @RequestBody TestemunhaDTO dto) {
        return ResponseEntity.ok(testemunhaService.create(dto));
    }

    @PostMapping(TESTEMUNHA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateTestemunhaById(@RequestBody TestemunhaDTO dto, @PathVariable("testemunhaId") Long testemunhaId) {
        return ResponseEntity.ok(testemunhaService.updateById(dto, testemunhaId));
    }

    @DeleteMapping(TESTEMUNHA_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteTestemunhaById(@PathVariable("testemunhaId") Long testemunhaId) {
        return ResponseEntity.ok(testemunhaService.deleteById(testemunhaId));
    }
}
