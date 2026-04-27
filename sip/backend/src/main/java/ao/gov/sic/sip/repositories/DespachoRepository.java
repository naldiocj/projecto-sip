package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Long> {
    List<Despacho> findByProcessoId(Long processoId);
}
