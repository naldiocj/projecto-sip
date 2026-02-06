package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.entities.Categoria;
import ao.gov.sic.sip.entities.Patente;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.repositories.CategoriaRepository;
import ao.gov.sic.sip.repositories.PatenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static ao.gov.sic.sip.utils.Constants.*;

@Component
@RequiredArgsConstructor
public class CategoriesAndPatentes {
    private final CategoriaRepository categoriaRepository;
    private final PatenteRepository patenteRepository;

    public void loadData() {
        loadCategories();
        loadPatentes();
    }

    private void loadCategories() {
        if (categoriaRepository.count() == 0) {
            Categoria categoria1 = Categoria
                    .builder()
                    .nome(COMISSARIO)
                    .build();

            Categoria categoria2 = Categoria
                    .builder()
                    .nome(OFICIAL_SUPERIOR)
                    .build();

            Categoria categoria3 = Categoria
                    .builder()
                    .nome(OFICIAL_SUBALTERNO)
                    .build();

            Categoria categoria4 = Categoria
                    .builder()
                    .nome(SUBCHEFE)
                    .build();

            Categoria categoria5 = Categoria
                    .builder()
                    .nome(AGENTE)
                    .build();

            categoriaRepository.save(categoria1);
            categoriaRepository.save(categoria2);
            categoriaRepository.save(categoria3);
            categoriaRepository.save(categoria4);
            categoriaRepository.save(categoria5);
        }
    }

    private void loadPatentes() {
        if (patenteRepository.count() == 0) {
            Categoria savedCategoria1 = getCategoriaByNome(COMISSARIO);
            Categoria savedCategoria2 = getCategoriaByNome(OFICIAL_SUPERIOR);
            Categoria savedCategoria3 = getCategoriaByNome(OFICIAL_SUBALTERNO);
            Categoria savedCategoria4 = getCategoriaByNome(SUBCHEFE);
            Categoria savedCategoria5 = getCategoriaByNome(AGENTE);

            List<Patente> patentes = List.of(
                    Patente.builder()
                            .nome("Comissário Chefe")
                            .categoria(savedCategoria1)
                            .build(),
                    Patente.builder()
                            .nome("Comissário")
                            .categoria(savedCategoria1)
                            .build(),
                    Patente.builder()
                            .nome("Subcomissário")
                            .categoria(savedCategoria1)
                            .build(),
                    Patente.builder()
                            .nome("Superintendente-chefe")
                            .categoria(savedCategoria2)
                            .build(),
                    Patente.builder()
                            .nome("Superintendente")
                            .categoria(savedCategoria2)
                            .build(),
                    Patente.builder()
                            .nome("Intendente")
                            .categoria(savedCategoria2)
                            .build(),
                    Patente.builder()
                            .nome("Inspector-chefe")
                            .categoria(savedCategoria3)
                            .build(),
                    Patente.builder()
                            .nome("Inspector")
                            .categoria(savedCategoria3)
                            .build(),
                    Patente.builder()
                            .nome("Subinspector")
                            .categoria(savedCategoria3)
                            .build(),
                    Patente.builder()
                            .nome("1º Subchefe")
                            .categoria(savedCategoria4)
                            .build(),
                    Patente.builder()
                            .nome("2º Subchefe")
                            .categoria(savedCategoria4)
                            .build(),
                    Patente.builder()
                            .nome("3º Subchefe")
                            .categoria(savedCategoria4)
                            .build(),
                    Patente.builder()
                            .nome("Agente de 1º Classe")
                            .categoria(savedCategoria5)
                            .build(),
                    Patente.builder()
                            .nome("Agente de 2º Classe")
                            .categoria(savedCategoria5)
                            .build(),
                    Patente.builder()
                            .nome("Agente de 3ª Classe")
                            .categoria(savedCategoria5)
                            .build()
            );

            patenteRepository.saveAll(patentes);
        }
    }

    private Categoria getCategoriaByNome(String nome) {
        return categoriaRepository.findAll()
                .stream()
                .filter(category -> category.getNome().equals(nome))
                .findFirst().orElseThrow(() -> new NotFoundException("Category not found"));
    }

}
