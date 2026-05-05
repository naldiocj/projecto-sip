package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.controllers.AuthController;
import ao.gov.sic.sip.dtos.RegistrationRequest;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.enums.AuthMethod;
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

            RegistrationRequest secretariaGeral = new RegistrationRequest();
            secretariaGeral.setName("SECRETARIA GERAL");
            secretariaGeral.setEmail("secretaria.geral@sic.gov.ao");
            secretariaGeral.setPassword("123456");
            secretariaGeral.setPhoneNumber("-");
            secretariaGeral.setRoleName(SECRETARIA_GERAL);
            authController.register(secretariaGeral);

            // news
            RegistrationRequest director1 = new RegistrationRequest();
            director1.setName("Paulo Ambrosio");
            director1.setEmail("director.dcn@sic.gov.ao");
            director1.setPassword("123456");
            director1.setPhoneNumber("-");
            director1.setRoleName(DIRECTOR);
            authController.register(director1);

            RegistrationRequest director2 = new RegistrationRequest();
            director2.setName("Teresa Catate");
            director2.setEmail("directora.rh@sic.gov.ao");
            director2.setPassword("123456");
            director2.setPhoneNumber("-");
            director2.setRoleName(DIRECTOR);
            authController.register(director2);

            RegistrationRequest director3 = new RegistrationRequest();
            director3.setName("Edilson Quibelo");
            director3.setEmail("director.dtti@sic.gov.ao");
            director3.setPassword("123456");
            director3.setPhoneNumber("-");
            director3.setRoleName(DIRECTOR);
            authController.register(director3);

            RegistrationRequest secretaria21= new RegistrationRequest();
            secretaria21.setName("SECRETARIA RH");
            secretaria21.setEmail("secretaria.rh@sic.gov.ao");
            secretaria21.setPassword("123456");
            secretaria21.setPhoneNumber("-");
            secretaria21.setRoleName(SECRETARIA);
            authController.register(secretaria21);

            RegistrationRequest secretaria31= new RegistrationRequest();
            secretaria31.setName("SECRETARIA DCN");
            secretaria31.setEmail("secretaria.dcn@sic.gov.ao");
            secretaria31.setPassword("123456");
            secretaria31.setPhoneNumber("-");
            secretaria31.setRoleName(SECRETARIA);
            authController.register(secretaria31);

            RegistrationRequest secretaria41= new RegistrationRequest();
            secretaria41.setName("SECRETARIA DTTI");
            secretaria41.setEmail("secretaria.dtti@sic.gov.ao");
            secretaria41.setPassword("123456");
            secretaria41.setPhoneNumber("-");
            secretaria41.setRoleName(SECRETARIA);
            authController.register(secretaria41);

            // RH
            RegistrationRequest instrutor1 = new RegistrationRequest();
            instrutor1.setName("Marques Malanga");
            instrutor1.setEmail("instrutor.rh1@sic.gov.ao");
            instrutor1.setPassword("123456");
            instrutor1.setPhoneNumber("-");
            instrutor1.setRoleName(INSTRUTOR);
            authController.register(instrutor1);

            RegistrationRequest instrutor2 = new RegistrationRequest();
            instrutor2.setName("José Martins");
            instrutor2.setEmail("instrutor.rh2@sic.gov.ao");
            instrutor2.setPassword("123456");
            instrutor2.setPhoneNumber("-");
            instrutor2.setRoleName(INSTRUTOR);
            authController.register(instrutor2);

            // DTTI
            RegistrationRequest instrutor3 = new RegistrationRequest();
            instrutor3.setName("Paulo dos Santos");
            instrutor3.setEmail("instrutor.dtti1@sic.gov.ao");
            instrutor3.setPassword("123456");
            instrutor3.setPhoneNumber("-");
            instrutor3.setRoleName(INSTRUTOR);
            authController.register(instrutor3);

            RegistrationRequest instrutor4 = new RegistrationRequest();
            instrutor4.setName("Tavares Carlos");
            instrutor4.setEmail("instrutor.dtti2@sic.gov.ao");
            instrutor4.setPassword("123456");
            instrutor4.setPhoneNumber("-");
            instrutor4.setRoleName(INSTRUTOR);
            authController.register(instrutor4);

            // DCN
            RegistrationRequest instrutor5 = new RegistrationRequest();
            instrutor5.setName("Carlos Inâcio");
            instrutor5.setEmail("instrutor.dcn1@sic.gov.ao");
            instrutor5.setPassword("123456");
            instrutor5.setPhoneNumber("-");
            instrutor5.setRoleName(INSTRUTOR);
            authController.register(instrutor5);

            RegistrationRequest instrutor6 = new RegistrationRequest();
            instrutor6.setName("Mendes Lulas");
            instrutor6.setEmail("instrutor.dcn2@sic.gov.ao");
            instrutor6.setPassword("123456");
            instrutor6.setPhoneNumber("-");
            instrutor6.setRoleName(INSTRUTOR);
            authController.register(instrutor6);
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
