package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.InstrutorDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.InstrutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InstrutorController {
    private final static String INSTRUTOR_PATH = "/api/v1/instrutores";
    private final static String INSTRUTOR_PATH_ID = INSTRUTOR_PATH + "/{instrutorId}";
    private final static String INSTRUTOR_PATH_BULK = INSTRUTOR_PATH + "/bulk";

    private final InstrutorService instrutorService;

    @GetMapping(INSTRUTOR_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<InstrutorDTO>>> getAllInstrutores() {
        return ResponseEntity.ok(instrutorService.getAll());
    }

    @GetMapping(INSTRUTOR_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getInstrutorById(@PathVariable("instrutorId") Long instrutorId) {
        return ResponseEntity.ok(instrutorService.getById(instrutorId));
    }

    @PostMapping(INSTRUTOR_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createInstrutor(@Validated @RequestBody InstrutorDTO dto) {
        return ResponseEntity.ok(instrutorService.create(dto));
    }

    @PostMapping(INSTRUTOR_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateInstrutorById(@RequestBody InstrutorDTO dto, @PathVariable("instrutorId") Long instrutorId) {
        return ResponseEntity.ok(instrutorService.updateById(dto, instrutorId));
    }

    @DeleteMapping(INSTRUTOR_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteInstrutorById(@PathVariable("instrutorId") Long instrutorId) {
        return ResponseEntity.ok(instrutorService.deleteById(instrutorId));
    }

    @PostMapping(INSTRUTOR_PATH_BULK)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> bulkInstrutores(@RequestParam("csvFile") MultipartFile csvFile) {
        return ResponseEntity.ok(instrutorService.bulkInstrutoresByCsv(csvFile));
    }
}
