package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.controllers.AuthController;
import ao.gov.sic.sip.dtos.RegistrationRequest;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
            admin.setRoleName(ADMIN);
            authController.register(admin);

            RegistrationRequest director = new RegistrationRequest();
            director.setName("Director");
            director.setEmail("director@sic.gov.ao");
            director.setPassword("123456");
            director.setPhoneNumber("-");
            director.setRoleName(DIRECTOR);
            authController.register(director);

            RegistrationRequest instrutor = new RegistrationRequest();
            instrutor.setName("Instrutor");
            instrutor.setEmail("instrutor1@sic.gov.ao");
            instrutor.setPassword("123456");
            instrutor.setPhoneNumber("-");
            instrutor.setRoleName(INSTRUTOR);
            authController.register(instrutor);



            RegistrationRequest instrutor1 = new RegistrationRequest();
            instrutor1.setName("Instrutor");
            instrutor1.setEmail("instrutor@sic.gov.ao");
            instrutor1.setPassword("123456");
            instrutor1.setPhoneNumber("-");
            instrutor1.setRoleName(INSTRUTOR);
            authController.register(instrutor1);



            RegistrationRequest piquete = new RegistrationRequest();
            piquete.setName("PIQUETE");
            piquete.setEmail("piquete@sic.gov.ao");
            piquete.setPassword("123456");
            piquete.setPhoneNumber("-");
            piquete.setRoleName(PIQUETE);
            authController.register(piquete);

            RegistrationRequest pgr = new RegistrationRequest();
            pgr.setName("PGR");
            pgr.setEmail("pgr@sic.gov.ao");
            pgr.setPassword("123456");
            pgr.setPhoneNumber("-");
            pgr.setRoleName(PGR);
            authController.register(pgr);

            RegistrationRequest secretaria = new RegistrationRequest();
            secretaria.setName("SECRETARIA");
            secretaria.setEmail("secretaria@sic.gov.ao");
            secretaria.setPassword("123456");
            secretaria.setPhoneNumber("-");
            secretaria.setRoleName(SECRETARIA);
            authController.register(secretaria);

            RegistrationRequest secretariaGeral = new RegistrationRequest();
            secretariaGeral.setName("SECRETARIA GERAL");
            secretariaGeral.setEmail("secretaria.geral@sic.gov.ao");
            secretariaGeral.setPassword("123456");
            secretariaGeral.setPhoneNumber("-");
            secretariaGeral.setRoleName(SECRETARIA_GERAL);
            authController.register(secretariaGeral);

            RegistrationRequest secretaria1 = new RegistrationRequest();
            secretaria1.setName("SECRETARIA 1");
            secretaria1.setEmail("secretaria1@sic.gov.ao");
            secretaria1.setPassword("123456");
            secretaria1.setPhoneNumber("-");
            secretaria1.setRoleName(SECRETARIA);
            authController.register(secretaria1);
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
                    SECRETARIA,
                    SECRETARIA_GERAL
            );
            for (String roleName : rolesNames) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }
}
