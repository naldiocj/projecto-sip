package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Instrutores {
    private final InstrutorRepository instrutorRepository;
    private final DirecaoRepository direcaoRepository;
    private final PatenteRepository patenteRepository;
    private final UserRepository userRepository;

    public void loadData() {
        Optional<User> user1 = userRepository.findByEmail("instrutor1@sic.gov.ao");
        Optional<Direcao> direcao1 = direcaoRepository.findById(10L);
        Optional<Patente> patente1 = patenteRepository.findById(15L);
        createInstrutor("Instrutor 1", user1, direcao1, patente1);

        Optional<User> user2 = userRepository.findByEmail("instrutor@sic.gov.ao");
        Optional<Direcao> direcao2 = direcaoRepository.findById(5L);
        Optional<Patente> patente2 = patenteRepository.findById(14L);

        createInstrutor("Instrutor 2", user2, direcao2, patente2);
    }

    private void createInstrutor(String nome, Optional<User> user, Optional<Direcao> direcao, Optional<Patente> patente) {

        if (user.isPresent() && direcao.isPresent() && patente.isPresent()) {
            if (instrutorRepository.findByUser(user.get()).isPresent()) {
                log.info("Instrutor com o email '{}' já existe. Ignorando...", user.get().getEmail());
                return;
            }

            Instrutor instrutor = Instrutor.builder()
                    .user(user.get())
                    .nomeCompleto(nome)
                    .patente(patente.get())
                    .direcao(direcao.get())
                    .build();
            instrutorRepository.save(instrutor);
            log.info("Instrutor '{}' criada com sucesso.", nome);
        } else {
            log.error("Não foi possível criar a instrutor '{}': Dependências (User, Direção ou Patente) não encontradas.", nome);
        }

    }
}
