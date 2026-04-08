package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.controllers.AuthController;
import ao.gov.sic.sip.dtos.RegistrationRequest;
import ao.gov.sic.sip.entities.Role;
import ao.gov.sic.sip.repositories.RoleRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static ao.gov.sic.sip.utils.Constants.*;

@Component
@RequiredArgsConstructor
public class RolesAndUsers {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthController authController;

    public void loadData() {
        loadRoles();
        loadUsers();
    }

    private void loadUsers() {
        if (userRepository.findAll().isEmpty()) {
            RegistrationRequest admin = new RegistrationRequest();
            admin.setName("Admin");
            admin.setEmail("admin@sic.gov.ao");
            admin.setPassword("123456");
            admin.setPhoneNumber("-");
            admin.setRoles(List.of(ADMIN, DIRECTOR, INSTRUTOR, PIQUETE, PGR));
            authController.register(admin);

            RegistrationRequest director = new RegistrationRequest();
            director.setName("Director");
            director.setEmail("director@sic.gov.ao");
            director.setPassword("123456");
            director.setPhoneNumber("-");
            director.setRoles(List.of(DIRECTOR));
            authController.register(director);

            RegistrationRequest instrutor = new RegistrationRequest();
            instrutor.setName("Instrutor");
            instrutor.setEmail("instrutor1@sic.gov.ao");
            instrutor.setPassword("123456");
            instrutor.setPhoneNumber("-");
            instrutor.setRoles(List.of(INSTRUTOR));
            authController.register(instrutor);

            RegistrationRequest instrutor1 = new RegistrationRequest();
            instrutor1.setName("Instrutor");
            instrutor1.setEmail("instrutor@sic.gov.ao");
            instrutor1.setPassword("123456");
            instrutor1.setPhoneNumber("-");
            instrutor1.setRoles(List.of(INSTRUTOR));
            authController.register(instrutor1);

            RegistrationRequest piquete = new RegistrationRequest();
            piquete.setName("PIQUETE");
            piquete.setEmail("piquete@sic.gov.ao");
            piquete.setPassword("123456");
            piquete.setPhoneNumber("-");
            piquete.setRoles(List.of(PIQUETE));
            authController.register(piquete);

            RegistrationRequest pgr = new RegistrationRequest();
            pgr.setName("PGR");
            pgr.setEmail("pgr@sic.gov.ao");
            pgr.setPassword("123456");
            pgr.setPhoneNumber("-");
            pgr.setRoles(List.of(PGR));
            authController.register(pgr);

            RegistrationRequest secretaria = new RegistrationRequest();
            secretaria.setName("SECRETARIA");
            secretaria.setEmail("secretaria@sic.gov.ao");
            secretaria.setPassword("123456");
            secretaria.setPhoneNumber("-");
            secretaria.setRoles(List.of(SECRETARIA));
            authController.register(secretaria);
        }
    }

    private void loadRoles() {
        if (roleRepository.findAll().isEmpty()) {

            List<String> rolesNames = List.of(
                    ADMIN,
                    DIRECTOR,
                    INSTRUTOR,
                    PIQUETE,
                    PGR,
                    SECRETARIA
            );
            for (String roleName : rolesNames) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }
}
