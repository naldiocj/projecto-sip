package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.ObjectoAutoApreensao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectoAutoApreensaoRepository extends JpaRepository<ObjectoAutoApreensao, Long> {
    ObjectoAutoApreensao findAllByNome(String nome);
}
