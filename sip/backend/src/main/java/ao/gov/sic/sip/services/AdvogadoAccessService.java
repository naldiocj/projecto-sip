package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AdvogadoDTO;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AdvogadoMapper;
import ao.gov.sic.sip.repositories.AdvogadoRepository;
import ao.gov.sic.sip.repositories.DirectorRepository;
import ao.gov.sic.sip.repositories.InstrutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvogadoAccessService {

    private final UserService userService;
    private final AdvogadoRepository advogadoRepository;
    private final AdvogadoMapper advogadoMapper;
    private final InstrutorRepository instrutorRepository;
    private final DirectorRepository directorRepository;

    public List<AdvogadoDTO> getFilteredAdvogados() {
        User user = userService.currentUser();

        if (user == null) {
            throw new NotFoundException("Usuário não autenticado");
        }

        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        boolean isSecretariaGeral = user.getRoles().stream().anyMatch(role -> role.getName().equals("SECRETARIA_GERAL"));
        boolean isInstrutor = user.getRoles().stream().anyMatch(role -> role.getName().equals("INSTRUTOR"));
        boolean isDirector = user.getRoles().stream().anyMatch(role -> role.getName().equals("DIRECTOR"));
        boolean isPgr = user.getRoles().stream().anyMatch(role -> role.getName().equals("PGR"));

        List<Advogado> filteredAdvogados;

        if (isAdmin || isSecretariaGeral || isPgr) {
            filteredAdvogados = advogadoRepository.findAll();
        } else if (isInstrutor) {
            Instrutor instrutor = instrutorRepository.findAll().stream()
                    .filter(i -> i.getUser() != null && i.getUser().getId().equals(user.getId())).findFirst()
                    .orElseThrow(() -> new NotFoundException("Instrutor não encontrado para o usuário atual"));

            filteredAdvogados = advogadoRepository.findAll().stream()
                    .filter(queixoso -> queixoso.getUser() != null && queixoso.getUser().getId().equals(instrutor.getUser().getId()))
                    .collect(Collectors.toList());
        }  else if (isDirector) {
            Director director = directorRepository.findAll().stream()
                    .filter(d -> d.getUser() != null && d.getUser().getId().equals(user.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Director não encontrado para o usuário atual"));


            List<Instrutor> instrutors = instrutorRepository.findAll().stream()
                    .filter(i -> i.getDirecao().getId().equals(director.getDirecao().getId()))
                    .toList();

            List<Advogado> arguidosList = new ArrayList<>();

            for (Instrutor instrutor : instrutors) {
                Optional<Advogado> advogado = advogadoRepository.findAll().stream()
                        .filter(q -> q.getUser().getId().equals(instrutor.getUser().getId()))
                        .findFirst();

                advogado.ifPresent(arguidosList::add);
            }

            filteredAdvogados = arguidosList;
        } else {
            // For all other roles, they can only see advogados associated with their user ID.
            filteredAdvogados = advogadoRepository.findAll().stream()
                    .filter(advogado -> advogado.getUser() != null && advogado.getUser().getId().equals(user.getId()))
                    .collect(Collectors.toList());
        }

        return filteredAdvogados.stream()
                .map(advogadoMapper::advogadoToAdvogadoDTO)
                .collect(Collectors.toList());
    }
}
