package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.DirectorDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DirectorController {
    private final static String DIRECTOR_PATH = "/api/v1/directores";
    private final static String DIRECTOR_PATH_ID = DIRECTOR_PATH + "/{directorId}";

    private final DirectorService directorService;

    @GetMapping(DIRECTOR_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<DirectorDTO>>> getAllDirectores() {
        return ResponseEntity.ok(directorService.getAll());
    }

    @GetMapping(DIRECTOR_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR')")
    public ResponseEntity<Response<?>> getDirectorById(@PathVariable("directorId") Long directorId) {
        return ResponseEntity.ok(directorService.getById(directorId));
    }

    @PostMapping(DIRECTOR_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createDirector(@Validated @RequestBody DirectorDTO dto) {
        return ResponseEntity.ok(directorService.create(dto));
    }

    @PutMapping(DIRECTOR_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateDirectorById(@RequestBody DirectorDTO dto, @PathVariable("directorId") Long directorId) {
        return ResponseEntity.ok(directorService.updateById(dto, directorId));
    }

    @DeleteMapping(DIRECTOR_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteDirectorById(@PathVariable("directorId") Long directorId) {
        return ResponseEntity.ok(directorService.deleteById(directorId));
    }
}
