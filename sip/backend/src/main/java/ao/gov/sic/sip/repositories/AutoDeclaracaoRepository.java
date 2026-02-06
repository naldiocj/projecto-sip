package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoDeclaracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoDeclaracaoRepository extends JpaRepository<AutoDeclaracao, Long> {
}
