package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Advogado;
import ao.gov.sic.sip.entities.Resource;
import ao.gov.sic.sip.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Role findByRole_Id(Long roleId);

    List<Resource> findAllByRole_Id(Long id);
}
