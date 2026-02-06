package ao.gov.sic.sip.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final CategoriesAndPatentes categoriesAndPatentes;
    private final RolesAndUsers rolesAndUsers;
    private final ProvinciasAndMunicipios provinciasAndMunicipios;
    private final Distritos distritos;

    @Override
    public void run(String... args) throws Exception {
        rolesAndUsers.loadData();
        categoriesAndPatentes.loadData();
        provinciasAndMunicipios.loadData();
        distritos.loadData();
    }
}
