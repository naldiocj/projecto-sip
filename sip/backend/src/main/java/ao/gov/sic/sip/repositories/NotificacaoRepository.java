package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Notificacao> findByUserIdAndLidaFalseOrderByCreatedAtDesc(Long userId);
}
