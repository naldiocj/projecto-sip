package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoApreensaoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoApreensaoService {
    Response<AutoApreensaoDTO> getById(Long id);

    Response<?> updateById(AutoApreensaoDTO dto, Long id);

    Response<?> create(AutoApreensaoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoApreensaoDTO>> getAll();
}
