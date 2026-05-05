package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.MandadoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.MandadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mandados")
@RequiredArgsConstructor
public class MandadoController {

    private final MandadoService mandadoService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<MandadoDTO>> create(@ModelAttribute MandadoDTO dto) {
        return new ResponseEntity<>(mandadoService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<MandadoDTO>> update(@PathVariable Long id, @ModelAttribute MandadoDTO dto) {
        return ResponseEntity.ok(mandadoService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MandadoDTO>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mandadoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<MandadoDTO>>> findAll() {
        return ResponseEntity.ok(mandadoService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(mandadoService.delete(id));
    }
}
