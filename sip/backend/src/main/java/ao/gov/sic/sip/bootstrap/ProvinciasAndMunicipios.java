package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.entities.Municipio;
import ao.gov.sic.sip.entities.Provincia;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.repositories.MunicipioRepository;
import ao.gov.sic.sip.repositories.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProvinciasAndMunicipios {
    private final ProvinciaRepository provinciaRepository;
    private final MunicipioRepository municipioRepository;

    public void loadData() {
        loadProvincias();
        loadMunicipios();
    }

    private void loadProvincias() {
        if (provinciaRepository.findAll().isEmpty()) {
            List<String> provinces = List.of("Bengo",
                    "Benguela",
                    "Bié",
                    "Cabinda",
                    "Cuando",
                    "Cuanza Norte",
                    "Cuanza Sul",
                    "Cubango",
                    "Cunene",
                    "Huambo",
                    "Huíla",
                    "Icolo e Bengo",
                    "Luanda",
                    "Lunda Norte",
                    "Lunda Sul",
                    "Malanje",
                    "Moxico",
                    "Moxico Leste",
                    "Namibe",
                    "Uíge",
                    "Zaire"
            );

            List<Provincia> provinceToSave = provinces.stream()
                    .map(ProvinceName -> Provincia.builder()
                            .nome(ProvinceName)
                            .build())
                    .toList();

            provinciaRepository.saveAll(provinceToSave);
        }
    }
    private void loadMunicipios() {
        if (municipioRepository.findAll().isEmpty()) {
            List<Municipio> municipes = new ArrayList<>();

            // 1. BENGO (provinciaId: 1)
            Provincia provincia1 = provinciaRepository.findById(1L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Ambriz").provincia(provincia1).build());
            municipes.add(Municipio.builder().nome("Bula Atumba").provincia(provincia1).build());
            municipes.add(Municipio.builder().nome("Dande").provincia(provincia1).build());
            municipes.add(Municipio.builder().nome("Dembos").provincia(provincia1).build());
            municipes.add(Municipio.builder().nome("Nambuangongo").provincia(provincia1).build());
            municipes.add(Municipio.builder().nome("Pango Aluquém").provincia(provincia1).build());

            // 2. BENGUELA (provinciaId: 2)
            Provincia provincia2 = provinciaRepository.findById(2L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Balombo").provincia(provincia2).build());
            municipes.add(Municipio.builder().nome("Baía Farta").provincia(provincia2).build());
            municipes.add(Municipio.builder().nome("Benguela").provincia(provincia2).build());
            municipes.add(Municipio.builder().nome("Catumbela").provincia(provincia2).build());
            municipes.add(Municipio.builder().nome("Chongorói").provincia(provincia2).build());
            municipes.add(Municipio.builder().nome("Ganda").provincia(provincia2).build());
            municipes.add(Municipio.builder().nome("Lobito").provincia(provincia2).build());
            municipes.add(Municipio.builder().nome("Caimbambo").provincia(provincia2).build());

            // 3. BIÉ (provinciaId: 3)
            Provincia provincia3 = provinciaRepository.findById(3L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Andulo").provincia(provincia3).build());
            municipes.add(Municipio.builder().nome("Camacupa").provincia(provincia3).build());
            municipes.add(Municipio.builder().nome("Catabola").provincia(provincia3).build());
            municipes.add(Municipio.builder().nome("Chitembo").provincia(provincia3).build());
            municipes.add(Municipio.builder().nome("Chinguar").provincia(provincia3).build());
            municipes.add(Municipio.builder().nome("Cuemba").provincia(provincia3).build());
            municipes.add(Municipio.builder().nome("Cunhinga").provincia(provincia3).build());
            municipes.add(Municipio.builder().nome("Cuíto").provincia(provincia3).build());
            municipes.add(Municipio.builder().nome("Nharea").provincia(provincia3).build());

            // 4. CABINDA (provinciaId: 4)
            Provincia provincia4 = provinciaRepository.findById(4L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Belize").provincia(provincia4).build());
            municipes.add(Municipio.builder().nome("Buco-Zau").provincia(provincia4).build());
            municipes.add(Municipio.builder().nome("Cabinda").provincia(provincia4).build());
            municipes.add(Municipio.builder().nome("Cacongo").provincia(provincia4).build());

            // 5. CUANDO (Nova Província - provinciaId: 5)
            Provincia provincia5 = provinciaRepository.findById(5L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Menongue").provincia(provincia5).build());
            municipes.add(Municipio.builder().nome("Cuíto Cuanavale").provincia(provincia5).build());
            municipes.add(Municipio.builder().nome("Nancova").provincia(provincia5).build());
            municipes.add(Municipio.builder().nome("Cuangar").provincia(provincia5).build());

            // 6. CUANZA NORTE (provinciaId: 6)
            Provincia provincia6 = provinciaRepository.findById(6L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Ambaca").provincia(provincia6).build());
            municipes.add(Municipio.builder().nome("Bangas").provincia(provincia6).build());
            municipes.add(Municipio.builder().nome("Bolongongo").provincia(provincia6).build());
            municipes.add(Municipio.builder().nome("Cambambe").provincia(provincia6).build());
            municipes.add(Municipio.builder().nome("Cazengo").provincia(provincia6).build());
            municipes.add(Municipio.builder().nome("Golungo Alto").provincia(provincia6).build());
            municipes.add(Municipio.builder().nome("Lucala").provincia(provincia6).build());
            municipes.add(Municipio.builder().nome("Quiculungo").provincia(provincia6).build());
            municipes.add(Municipio.builder().nome("Samba Caju").provincia(provincia6).build());

            // 7. CUANZA SUL (provinciaId: 7)
            Provincia provincia7 = provinciaRepository.findById(7L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Amboim").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Cassongue").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Cela").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Conda").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Ebo").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Libolo").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Mussende").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Porto Amboim").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Quibala").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Quilenda").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Seles").provincia(provincia7).build());
            municipes.add(Municipio.builder().nome("Sumbe").provincia(provincia7).build());

            // 8. CUBANGO (Nova Província - provinciaId: 8)
            Provincia provincia8 = provinciaRepository.findById(8L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Calai").provincia(provincia8).build());
            municipes.add(Municipio.builder().nome("Dirico").provincia(provincia8).build());
            municipes.add(Municipio.builder().nome("Mavinga").provincia(provincia8).build());
            municipes.add(Municipio.builder().nome("Rivungo").provincia(provincia8).build());

            // 9. CUNENE (provinciaId: 9)
            Provincia provincia9 = provinciaRepository.findById(9L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Cahama").provincia(provincia9).build());
            municipes.add(Municipio.builder().nome("Cuanhama").provincia(provincia9).build());
            municipes.add(Municipio.builder().nome("Curoca").provincia(provincia9).build());
            municipes.add(Municipio.builder().nome("Cuvelai").provincia(provincia9).build());
            municipes.add(Municipio.builder().nome("Namacunde").provincia(provincia9).build());
            municipes.add(Municipio.builder().nome("Ombadja").provincia(provincia9).build());

            // 10. HUAMBO (provinciaId: 10)
            Provincia provincia10 = provinciaRepository.findById(10L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Bailundo").provincia(provincia10).build());
            municipes.add(Municipio.builder().nome("Caála").provincia(provincia10).build());
            municipes.add(Municipio.builder().nome("Catchiungo").provincia(provincia10).build());
            municipes.add(Municipio.builder().nome("Chicala-Cholohanga").provincia(provincia10).build());
            municipes.add(Municipio.builder().nome("Chinjenje").provincia(provincia10).build());
            municipes.add(Municipio.builder().nome("Ecunha").provincia(provincia10).build());
            municipes.add(Municipio.builder().nome("Huambo").provincia(provincia10).build());
            municipes.add(Municipio.builder().nome("Londuimbali").provincia(provincia10).build());
            municipes.add(Municipio.builder().nome("Longonjo").provincia(provincia10).build());
            municipes.add(Municipio.builder().nome("Mungo").provincia(provincia10).build());
            municipes.add(Municipio.builder().nome("Ucuma").provincia(provincia10).build());

            // 11. HUÍLA (provinciaId: 11)
            Provincia provincia11 = provinciaRepository.findById(11L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Caconda").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Cacula").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Caluquembe").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Chiange").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Chibia").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Chicomba").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Chipindo").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Cuvango").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Humpata").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Jamba").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Lubango").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Matala").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Quilengues").provincia(provincia11).build());
            municipes.add(Municipio.builder().nome("Quipungo").provincia(provincia11).build());

            // 12. ICOLO E BENGO (Nova Província - provinciaId: 12)
            Provincia provincia12 = provinciaRepository.findById(12L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Icolo e Bengo").provincia(provincia12).build());
            municipes.add(Municipio.builder().nome("Quissama").provincia(provincia12).build());
            municipes.add(Municipio.builder().nome("Catete").provincia(provincia12).build());

            // 13. LUANDA (provinciaId: 13)
            Provincia provincia13 = provinciaRepository.findById(13L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Belas").provincia(provincia13).build());
            municipes.add(Municipio.builder().nome("Cacuaco").provincia(provincia13).build());
            municipes.add(Municipio.builder().nome("Cazenga").provincia(provincia13).build());
            municipes.add(Municipio.builder().nome("Luanda").provincia(provincia13).build());
            municipes.add(Municipio.builder().nome("Kilamba Kiaxi").provincia(provincia13).build());
            municipes.add(Municipio.builder().nome("Talatona").provincia(provincia13).build());
            municipes.add(Municipio.builder().nome("Viana").provincia(provincia13).build());

            // 14. LUNDA NORTE (provinciaId: 14)
            Provincia provincia14 = provinciaRepository.findById(14L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Cambulo").provincia(provincia14).build());
            municipes.add(Municipio.builder().nome("Capenda-Camulemba").provincia(provincia14).build());
            municipes.add(Municipio.builder().nome("Caungula").provincia(provincia14).build());
            municipes.add(Municipio.builder().nome("Chitato").provincia(provincia14).build());
            municipes.add(Municipio.builder().nome("Cuilo").provincia(provincia14).build());
            municipes.add(Municipio.builder().nome("Cuango").provincia(provincia14).build());
            municipes.add(Municipio.builder().nome("Lubalo").provincia(provincia14).build());
            municipes.add(Municipio.builder().nome("Lucapa").provincia(provincia14).build());
            municipes.add(Municipio.builder().nome("Xá-Muteba").provincia(provincia14).build());
            municipes.add(Municipio.builder().nome("Lóvua").provincia(provincia14).build());

            // 15. LUNDA SUL (provinciaId: 15)
            Provincia provincia15 = provinciaRepository.findById(15L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Cacolo").provincia(provincia15).build());
            municipes.add(Municipio.builder().nome("Dala").provincia(provincia15).build());
            municipes.add(Municipio.builder().nome("Muconda").provincia(provincia15).build());
            municipes.add(Municipio.builder().nome("Saurimo").provincia(provincia15).build());

            // 16. MALANJE (provinciaId: 16)
            Provincia provincia16 = provinciaRepository.findById(16L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Cacuso").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Calandula").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Cambundi-Catembo").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Cangandala").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Caombo").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Cuaba Nzoji").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Cunda-dia-Baze").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Luquembo").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Malanje").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Marimba").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Massango").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Mucari").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Quela").provincia(provincia16).build());
            municipes.add(Municipio.builder().nome("Quirima").provincia(provincia16).build());

            // 17. MOXICO (provinciaId: 17)
            Provincia provincia17 = provinciaRepository.findById(17L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Bundas").provincia(provincia17).build());
            municipes.add(Municipio.builder().nome("Camanongue").provincia(provincia17).build());
            municipes.add(Municipio.builder().nome("Léua").provincia(provincia17).build());
            municipes.add(Municipio.builder().nome("Luau").provincia(provincia17).build());
            municipes.add(Municipio.builder().nome("Luacano").provincia(provincia17).build());
            municipes.add(Municipio.builder().nome("Luchazes").provincia(provincia17).build());
            municipes.add(Municipio.builder().nome("Moxico").provincia(provincia17).build());

            // 18. MOXICO LESTE (Nova Província - provinciaId: 18)
            Provincia provincia18 = provinciaRepository.findById(18L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Alto Zambeze").provincia(provincia18).build());
            municipes.add(Municipio.builder().nome("Cazombo").provincia(provincia18).build());
            municipes.add(Municipio.builder().nome("Nana Candundo").provincia(provincia18).build());

            // 19. NAMIBE (provinciaId: 19)
            Provincia provincia19 = provinciaRepository.findById(19L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Bibala").provincia(provincia19).build());
            municipes.add(Municipio.builder().nome("Camucuio").provincia(provincia19).build());
            municipes.add(Municipio.builder().nome("Moçâmedes").provincia(provincia19).build());
            municipes.add(Municipio.builder().nome("Virei").provincia(provincia19).build());
            municipes.add(Municipio.builder().nome("Tômbua").provincia(provincia19).build());

            // 20. UÍGE (provinciaId: 20)
            Provincia provincia20 = provinciaRepository.findById(20L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Ambuíla").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Bembe").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Buengas").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Bungo").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Damba").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Milunga").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Mucaba").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Negage").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Puri").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Quimbabele").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Quitexe").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Sanza Pombo").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Songo").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Uíge").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Maquela do Zombo").provincia(provincia20).build());
            municipes.add(Municipio.builder().nome("Cangola").provincia(provincia20).build());

            // 21. ZAIRE (provinciaId: 21)
            Provincia provincia21 = provinciaRepository.findById(21L)
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));

            municipes.add(Municipio.builder().nome("Cuimba").provincia(provincia21).build());
            municipes.add(Municipio.builder().nome("M'banza-Kongo").provincia(provincia21).build());
            municipes.add(Municipio.builder().nome("Noqui").provincia(provincia21).build());
            municipes.add(Municipio.builder().nome("N'zeto").provincia(provincia21).build());
            municipes.add(Municipio.builder().nome("Soyo").provincia(provincia21).build());
            municipes.add(Municipio.builder().nome("Sumbula").provincia(provincia21).build());


            municipioRepository.saveAll(municipes);
        }
    }
}
