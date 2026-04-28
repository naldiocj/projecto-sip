package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.DetidoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.DetidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detidos")
@RequiredArgsConstructor
public class DetidoController {

    private final DetidoService detidoService;

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody DetidoDTO dto) {
        return new ResponseEntity<>(detidoService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable Long id, @RequestBody DetidoDTO dto) {
        return ResponseEntity.ok(detidoService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<DetidoDTO>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(detidoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<DetidoDTO>>> findAll() {
        return ResponseEntity.ok(detidoService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        detidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
