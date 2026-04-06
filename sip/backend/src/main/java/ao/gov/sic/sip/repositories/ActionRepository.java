package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Action;
import ao.gov.sic.sip.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
}
