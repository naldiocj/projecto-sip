package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.entities.Direcao;
import ao.gov.sic.sip.entities.Patente;
import ao.gov.sic.sip.entities.Secretaria;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.enums.SecretariaType;
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
        Optional<User> user2 = userRepository.findByEmail("secretaria.geral@sic.gov.ao");
        Optional<Direcao> direcao2 = direcaoRepository.findById(5L);
        Optional<Patente> patente2 = patenteRepository.findById(14L);

        createSecretaria("Secretaria Geral", user2, direcao2, patente2, SecretariaType.GERAL);

        Optional<User> user4 = userRepository.findByEmail("secretaria.rh@sic.gov.ao");
        Optional<Direcao> direcao4 = direcaoRepository.findById(10L);
        Optional<Patente> patente4 = patenteRepository.findById(6L);
        createSecretaria("Secretaria RH", user4, direcao4, patente4, null);

        Optional<User> user5 = userRepository.findByEmail("secretaria.dcn@sic.gov.ao");
        Optional<Direcao> direcao5 = direcaoRepository.findById(2L);
        Optional<Patente> patente5 = patenteRepository.findById(6L);
        createSecretaria("Secretaria RH", user5, direcao5, patente5, null);

        Optional<User> user6 = userRepository.findByEmail("secretaria.dtti@sic.gov.ao");
        Optional<Direcao> direcao6 = direcaoRepository.findById(13L);
        Optional<Patente> patente6 = patenteRepository.findById(6L);
        createSecretaria("Secretaria RH", user6, direcao6, patente6, null);
    }

    private void createSecretaria(String nome, Optional<User> user, Optional<Direcao> direcao, Optional<Patente> patente, SecretariaType type) {
        if (user.isPresent() && direcao.isPresent() && patente.isPresent()) {
            if (secretariaRepository.findAll().stream().anyMatch(s -> s.getUser().getEmail().equals(user.get().getEmail()))) {
                log.info("Instrutor com o email '{}' já existe. Ignorando...", user.get().getEmail());
                return;
            }
            Secretaria secretaria = Secretaria.builder()
                    .user(user.get())
                    .nomeCompleto(nome)
                    .patente(patente.get())
                    .type(type == null ? SecretariaType.ORGAO : type)
                    .direcao(direcao.get())
                    .build();
            secretariaRepository.save(secretaria);
            log.info("Secretaria '{}' criada com sucesso.", nome);
        } else {
            log.error("Não foi possível criar a secretaria '{}': Dependências (User, Direção ou Patente) não encontradas.", nome);
        }
    }
}
