package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.CategoriaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoriaController {
    private final static String PATENT_PATH = "/api/v1/categorias";
    private final static String PATENT_PATH_ID = PATENT_PATH + "/{categoriaId}";

    private final CategoriaService categoriaService;

    @GetMapping(PATENT_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<Response<List<CategoriaDTO>>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.getAll());
    }

    @GetMapping(PATENT_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR')")
    public ResponseEntity<Response<?>> getCategoriaById(@PathVariable("categoriaId") Long categoriaId) {
        return ResponseEntity.ok(categoriaService.getById(categoriaId));
    }

    @PostMapping(PATENT_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createCategoria(@Validated @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(categoriaService.create(dto));
    }

    @PutMapping(PATENT_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateCategoriaById(@RequestBody CategoriaDTO dto, @PathVariable("categoriaId") Long categoriaId) {
        return ResponseEntity.ok(categoriaService.updateById(dto, categoriaId));
    }

    @DeleteMapping(PATENT_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteCategoriaById(@PathVariable("categoriaId") Long categoriaId) {
        return ResponseEntity.ok(categoriaService.deleteById(categoriaId));
    }
}
