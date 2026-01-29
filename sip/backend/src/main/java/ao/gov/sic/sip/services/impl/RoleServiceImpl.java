package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.RoleDTO;
import ao.gov.sic.sip.entities.Role;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.RoleMapper;
import ao.gov.sic.sip.repositories.RoleRepository;
import ao.gov.sic.sip.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Response<?> createRole(RoleDTO roleDTO) {
        log.info("Inside createRole()");
        Role role = roleMapper.roleDTOToRole(roleDTO);
        role.setName(roleDTO.getName().toUpperCase());
        roleRepository.save(role);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role Created Successfully")
                .build();
    }

    @Override
    public Response<?> updateRole(RoleDTO roleDTO) {
        log.info("Inside updateRole()");
        Long id = roleDTO.getId();
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        existingRole.setName(roleDTO.getName().toUpperCase());
        roleRepository.save(existingRole);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role Updated Successfully")
                .build();
    }

    @Override
    public Response<List<RoleDTO>> getAllRoles() {
        log.info("Inside getAllRoles()");
        List<RoleDTO> roles = roleRepository.findAll().stream()
                .map(roleMapper::roleToRoleDTO)
                .toList();

        return Response.<List<RoleDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(roles.isEmpty() ? "No Roles Found" : "Roles Retreived Successfully")
                .data(roles)
                .build();
    }
}
