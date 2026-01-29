package ao.gov.sic.sip.controllers;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.UserDTO;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private static final String USER_PATH = "/api/v1/users";
    private static final String USER_PATH_ME = USER_PATH + "/me";

    private final UserService userService;

    @PutMapping(USER_PATH)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> updateMyAccount(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateMyAccount(userDTO));
    }

    @GetMapping(USER_PATH)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Response<List<UserDTO>>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(USER_PATH_ME)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<UserDTO>> getAccountDetails() {
        return ResponseEntity.ok(userService.getAccountDetails());
    }
}
