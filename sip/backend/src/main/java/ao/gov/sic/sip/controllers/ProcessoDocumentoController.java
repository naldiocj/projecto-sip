package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ProcessoDocumentoDTO;
import ao.gov.sic.sip.dtos.ProcessoDocumentoItemDTO;
import ao.gov.sic.sip.dtos.ProcessoResDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.services.ProcessoDocumentoService;
import ao.gov.sic.sip.services.StorageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProcessoDocumentoController {
    private final static String URL = "/api/v1/processos-documentos";
    private final static String URL_ID = "/api/v1/processos-documentos/{processoNumero}";

    private final ProcessoDocumentoService processoDocumentoService;
    private final ProcessoRepository processoRepository;

    @PostMapping(value = URL, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR')")
    public ResponseEntity<?> saveDocumento(@Validated @ModelAttribute ProcessoDocumentoDTO dto) {
        return ResponseEntity.ok(processoDocumentoService.saveDocumento(dto));
    }

    @GetMapping(URL_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<ProcessoDocumentoItemDTO>>> getDocumentosByProcessoId(@PathVariable("processoNumero") String processoNumero, @Param("term") String term) {

        boolean hasNumero = StringUtils.hasText(processoNumero);
        String numeroConvertido = hasNumero ? processoNumero.replaceFirst("-", "/") : null;

        Processo processo = processoRepository.findFirstByNumero(numeroConvertido);

        if (processo == null) {
            throw new NotFoundException("Processo não encontrado");
        }

        return ResponseEntity.ok(processoDocumentoService.getDocumentosByProcessoId(processo.getId()));
    }
}
