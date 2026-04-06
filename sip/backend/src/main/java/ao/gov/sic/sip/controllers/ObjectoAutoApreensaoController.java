package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.ObjectoAutoApreensaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.ObjectoAutoApreensaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ObjectoAutoApreensaoController {
    private final static String OBJECTO_AUTO_APREENSAO_PATH = "/api/v1/objectos-autos-apreensoes";
    private final static String OBJECTO_AUTO_APREENSAO_PATH_ID = OBJECTO_AUTO_APREENSAO_PATH + "/{objectoAutoApreensaoId}";

    private final ObjectoAutoApreensaoService objectoAutoApreensaoService;

    @GetMapping(OBJECTO_AUTO_APREENSAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<List<ObjectoAutoApreensaoDTO>>> getAllObjectosAutosApreensoes() {
        return ResponseEntity.ok(objectoAutoApreensaoService.getAll());
    }

    @GetMapping(OBJECTO_AUTO_APREENSAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'INSTRUTOR', 'PIQUETE', 'PGR', 'SECRETARIA')")
    public ResponseEntity<Response<?>> getObjectoAutoApreensaoById(@PathVariable("objectoAutoApreensaoId") Long objectoAutoApreensaoId) {
        return ResponseEntity.ok(objectoAutoApreensaoService.getById(objectoAutoApreensaoId));
    }

    @PostMapping(OBJECTO_AUTO_APREENSAO_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createObjectoAutoApreensao(@Validated @RequestBody ObjectoAutoApreensaoDTO dto) {
        return ResponseEntity.ok(objectoAutoApreensaoService.create(dto));
    }

    @PostMapping(OBJECTO_AUTO_APREENSAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateObjectoAutoApreensaoById(@RequestBody ObjectoAutoApreensaoDTO dto, @PathVariable("objectoAutoApreensaoId") Long objectoAutoApreensaoId) {
        return ResponseEntity.ok(objectoAutoApreensaoService.updateById(dto, objectoAutoApreensaoId));
    }

    @DeleteMapping(OBJECTO_AUTO_APREENSAO_PATH_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteObjectoAutoApreensaoById(@PathVariable("objectoAutoApreensaoId") Long objectoAutoApreensaoId) {
        return ResponseEntity.ok(objectoAutoApreensaoService.deleteById(objectoAutoApreensaoId));
    }
}
