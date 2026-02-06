package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.RemessaCartaPrecatoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemessaCartaPrecatoriaRepository extends JpaRepository<RemessaCartaPrecatoria, Long> {
    RemessaCartaPrecatoria findAllByNumeroOficio(String numeroOficio);
}
