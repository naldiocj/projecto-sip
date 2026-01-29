package ao.gov.sic.sip.controllers;


import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.RoleDTO;
import ao.gov.sic.sip.services.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class RoleController {
    private static final String ROLES_PATH = "/api/v1/roles";
    private final RoleService roleService;

    @PostMapping(ROLES_PATH)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.createRole(roleDTO));
    }

    @PutMapping(ROLES_PATH)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> updateRole(@Valid @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.updateRole(roleDTO));
    }

    @GetMapping(ROLES_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Response<?>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
