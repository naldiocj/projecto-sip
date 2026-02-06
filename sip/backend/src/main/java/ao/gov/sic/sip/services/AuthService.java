package ao.gov.sic.sip.services;


import ao.gov.sic.sip.dtos.LoginRequest;
import ao.gov.sic.sip.dtos.LoginResponse;
import ao.gov.sic.sip.dtos.RegistrationRequest;
import ao.gov.sic.sip.dtos.Response;

public interface AuthService {
    Response<?> register(RegistrationRequest registrationRequest);

    Response<LoginResponse> login(LoginRequest loginRequest);
}
