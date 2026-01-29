package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.PatenteDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.PatenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatenteController {
    private final static String PATENT_PATH = "/api/v1/patentes";
    private final static String PATENT_PATH_ID = PATENT_PATH + "/{patenteId}";

    private final PatenteService patenteService;

    @GetMapping(PATENT_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AGENT')")
    public ResponseEntity<Response<List<PatenteDTO>>> getAllPatents() {
        return ResponseEntity.ok(patenteService.getAllPatentes());
    }

    @GetMapping(PATENT_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AGENT')")
    public ResponseEntity<Response<?>> getPatentById(@PathVariable("patenteId") Long patenteId) {
        return ResponseEntity.ok(patenteService.getPatenteById(patenteId));
    }
}
