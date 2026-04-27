package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByUserId(Long userId);

    // Busca o maior número de página para um processo específico
    @Query("SELECT MAX(e.pagina) FROM Documento e WHERE e.numeroProcesso = :numero")
    Integer findMaxPaginaByNumeroProcesso(@Param("numero") String numeroProcesso);
}