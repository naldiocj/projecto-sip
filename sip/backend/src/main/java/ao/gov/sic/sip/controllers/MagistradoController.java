package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.MagistradoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.MagistradoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MagistradoController {
    private final static String MAGISTRADO_PATH = "/api/v1/magistrados";
    private final static String MAGISTRADO_PATH_ID = MAGISTRADO_PATH + "/{magistradoId}";
    private final static String MAGISTRADO_PATH_BULK = MAGISTRADO_PATH + "/bulk";

    private final MagistradoService magistradoService;

    @GetMapping(MAGISTRADO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<MagistradoDTO>>> getAllMagistrados() {
        return ResponseEntity.ok(magistradoService.getAll());
    }

    @GetMapping(MAGISTRADO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getMagistradoById(@PathVariable("magistradoId") Long magistradoId) {
        return ResponseEntity.ok(magistradoService.getById(magistradoId));
    }

    @PostMapping(MAGISTRADO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createMagistrado(@Validated @RequestBody MagistradoDTO dto) {
        return ResponseEntity.ok(magistradoService.create(dto));
    }

    @PostMapping(MAGISTRADO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateMagistradoById(@RequestBody MagistradoDTO dto, @PathVariable("magistradoId") Long magistradoId) {
        return ResponseEntity.ok(magistradoService.updateById(dto, magistradoId));
    }

    @DeleteMapping(MAGISTRADO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteMagistradoById(@PathVariable("magistradoId") Long magistradoId) {
        return ResponseEntity.ok(magistradoService.deleteById(magistradoId));
    }

    @PostMapping(MAGISTRADO_PATH_BULK)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> bulkMagistrados(@RequestParam("csvFile") MultipartFile csvFile) {
        return ResponseEntity.ok(magistradoService.bulkMagistradosByCsv(csvFile));
    }
}
