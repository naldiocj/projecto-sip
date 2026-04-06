package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Diligencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiligenciaRepository extends JpaRepository<Diligencia, Long> {
    boolean existsByOrdem(Integer ordem);

    @Query("SELECT MAX(d.ordem) FROM Diligencia d")
    Integer findMaxOrdem();

    @Modifying
    @Query("UPDATE Diligencia d SET d.ordem = d.ordem + 1 WHERE d.ordem >= :ordem")
    void incrementOrdemFrom(@Param("ordem") Integer ordem);

    @Query("SELECT d FROM Diligencia d WHERE d.processo.id = :processoId ORDER BY d.ordem ASC")
    List<Diligencia> findAllByProcessoId(@Param("processoId") Long processoId);

    @Query("SELECT d FROM Diligencia d WHERE d.processo.numero = :numero ORDER BY d.ordem ASC")
    List<Diligencia> findAllByProcessoNumero(@Param("numero") String numero);
}
