package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Testemunha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestemunhaRepository extends JpaRepository<Testemunha, Long> {
    Testemunha findByNomeCompleto(String nomeCompleto);
}
