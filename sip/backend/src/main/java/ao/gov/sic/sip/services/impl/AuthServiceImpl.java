package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.LoginRequest;
import ao.gov.sic.sip.dtos.LoginResponse;
import ao.gov.sic.sip.dtos.RegistrationRequest;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Action;
import ao.gov.sic.sip.entities.Resource;
import ao.gov.sic.sip.entities.Role;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.enums.ActionType;
import ao.gov.sic.sip.enums.AuthMethod;
import ao.gov.sic.sip.exceptions.BadRequestException;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.exceptions.UnauthorizedException;
import ao.gov.sic.sip.repositories.ResourceRepository;
import ao.gov.sic.sip.repositories.RoleRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.security.JwtUtils;
import ao.gov.sic.sip.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ao.gov.sic.sip.utils.Constants.INSTRUTOR;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;
    private final ResourceRepository resourceRepository;

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
