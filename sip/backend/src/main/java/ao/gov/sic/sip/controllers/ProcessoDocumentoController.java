package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ProcessoDocumentoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProcessoDocumentoController {
    private final static String URL = "/api/v1/carregar-documentos";

    @PostMapping(value = URL, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<?> saveDocumento(@Validated @ModelAttribute ProcessoDocumentoDTO dto) {

        return ResponseEntity.ok(null);
    }
}
