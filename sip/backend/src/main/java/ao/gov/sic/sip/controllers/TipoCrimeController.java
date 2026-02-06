package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.TipoCrimeDTO;
import ao.gov.sic.sip.services.TipoCrimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TipoCrimeController {
    private final static String TIPO_CRIME_PATH = "/api/v1/tipos-crimes";
    private final static String TIPO_CRIME_PATH_ID = TIPO_CRIME_PATH + "/{tipoCrimeId}";
    private final static String TIPO_CRIME_PATH_BULK = TIPO_CRIME_PATH + "/bulk";

    private final TipoCrimeService tipoCrimeService;

    @GetMapping(TIPO_CRIME_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<TipoCrimeDTO>>> getAllTiposCrimes() {
        return ResponseEntity.ok(tipoCrimeService.getAll());
    }

    @GetMapping(TIPO_CRIME_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getTipoCrimeById(@PathVariable("tipoCrimeId") Long tipoCrimeId) {
        return ResponseEntity.ok(tipoCrimeService.getById(tipoCrimeId));
    }

    @PostMapping(TIPO_CRIME_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR', 'PIQUETE')")
    public ResponseEntity<?> createTipoCrime(@Validated @RequestBody TipoCrimeDTO dto) {
        return ResponseEntity.ok(tipoCrimeService.create(dto));
    }

    @PostMapping(TIPO_CRIME_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUTOR', 'PIQUETE')")
    public ResponseEntity<?> updateTipoCrimeById(@RequestBody TipoCrimeDTO dto, @PathVariable("tipoCrimeId") Long tipoCrimeId) {
        return ResponseEntity.ok(tipoCrimeService.updateById(dto, tipoCrimeId));
    }

    @DeleteMapping(TIPO_CRIME_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteTipoCrimeById(@PathVariable("tipoCrimeId") Long tipoCrimeId) {
        return ResponseEntity.ok(tipoCrimeService.deleteById(tipoCrimeId));
    }

    @PostMapping(TIPO_CRIME_PATH_BULK)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> bulkTipoCrimes(@RequestParam("csvFile") MultipartFile csvFile) {
        return ResponseEntity.ok(tipoCrimeService.bulkTipoCrimesByCsv(csvFile));
    }
}
