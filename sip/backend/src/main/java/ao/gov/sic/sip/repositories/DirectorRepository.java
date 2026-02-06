package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    Director findByNomeCompletoIgnoreCase(String nome);
}
