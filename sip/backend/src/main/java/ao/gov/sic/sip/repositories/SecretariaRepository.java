package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Secretaria;
import ao.gov.sic.sip.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecretariaRepository extends JpaRepository<Secretaria, Long> {
    Optional<Secretaria> findByUser(User user);
}
