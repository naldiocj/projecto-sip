package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findAllByNome(String nome);
}
