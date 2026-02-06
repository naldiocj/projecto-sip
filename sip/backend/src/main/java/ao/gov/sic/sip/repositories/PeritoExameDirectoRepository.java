package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.PeritoExameDirecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeritoExameDirectoRepository extends JpaRepository<PeritoExameDirecto, Long> {
    PeritoExameDirecto findAllByNome(String nome);
}
