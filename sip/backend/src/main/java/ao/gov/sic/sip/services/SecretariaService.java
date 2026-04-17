package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.SecretariaDTO;
import java.util.List;

public interface SecretariaService {
    SecretariaDTO create(SecretariaDTO dto);
    SecretariaDTO update(Long id, SecretariaDTO dto);
    SecretariaDTO findById(Long id);
    List<SecretariaDTO> findAll();
    void delete(Long id);
}
