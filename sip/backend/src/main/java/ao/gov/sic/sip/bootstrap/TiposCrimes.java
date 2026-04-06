package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.entities.TipoCrime;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.repositories.TipoCrimeRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TiposCrimes {
    private final TipoCrimeRepository tipoCrimeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void loadData() {
        if (tipoCrimeRepository.count() == 0) {
            User user = userRepository.findById(1L).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

            List<TipoCrime> crimes = Arrays.asList(
                    // Crimes contra as Pessoas
                    addTipoCrime("Art. 145º", "Homicídio: Tirar a vida de outrem.", user),
                    addTipoCrime("Art. 155º", "Ofensa à integridade física grave: Lesões corporais graves.", user),
                    addTipoCrime("Art. 182º", "Violência doméstica: Maus-tratos no âmbito familiar.", user),

                    // Crimes contra o Património
                    addTipoCrime("Art. 396º", "Furto: Subtração de coisa móvel alheia.", user),
                    addTipoCrime("Art. 404º", "Roubo: Subtração com violência ou ameaça grave.", user),
                    addTipoCrime("Art. 417º", "Burla: Obtenção de enriquecimento ilegítimo por erro/engano.", user),

                    // Crimes contra a Ordem Pública (Corrupção/Estado)
                    addTipoCrime("Art. 351º", "Peculato: Apropriação de bens públicos por funcionário.", user),
                    addTipoCrime("Art. 359º", "Corrupção Passiva: Solicitação de vantagem indevida.", user),
                    addTipoCrime("Art. 362º", "Corrupção Ativa: Promessa ou oferta de vantagem indevida.", user),
                    addTipoCrime("Art. 337º", "Tráfico de Influência.", user),
                    addTipoCrime("Art. 374º", "Abuso de Poder: Uso do cargo para fins ilícitos.", user),

                    // Crimes contra a Paz Pública
                    addTipoCrime("Art. 294º", "Associação Criminosa: Grupo organizado para cometer crimes.", user)
            );

            tipoCrimeRepository.saveAll(crimes);
            System.out.println("Bulk Insert concluído: " + crimes.size() + " crimes inseridos.");
        }
    }

    private TipoCrime addTipoCrime(String artigo, String descricao, User user) {
        TipoCrime crime = new TipoCrime();
        crime.setArtigoPenal(artigo);
        crime.setDescricao(descricao);
        crime.setUser(user);
        return crime;
    }
}
