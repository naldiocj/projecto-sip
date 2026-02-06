package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Cargo findByNomeIgnoreCase(String nome);
}
