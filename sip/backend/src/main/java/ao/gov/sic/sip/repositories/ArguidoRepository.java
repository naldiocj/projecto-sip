package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Arguido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArguidoRepository extends JpaRepository<Arguido, Long> {
    @Query("SELECT m FROM Arguido m WHERE LOWER(m.nomeCompleto) = LOWER(:nomeCompleto)")
    Arguido findByNomeCompletoIgnoreCase(@Param("nomeCompleto") String nomeCompleto);
}
