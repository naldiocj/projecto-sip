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
        //RH
        Optional<User> user1 = userRepository.findByEmail("instrutor.rh1@sic.gov.ao");
        Optional<Direcao> direcao1 = direcaoRepository.findById(10L);
        Optional<Patente> patente1 = patenteRepository.findById(10L);
        createInstrutor("Paulo Novais", user1, direcao1, patente1);

        Optional<User> user2 = userRepository.findByEmail("instrutor.rh2@sic.gov.ao");
        Optional<Direcao> direcao2 = direcaoRepository.findById(10L);
        Optional<Patente> patente2 = patenteRepository.findById(10L);
        createInstrutor("Armando Gulundo", user2, direcao2, patente2);

        // DTTI
        Optional<User> user21 = userRepository.findByEmail("instrutor.dtti1@sic.gov.ao");
        Optional<Direcao> direcao21 = direcaoRepository.findById(13L);
        Optional<Patente> patente21 = patenteRepository.findById(13L);
        createInstrutor("Ricardo Martins", user21, direcao21, patente21);

        Optional<User> user22 = userRepository.findByEmail("instrutor.dtti2@sic.gov.ao");
        Optional<Direcao> direcao22 = direcaoRepository.findById(13L);
        Optional<Patente> patente22 = patenteRepository.findById(13L);
        createInstrutor("Ana Paula", user22, direcao22, patente22);

        // DCN
        Optional<User> user31 = userRepository.findByEmail("instrutor.dcn1@sic.gov.ao");
        Optional<Direcao> direcao31 = direcaoRepository.findById(2L);
        Optional<Patente> patente31 = patenteRepository.findById(15L);
        createInstrutor("Marta Gomalo", user31, direcao31, patente31);

        Optional<User> user32 = userRepository.findByEmail("instrutor.dcn2@sic.gov.ao");
        Optional<Direcao> direcao32 = direcaoRepository.findById(2L);
        Optional<Patente> patente32 = patenteRepository.findById(15L);
        createInstrutor("Tercio Rodrigues", user32, direcao32, patente32);
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
