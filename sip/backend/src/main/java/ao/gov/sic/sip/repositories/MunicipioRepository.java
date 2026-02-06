package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    @Query("SELECT m FROM Municipio m WHERE LOWER(m.nome) = LOWER(:nome)")
    Municipio findByNomeIgnoreCase(@Param("nome") String nome);
}
