package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.TipoCrime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCrimeRepository extends JpaRepository<TipoCrime, Long> {
    TipoCrime findAllByArtigoPenal(String artigoPenal);
}
