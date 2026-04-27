package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.DespachoDTO;
import ao.gov.sic.sip.services.DespachoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/despachos")
@RequiredArgsConstructor
public class DespachoController {

    private final DespachoService despachoService;

    @PostMapping
    public ResponseEntity<DespachoDTO> create(@RequestBody DespachoDTO dto) {
        return new ResponseEntity<>(despachoService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespachoDTO> update(@PathVariable Long id, @RequestBody DespachoDTO dto) {
        return ResponseEntity.ok(despachoService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(despachoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<DespachoDTO>> findAll() {
        return ResponseEntity.ok(despachoService.findAll());
    }

    @GetMapping("/processo/{processoId}")
    public ResponseEntity<List<DespachoDTO>> findByProcessoId(@PathVariable Long processoId) {
        return ResponseEntity.ok(despachoService.findByProcessoId(processoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        despachoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
