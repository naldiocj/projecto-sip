package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.entities.Distrito;
import ao.gov.sic.sip.entities.Municipio;
import ao.gov.sic.sip.repositories.DistritoRepository;
import ao.gov.sic.sip.repositories.MunicipioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Distritos {
    private final DistritoRepository distritoRepository;
    private final MunicipioRepository municipioRepository;

    public void loadData() {
        loadDistritos();
    }

    private void saveDistrito(String municipioNome, String... distritos) {
        Municipio foundedMunicipio = municipioRepository.findByNomeIgnoreCase(municipioNome);

        if (foundedMunicipio != null) {

            for (String distritoNome : distritos) {
                Distrito distritoToSave =  Distrito.builder()
                        .nome(distritoNome)
                        .municipio(foundedMunicipio)
                        .build();
                distritoRepository.save(distritoToSave);
            }
        }
    }

    private void loadDistritos() {
        if (distritoRepository.findAll().isEmpty()) {
            // 1. LUANDA (Capital: Luanda)
            saveDistrito("Luanda", "Ingombota", "Maianga", "Sambizanga", "Rangel");
            saveDistrito("Viana", "Zango", "Baia", "Kikuxi");
            saveDistrito("Cacuaco", "Kicolo", "Funda");
            saveDistrito("Cazenga", "Hoji-ya-Henda", "Tala-Hady");
            saveDistrito("Belas", "Quenguela", "Vila Flor");
            saveDistrito("Kilamba Kiaxi", "Golfe", "Sapú");
            saveDistrito("Talatona", "Benfica", "Camama");

            // 2. ÍCOLO E BENGO (Capital: Catete) - NOVA
            saveDistrito("Ícolo e Bengo", "Catete", "Bela Vista");
            saveDistrito("Quiçama", "Muxima", "Cabo Ledo");

            // 3. BENGO (Capital: Caxito)
            saveDistrito("Dande", "Caxito", "Mabubas");
            saveDistrito("Ambriz", "Bula Atumba", "Dembos", "Nambuangongo");

            // 4. BENGUELA (Capital: Benguela)
            saveDistrito("Benguela", "Zona A", "Zona B", "Cassequel");
            saveDistrito("Lobito", "Canata", "Compão", "Restinga");
            saveDistrito("Catumbela", "Baía Farta", "Ganda", "Cubal", "Bocoio");

            // 5. CABINDA (Capital: Cabinda)
            saveDistrito("Cabinda", "Sede", "Tando Zinze");
            saveDistrito("Cacongo", "Buco-Zau", "Belize");

            // 6. CUANZA NORTE (Capital: Ndalatando)
            saveDistrito("Cazengo", "Ndalatando");
            saveDistrito("Cambambe", "Lucala", "Samba Caju", "Ambaca");

            // 7. CUANZA SUL (Capital: Sumbe)
            saveDistrito("Sumbe", "Sede", "Quicombo");
            saveDistrito("Porto Amboim", "Cela", "Quibala", "Libolo");

            // 8. HUAMBO (Capital: Huambo)
            saveDistrito("Huambo", "Sede", "Bairro Benfica");
            saveDistrito("Caála", "Bailundo", "Londuimbali", "Ecunha");

            // 9. HUÍLA (Capital: Lubango)
            saveDistrito("Lubango", "Sede", "Humpata", "Chibia");
            saveDistrito("Matala", "Jamba", "Caconda", "Quipungo");

            // 10. NAMIBE (Capital: Moçâmedes)
            saveDistrito("Moçâmedes", "Sede", "Saco Mar");
            saveDistrito("Tômbwa", "Bibala", "Virei", "Camucuio");

            // 11. CUNENE (Capital: Ondjiva)
            saveDistrito("Cuanhama", "Ondjiva");
            saveDistrito("Ombadja", "Namacunde", "Cahama", "Cuvelai");

            // 12. MALANJE (Capital: Malanje)
            saveDistrito("Malanje", "Sede", "Catepa");
            saveDistrito("Cacuso", "Calandula", "Cangandala", "Quela");

            // 13. BIÉ (Capital: Cuíto)
            saveDistrito("Cuíto", "Sede", "Chinguar", "Andulo", "Camacupa");

            // 14. UÍGE (Capital: Uíge)
            saveDistrito("Uíge", "Sede", "Negage", "Songo", "Maquela do Zombo");

            // 15. ZAIRE (Capital: Mbanza Congo)
            saveDistrito("Mbanza Congo", "Soyo", "Nzeto", "Cuimba");

            // 16. LUNDA NORTE (Capital: Dundo)
            saveDistrito("Chitato", "Dundo", "Lucapa", "Cambulo");

            // 17. LUNDA SUL (Capital: Saurimo)
            saveDistrito("Saurimo", "Sede", "Dala", "Muconda");

            // 18. MOXICO (Capital: Luena)
            saveDistrito("Moxico", "Luena", "Camanongue", "Léua");

            // 19. MOXICO LESTE (Capital: Cazombo) - NOVA
            saveDistrito("Alto Zambeze", "Cazombo", "Nana Candundo");
            saveDistrito("Bundas", "Lumbala Nguimbo");

            // 20. CUANDO (Capital: Mavinga) - NOVA
            saveDistrito("Mavinga", "Sede", "Rivungo");

            // 21. CUBANGO (Capital: Menongue) - NOVA
            saveDistrito("Menongue", "Cuchi", "Cuíto Cuanavale");
        }
    }
}
