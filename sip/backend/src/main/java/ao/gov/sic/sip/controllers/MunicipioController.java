package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.MunicipioDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.MunicipioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MunicipioController {
    private final static String MUNICIPIO_PATH = "/api/v1/municipios";
    private final static String MUNICIPIO_PATH_ID = MUNICIPIO_PATH + "/{municipioId}";

    private final MunicipioService municipioService;

    @GetMapping(MUNICIPIO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<MunicipioDTO>>> getAllMunicipios() {
        return ResponseEntity.ok(municipioService.getAll());
    }

    @GetMapping(MUNICIPIO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<?>> getMunicipioById(@PathVariable("municipioId") Long municipioId) {
        return ResponseEntity.ok(municipioService.getById(municipioId));
    }

    @PostMapping(MUNICIPIO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createMunicipio(@Validated @RequestBody MunicipioDTO dto) {
        return ResponseEntity.ok(municipioService.create(dto));
    }

    @PostMapping(MUNICIPIO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateMunicipioById(@RequestBody MunicipioDTO dto, @PathVariable("municipioId") Long municipioId) {
        return ResponseEntity.ok(municipioService.updateById(dto, municipioId));
    }

    @DeleteMapping(MUNICIPIO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteMunicipioById(@PathVariable("municipioId") Long municipioId) {
        return ResponseEntity.ok(municipioService.deleteById(municipioId));
    }
}
