package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.entities.Direcao;
import ao.gov.sic.sip.entities.Patente;
import ao.gov.sic.sip.entities.Secretaria;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.repositories.DirecaoRepository;
import ao.gov.sic.sip.repositories.PatenteRepository;
import ao.gov.sic.sip.repositories.SecretariaRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Secretarias {
    private final DirecaoRepository direcaoRepository;
    private final PatenteRepository patenteRepository;
    private final UserRepository userRepository;
    private final SecretariaRepository secretariaRepository;

    public void loadData() {
        Optional<User> user1 = userRepository.findByEmail("secretaria@sic.gov.ao");
        Optional<Direcao> direcao1 = direcaoRepository.findById(10L);
        Optional<Patente> patente1 = patenteRepository.findById(15L);
        createSecretaria("Secretaria", user1, direcao1, patente1);

        Optional<User> user2 = userRepository.findByEmail("secretaria.geral@sic.gov.ao");
        Optional<Direcao> direcao2 = direcaoRepository.findById(5L);
        Optional<Patente> patente2 = patenteRepository.findById(14L);

        createSecretaria("Secretaria Geral", user2, direcao2, patente2);
    }

    private void createSecretaria(String nome, Optional<User> user, Optional<Direcao> direcao, Optional<Patente> patente) {
        if (user.isPresent() && direcao.isPresent() && patente.isPresent()) {
            if (secretariaRepository.findByUser(user.get()).isPresent()) {
                log.info("Instrutor com o email '{}' já existe. Ignorando...", user.get().getEmail());
                return;
            }
            Secretaria secretaria = Secretaria.builder()
                    .user(user.get())
                    .nomeCompleto(nome)
                    .patente(patente.get())
                    .direcao(direcao.get())
                    .build();
            secretariaRepository.save(secretaria);
            log.info("Secretaria '{}' criada com sucesso.", nome);
        } else {
            log.error("Não foi possível criar a secretaria '{}': Dependências (User, Direção ou Patente) não encontradas.", nome);
        }
    }
}
