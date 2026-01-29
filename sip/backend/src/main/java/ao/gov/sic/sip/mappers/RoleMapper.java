package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.RoleDTO;
import ao.gov.sic.sip.entities.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    Role roleDTOToRole(RoleDTO dto);

    RoleDTO roleToRoleDTO(Role entity);
}
