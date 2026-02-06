package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.LoginRequest;
import ao.gov.sic.sip.dtos.LoginResponse;
import ao.gov.sic.sip.dtos.RegistrationRequest;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Role;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.enums.AuthMethod;
import ao.gov.sic.sip.exceptions.BadRequestException;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.repositories.RoleRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.security.JwtUtils;
import ao.gov.sic.sip.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static ao.gov.sic.sip.utils.Constants.INSTRUTOR;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    @Override
    public Response<?> register(RegistrationRequest registrationRequest) {
        log.info("Starting user registration...");
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new BadRequestException("Email exists");
        }

        List<Role> userRoles;

        if (registrationRequest.getRoles() != null
                && !registrationRequest.getRoles().isEmpty()) {
            userRoles = registrationRequest.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName.toUpperCase())
                            .orElseThrow(() -> new NotFoundException("Role not found"))).toList();
        } else {
            Role defaultRole = roleRepository.findByName(INSTRUTOR)
                    .orElseThrow(() -> new NotFoundException("Role not found"));
            userRoles = List.of(defaultRole);
        }

        User userToSave = new User();
        userToSave.setName(registrationRequest.getName());
        userToSave.setEmail(registrationRequest.getEmail());
        userToSave.setPhoneNumber(registrationRequest.getPhoneNumber());
        userToSave.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userToSave.setRoles(userRoles);
        userToSave.setProvider(AuthMethod.LOCAL);
        userToSave.setActive(true);

        userRepository.save(userToSave);


        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User created successfully")
                .build();
    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {
        log.info("Iniciando o login de usuário ...");
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.isActive()) {
            throw new NotFoundException("User is not active, please contact the admin.");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName).toList();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(roleNames);

        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successful")
                .data(loginResponse)
                .build();
    }
}
