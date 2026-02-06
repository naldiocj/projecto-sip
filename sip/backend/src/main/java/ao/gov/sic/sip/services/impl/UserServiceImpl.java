package ao.gov.sic.sip.services.impl;


import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.UserDTO;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.UserMapper;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public User currentUser() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    @Transactional
    public Response<?> updateMyAccount(UserDTO userDTO) {
        log.info("Iniciando a actualização de conta de usuário ...");

        User user = currentUser();

        if (StringUtils.hasText(userDTO.getName())) {
            user.setName(userDTO.getName());
        }
        if (StringUtils.hasText(userDTO.getPhoneNumber())) {
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }
        if (StringUtils.hasText(userDTO.getEmail())) {
            user.setEmail(userDTO.getEmail());
        }
        if (StringUtils.hasText(userDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User updated successfully")
                .build();
    }

    @Override
    public Response<?> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User deleted successfully")
                .build();
    }

    @Override
    public Response<List<UserDTO>> getAllUsers() {
        log.info("Iniciando a lista de usuários ...");
        List<UserDTO> pilots = userRepository.findAll().stream()
                .map(userMapper::userToUserDTO)
                .toList();

        return Response.<List<UserDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(pilots.isEmpty() ? "User not found" : "Users retrieved successfully")
                .data(pilots)
                .build();
    }

    @Override
    public Response<UserDTO> getAccountDetails() {
        log.info("Iniciando o Detalhes da conta de usuário ...");
        User user = currentUser();
        UserDTO userDTO = userMapper.userToUserDTO(user);

        return Response.<UserDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User retrieved successfully")
                .data(userDTO)
                .build();
    }
}
