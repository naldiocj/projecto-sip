package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ProcessoResDTO;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ProcessoMapper;
import ao.gov.sic.sip.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessoAccessService {

    private final UserService userService;
    private final ProcessoRepository processoRepository;
    private final ProcessoMapper processoMapper;
    private final InstrutorRepository instrutorRepository;
    private final SecretariaRepository secretariaRepository;
    private final DirectorRepository directorRepository;

    public List<ProcessoResDTO> getFilteredProcessos(Specification<Processo> spec) {
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

        List<Processo> filteredProcessos;

        if (isAdmin || isSecretariaGeral || isPgr) {
            filteredProcessos = processoRepository.findAll(spec);
        } else if (isInstrutor) {
            Instrutor instrutor = instrutorRepository.findAll().stream()
                    .filter(i -> i.getUser() != null && i.getUser().getId().equals(user.getId())).findFirst()
                    .orElseThrow(() -> new NotFoundException("Instrutor não encontrado para o usuário atual"));

            filteredProcessos = processoRepository.findAll(spec).stream()
                    .filter(processo -> processo.getInstrutor() != null && processo.getInstrutor().getId().equals(instrutor.getId()))
                    .collect(Collectors.toList());
        } else if (isSecretaria) {
            Secretaria secretaria = secretariaRepository.findAll()
                    .stream()
                    .filter(s -> s.getUser() != null && s.getUser().getId().equals(user.getId())).findFirst()
                    .orElseThrow(() -> new NotFoundException("Secretaria não encontrada para o usuário atual"));

            filteredProcessos = processoRepository.findAll(spec).stream()
                    .filter(processo -> processo.getDirecao() != null && secretaria.getDirecao() != null && processo.getDirecao().getId().equals(secretaria.getDirecao().getId()))
                    .collect(Collectors.toList());
        } else if (isDirector) {
            Director director = directorRepository.findAll().stream()
                    .filter(d -> d.getUser() != null && d.getUser().getId().equals(user.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Director não encontrado para o usuário atual"));

            filteredProcessos = processoRepository.findAll(spec).stream()
                    .filter(processo -> processo.getDirecao() != null && director.getDirecao() != null && processo.getDirecao().getId().equals(director.getDirecao().getId()))
                    .collect(Collectors.toList());
        } else {
            // Default: User can only see their own processes
            filteredProcessos = processoRepository.findAll(spec).stream()
                    .filter(processo -> processo.getUser() != null && processo.getUser().getId().equals(user.getId()))
                    .collect(Collectors.toList());
        }

        return filteredProcessos.stream()
                .map(processoMapper::processoToProcessoResDTO)
                .peek(p -> {
                    // Ensure Direcao is null if not explicitly set or mapped
                    if (p.getDirecao() != null && p.getDirecao().getId() == null) {
                        p.setDirecao(null);
                    }
                })
                .collect(Collectors.toList());
    }
}
