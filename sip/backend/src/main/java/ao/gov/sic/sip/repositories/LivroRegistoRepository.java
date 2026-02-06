package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.LivroRegisto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRegistoRepository extends JpaRepository<LivroRegisto, Long> {
    LivroRegisto findAllByNumeroLivro(String numeroLivro);
}
