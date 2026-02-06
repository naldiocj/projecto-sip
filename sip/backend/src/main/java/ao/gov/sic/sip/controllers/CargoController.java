package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.CargoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.CargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CargoController {
    private final static String CARGO_PATH = "/api/v1/cargos";
    private final static String CARGO_PATH_ID = CARGO_PATH + "/{cargoId}";
    private final static String CARGO_PATH_BULK = CARGO_PATH + "/bulk";

    private final CargoService cargoService;

    @GetMapping(CARGO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<CargoDTO>>> getAllCargos() {
        return ResponseEntity.ok(cargoService.getAll());
    }

    @GetMapping(CARGO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR')")
    public ResponseEntity<Response<?>> getCargoById(@PathVariable("cargoId") Long cargoId) {
        return ResponseEntity.ok(cargoService.getById(cargoId));
    }

    @PostMapping(CARGO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createCargo(@Validated @RequestBody CargoDTO dto) {
        return ResponseEntity.ok(cargoService.create(dto));
    }

    @PutMapping(CARGO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateCargoById(@RequestBody CargoDTO dto, @PathVariable("cargoId") Long cargoId) {
        return ResponseEntity.ok(cargoService.updateById(dto, cargoId));
    }

    @DeleteMapping(CARGO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteCargoById(@PathVariable("cargoId") Long cargoId) {
        return ResponseEntity.ok(cargoService.deleteById(cargoId));
    }

    @PostMapping(CARGO_PATH_BULK)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> bulkCargos(@RequestParam("csvFile") MultipartFile csvFile) {
        return ResponseEntity.ok(cargoService.bulkCargosByCsv(csvFile));
    }
}
