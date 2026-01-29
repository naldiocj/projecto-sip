package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.UserDTO;
import ao.gov.sic.sip.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User userDTOToUser(UserDTO dto);

    UserDTO userToUserDTO(User entity);
}
