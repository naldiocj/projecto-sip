package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.RoleDTO;

import java.util.List;

public interface RoleService {
    Response<?> createRole(RoleDTO roleDTO);

    Response<?> updateRole(RoleDTO roleDTO);

    Response<?> deleteRole(Long id);

    Response<List<RoleDTO>> getAllRoles();
}
