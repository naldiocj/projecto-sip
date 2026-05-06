package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ProcessoResDTO;
import ao.gov.sic.sip.dtos.QueixosoDTO;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ProcessoMapper;
import ao.gov.sic.sip.mappers.QueixosoMapper;
import ao.gov.sic.sip.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueixosoAccessService {

    private final UserService userService;
    private final ProcessoRepository processoRepository;
    private final ProcessoMapper processoMapper;
    private final InstrutorRepository instrutorRepository;
    private final SecretariaRepository secretariaRepository;
    private final DirectorRepository directorRepository;
    private final QueixosoRepository queixosoRepository;
    private final QueixosoMapper queixosoMapper;

    public List<QueixosoDTO> getFilteredQueixosos() {
        User user = userService.currentUser();

        if (user == null) {
            throw new NotFoundException("Usuário não autenticado");
        }

        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        boolean isInstrutor = user.getRoles().stream().anyMatch(role -> role.getName().equals("INSTRUTOR"));
        boolean isSecretaria = user.getRoles().stream().anyMatch(role -> role.getName().equals("SECRETARIA"));
        boolean isSecretariaGeral = user.getRoles().stream().anyMatch(role -> role.getName().equals("SECRETARIA_GERAL"));
        boolean isPgr = user.getRoles().stream().anyMatch(role -> role.getName().equals("PGR"));
        boolean isDirector = user.getRoles().stream().anyMatch(role -> role.getName().equals("DIRECTOR"));

        List<Queixoso> filteredQueixosos;

        if (isAdmin || isSecretariaGeral || isPgr) {
            filteredQueixosos = queixosoRepository.findAll();
        } else if (isInstrutor) {
            Instrutor instrutor = instrutorRepository.findAll().stream()
                    .filter(i -> i.getUser() != null && i.getUser().getId().equals(user.getId())).findFirst()
                    .orElseThrow(() -> new NotFoundException("Instrutor não encontrado para o usuário atual"));

            filteredQueixosos = queixosoRepository.findAll().stream()
                    .filter(queixoso -> queixoso.getUser() != null && queixoso.getUser().getId().equals(instrutor.getUser().getId()))
                    .collect(Collectors.toList());
        } else if (isSecretaria) {
            Secretaria secretaria = secretariaRepository.findAll()
                    .stream()
                    .filter(s -> s.getUser() != null && s.getUser().getId().equals(user.getId())).findFirst()
                    .orElseThrow(() -> new NotFoundException("Secretaria não encontrada para o usuário atual"));

            List<Instrutor> instrutors = instrutorRepository.findAll().stream()
                    .filter(i -> i.getDirecao().getId().equals(secretaria.getDirecao().getId()))
                    .toList();

            List<Queixoso> queixososList = new ArrayList<>();

            for (Instrutor instrutor : instrutors) {
                Optional<Queixoso> queixoso = queixosoRepository.findAll().stream()
                        .filter(q -> q.getUser().getId().equals(instrutor.getUser().getId()))
                        .findFirst();

                queixoso.ifPresent(queixososList::add);
            }

            filteredQueixosos = queixososList;

        } else if (isDirector) {
            Director director = directorRepository.findAll().stream()
                    .filter(d -> d.getUser() != null && d.getUser().getId().equals(user.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Director não encontrado para o usuário atual"));


            List<Instrutor> instrutors = instrutorRepository.findAll().stream()
                    .filter(i -> i.getDirecao().getId().equals(director.getDirecao().getId()))
                    .toList();

            List<Queixoso> queixososList = new ArrayList<>();

            for (Instrutor instrutor : instrutors) {
                Optional<Queixoso> queixoso = queixosoRepository.findAll().stream()
                        .filter(q -> q.getUser().getId().equals(instrutor.getUser().getId()))
                        .findFirst();

                queixoso.ifPresent(queixososList::add);
            }

            filteredQueixosos = queixososList;
        } else {
            filteredQueixosos = queixosoRepository.findAll().stream()
                    .filter(processo -> processo.getUser() != null && processo.getUser().getId().equals(user.getId()))
                    .collect(Collectors.toList());
        }

        return filteredQueixosos.stream()
                .map(queixosoMapper::queixosoToQueixosoDTO)
                .collect(Collectors.toList());
    }
}
