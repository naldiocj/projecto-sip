package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.dtos.UserDTO;

import java.util.List;

public interface UserService {
    User currentUser();

    Response<?> updateMyAccount(UserDTO userDTO);

    Response<List<UserDTO>> getAllUsers();

    Response<UserDTO> getAccountDetails();
}
