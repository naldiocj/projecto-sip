package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.SecretariaDTO;
import ao.gov.sic.sip.services.SecretariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/secretarias")
@RequiredArgsConstructor
public class SecretariaController {

    private final SecretariaService secretariaService;

    @PostMapping
    public ResponseEntity<SecretariaDTO> create(@RequestBody SecretariaDTO dto) {
        return new ResponseEntity<>(secretariaService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecretariaDTO> update(@PathVariable Long id, @RequestBody SecretariaDTO dto) {
        return ResponseEntity.ok(secretariaService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecretariaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(secretariaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<SecretariaDTO>> findAll() {
        return ResponseEntity.ok(secretariaService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        secretariaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
