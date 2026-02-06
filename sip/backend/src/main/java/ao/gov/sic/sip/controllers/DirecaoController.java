package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.DirecaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.DirecaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DirecaoController {
    private final static String PATENT_PATH = "/api/v1/direcoes";
    private final static String PATENT_PATH_ID = PATENT_PATH + "/{direcaoId}";
    private final static String PATENT_PATH_BULK = PATENT_PATH + "/bulk";

    private final DirecaoService direcaoService;

    @GetMapping(PATENT_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<DirecaoDTO>>> getAllDirecoes() {
        return ResponseEntity.ok(direcaoService.getAllDirecoes());
    }

    @GetMapping(PATENT_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR')")
    public ResponseEntity<Response<?>> getPatentById(@PathVariable("direcaoId") Long direcaoId) {
        return ResponseEntity.ok(direcaoService.getDirecaoById(direcaoId));
    }

    @PostMapping(PATENT_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createDirecao(@Validated @RequestBody DirecaoDTO dto) {
        return ResponseEntity.ok(direcaoService.createDirecao(dto));
    }

    @PutMapping(PATENT_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateDirecaoById(@RequestBody DirecaoDTO dto, @PathVariable("direcaoId") Long direcaoId) {
        return ResponseEntity.ok(direcaoService.updateDirecaoById(dto, direcaoId));
    }

    @DeleteMapping(PATENT_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteDirecaoById(@PathVariable("direcaoId") Long direcaoId) {
        return ResponseEntity.ok(direcaoService.deleteDirecaoById(direcaoId));
    }

    @PostMapping(PATENT_PATH_BULK)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> bulkDirecoes(@RequestParam("csvFile") MultipartFile csvFile) {
        return ResponseEntity.ok(direcaoService.bulkDirecoesByCsv(csvFile));
    }
}
