package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ParticipanteAutoApreensaoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface ParticipanteAutoApreensaoService {
    Response<ParticipanteAutoApreensaoDTO> getById(Long id);

    Response<?> updateById(ParticipanteAutoApreensaoDTO dto, Long id);

    Response<?> create(ParticipanteAutoApreensaoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<ParticipanteAutoApreensaoDTO>> getAll();
}
