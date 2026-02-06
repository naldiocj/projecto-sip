package ao.gov.sic.sip.controllers;


import ao.gov.sic.sip.dtos.LoginRequest;
import ao.gov.sic.sip.dtos.LoginResponse;
import ao.gov.sic.sip.dtos.RegistrationRequest;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final String AUTH_PATH = "/api/v1/auth";
    private static final String AUTH_REGISTER = AUTH_PATH + "/register";
    private static final String AUTH_LOGIN = AUTH_PATH + "/login";

    private final AuthService authService;

    @PostMapping(AUTH_REGISTER)
    public ResponseEntity<Response<?>> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authService.register(registrationRequest));
    }

    @PostMapping(AUTH_LOGIN)
    public ResponseEntity<Response<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
