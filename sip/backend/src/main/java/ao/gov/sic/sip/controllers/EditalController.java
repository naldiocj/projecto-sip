package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.EditalDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.EditalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EditalController {
    private final static String EDITAL_PATH = "/api/v1/editais";
    private final static String EDITAL_PATH_ID = EDITAL_PATH + "/{editalId}";

    private final EditalService editalService;

    @GetMapping(EDITAL_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<EditalDTO>>> getAllEditais() {
        return ResponseEntity.ok(editalService.getAll());
    }

    @GetMapping(EDITAL_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getEditalById(@PathVariable("editalId") Long editalId) {
        return ResponseEntity.ok(editalService.getById(editalId));
    }

    @PostMapping(EDITAL_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createEdital(@Validated @RequestBody EditalDTO dto) {
        return ResponseEntity.ok(editalService.create(dto));
    }

    @PostMapping(EDITAL_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateEditalById(@RequestBody EditalDTO dto, @PathVariable("editalId") Long editalId) {
        return ResponseEntity.ok(editalService.updateById(dto, editalId));
    }

    @DeleteMapping(EDITAL_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteEditalById(@PathVariable("editalId") Long editalId) {
        return ResponseEntity.ok(editalService.deleteById(editalId));
    }
}
