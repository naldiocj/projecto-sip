package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Magistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MagistradoRepository extends JpaRepository<Magistrado, Long> {
    @Query("SELECT m FROM Magistrado m WHERE LOWER(m.nomeCompleto) = LOWER(:nome)")
    Magistrado findByNomeIgnoreCase(@Param("nomeCompleto") String nomeCompleto);
}
