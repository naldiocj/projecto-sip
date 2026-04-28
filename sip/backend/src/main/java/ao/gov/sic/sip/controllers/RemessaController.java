package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.RemessaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.RemessaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/remessas")
@RequiredArgsConstructor
public class RemessaController {

    private final RemessaService remessaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<?>>create(@ModelAttribute RemessaDTO dto) {
        return new ResponseEntity<>(remessaService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<?>> update(@PathVariable Long id, @ModelAttribute RemessaDTO dto) {
        return ResponseEntity.ok(remessaService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<RemessaDTO>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(remessaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<RemessaDTO>>> findAll() {
        return ResponseEntity.ok(remessaService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        remessaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
