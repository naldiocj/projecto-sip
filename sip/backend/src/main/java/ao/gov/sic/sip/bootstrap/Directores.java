package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.entities.Direcao;
import ao.gov.sic.sip.entities.Director;
import ao.gov.sic.sip.entities.Role;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.enums.AuthMethod;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.repositories.DirecaoRepository;
import ao.gov.sic.sip.repositories.DirectorRepository;
import ao.gov.sic.sip.repositories.RoleRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static ao.gov.sic.sip.utils.Constants.DIRECTOR;

@Component
@RequiredArgsConstructor
public class Directores {
    private final DirectorRepository directorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final DirecaoRepository direcaoRepository;

    public void loadData() {
        loadDirectores();
    }

    private void loadDirectores() {
        if (directorRepository.findAll().isEmpty()) {

            Role role = roleRepository.findByName(DIRECTOR).orElseThrow(() -> new NotFoundException("Role not found"));


            User user1 = userRepository.findByEmail("director.dcn@sic.gov.ao")
                    .orElseThrow(() -> new NotFoundException("User not found"));
            User user2 = userRepository.findByEmail("directora.rh@sic.gov.ao")
                    .orElseThrow(() -> new NotFoundException("User not found"));
            User user3 = userRepository.findByEmail("director.dtti@sic.gov.ao")
                    .orElseThrow(() -> new NotFoundException("User not found"));

            Direcao direcao1 = direcaoRepository.findById(2L)
                    .orElseThrow(() -> new NotFoundException("Direcao not found"));
            Direcao direcao2 = direcaoRepository.findById(10L)
                    .orElseThrow(() -> new NotFoundException("Direcao not found"));
            Direcao direcao3 = direcaoRepository.findById(13L)
                    .orElseThrow(() -> new NotFoundException("Direcao not found"));

            Director d1 = Director.builder()
                    .user(user1)
                    .cargo(null)
                    .direcao(direcao1)
                    .nomeCompleto(user1.getName())
                    .build();

            Director d2 = Director.builder()
                    .user(user2)
                    .cargo(null)
                    .direcao(direcao2)
                    .nomeCompleto(user2.getName())
                    .build();

            Director d3 = Director.builder()
                    .user(user3)
                    .cargo(null)
                    .direcao(direcao3)
                    .nomeCompleto(user3.getName())
                    .build();

            directorRepository.saveAll(List.of(d1, d2, d3));

        }
    }
}
