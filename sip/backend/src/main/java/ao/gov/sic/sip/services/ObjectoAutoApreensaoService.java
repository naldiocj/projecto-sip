package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ObjectoAutoApreensaoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface ObjectoAutoApreensaoService {
    Response<ObjectoAutoApreensaoDTO> getById(Long id);

    Response<?> updateById(ObjectoAutoApreensaoDTO dto, Long id);

    Response<?> create(ObjectoAutoApreensaoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<ObjectoAutoApreensaoDTO>> getAll();
}
