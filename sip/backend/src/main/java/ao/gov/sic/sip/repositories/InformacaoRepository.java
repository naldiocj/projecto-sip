package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Informacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformacaoRepository extends JpaRepository<Informacao, Long> {
}
