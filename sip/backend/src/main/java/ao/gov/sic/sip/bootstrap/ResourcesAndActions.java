package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.controllers.AuthController;
import ao.gov.sic.sip.dtos.RegistrationRequest;
import ao.gov.sic.sip.entities.Action;
import ao.gov.sic.sip.entities.Resource;
import ao.gov.sic.sip.entities.Role;
import ao.gov.sic.sip.enums.ActionType;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.repositories.ActionRepository;
import ao.gov.sic.sip.repositories.ResourceRepository;
import ao.gov.sic.sip.repositories.RoleRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ao.gov.sic.sip.utils.Constants.*;

@Component
@RequiredArgsConstructor
public class ResourcesAndActions {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthController authController;
    private final ResourceRepository resourceRepository;
    private final ActionRepository actionRepository;

    public void loadData() {
        loadResources();
    }

    private void loadResources() {
        if (resourceRepository.findAll().isEmpty()) {

            Role admin = roleRepository.findByName(ADMIN)
                    .orElseThrow(() -> new NotFoundException("Role not found"));

            Role director = roleRepository.findByName(DIRECTOR)
                    .orElseThrow(() -> new NotFoundException("Role not found"));

            Role instrutor = roleRepository.findByName(INSTRUTOR)
                    .orElseThrow(() -> new NotFoundException("Role not found"));

            Role piquete = roleRepository.findByName(PIQUETE)
                    .orElseThrow(() -> new NotFoundException("Role not found"));

            Role pgr = roleRepository.findByName(PGR)
                    .orElseThrow(() -> new NotFoundException("Role not found"));

            Role secretaria = roleRepository.findByName(SECRETARIA)
                    .orElseThrow(() -> new NotFoundException("Role not found"));


            // ADMIN ROLES AND PERMISSION
            createResource(admin, "crimes", Set.of(
                    Action.builder().name(ActionType.READ).build(),
                    Action.builder().name(ActionType.CREATE).build(),
                    Action.builder().name(ActionType.UPDATE).build(),
                    Action.builder().name(ActionType.DELETE).build()));


            createResource(admin, "processos", Set.of(
                    Action.builder().name(ActionType.READ).build(),
                    Action.builder().name(ActionType.CREATE).build(),
                    Action.builder().name(ActionType.UPDATE).build(),
                    Action.builder().name(ActionType.DELETE).build()));

            // INSTRUTOR ROLES AND PERMISSIONS
            createResource(instrutor, "crimes", Set.of(
                    Action.builder().name(ActionType.READ).build()));

            createResource(instrutor, "processos", Set.of(
                    Action.builder().name(ActionType.READ).build(),
                    Action.builder().name(ActionType.CREATE).build(),
                    Action.builder().name(ActionType.UPDATE).build(),
                    Action.builder().name(ActionType.DELETE).build()));
        }
    }

    private void createResource(Role role, String name, Set<Action> actions) {
        Set<Action> actionsSaved = new HashSet<>(actionRepository.saveAll(actions));

        Resource resource = Resource.builder()
                .role(role)
                .name(name)
                .actions(actionsSaved)
                .build();

        resourceRepository.save(resource);
    }
}
