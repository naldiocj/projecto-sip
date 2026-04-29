package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.LoginRequest;
import ao.gov.sic.sip.dtos.LoginResponse;
import ao.gov.sic.sip.dtos.RegistrationRequest;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.enums.ActionType;
import ao.gov.sic.sip.enums.AuthMethod;
import ao.gov.sic.sip.enums.SecretariaType;
import ao.gov.sic.sip.exceptions.BadRequestException;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.exceptions.UnauthorizedException;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.security.JwtUtils;
import ao.gov.sic.sip.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ao.gov.sic.sip.utils.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;
    private final ResourceRepository resourceRepository;
    private final InstrutorRepository instrutorRepository;
    private final SecretariaRepository secretariaRepository;

    @Override
    public Response<?> register(RegistrationRequest registrationRequest) {
        log.info("Starting user registration...");
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new BadRequestException("Email exists");
        }

        Role role;

        if (registrationRequest.getRoleName() != null
                && !registrationRequest.getRoleName().isEmpty()) {
            role = roleRepository.findByName(registrationRequest.getRoleName().toUpperCase())
                    .orElseThrow(() -> new NotFoundException("Role not found"));
        } else {
            role = roleRepository.findByName(INSTRUTOR)
                    .orElseThrow(() -> new NotFoundException("Role not found"));
        }

        User userToSave = new User();
        userToSave.setName(registrationRequest.getName());
        userToSave.setEmail(registrationRequest.getEmail());
        userToSave.setPhoneNumber(registrationRequest.getPhoneNumber());
        userToSave.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userToSave.setRoles(List.of(role));
        userToSave.setProvider(AuthMethod.LOCAL);
        userToSave.setActive(true);

        User user = userRepository.save(userToSave);


        if (role.getName().equals(INSTRUTOR)) {
            instrutorRepository.save(Instrutor.builder()
                    .nomeCompleto(userToSave.getName())
                    .user(userToSave)
                    .build());
        } else if (role.getName().equals(SECRETARIA)) {
            secretariaRepository.save(Secretaria.builder()
                    .nomeCompleto(userToSave.getName())
                    .user(userToSave)
                    .type(SecretariaType.ORGAO)
                    .build());
        } else if (role.getName().equals(SECRETARIA_GERAL)) {
            secretariaRepository.save(Secretaria.builder()
                    .nomeCompleto(userToSave.getName())
                    .user(userToSave)
                    .type(SecretariaType.GERAL)
                    .build());
        }

        // TODO other here



        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .data(Map.of("id", user.getId()))
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
            throw new UnauthorizedException("Invalid credentials");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName).toList();

        List<Long> rolesIds = user.getRoles().stream()
                .map(Role::getId).toList();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(roleNames);

       for (Long roleId : rolesIds) {
          Map<String, Set<ActionType>> actions = new HashMap<>();
          List<Resource> resources = resourceRepository.findAllByRole_Id(roleId);

          for (Resource resource : resources) {
              Set<Action> resourceActions = resource.getActions();
              actions.put(resource.getName(), resourceActions.stream().map(Action::getName).collect(Collectors.toSet()));
          }

          loginResponse.setActions(actions);
       }

        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successful")
                .data(loginResponse)
                .build();
    }
}
