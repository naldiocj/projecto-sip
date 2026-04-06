package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ParticipanteDTO;
import ao.gov.sic.sip.dtos.ParticipanteResDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface ParticipanteService {
    Response<ParticipanteDTO> getById(Long id);

    Response<?> updateById(ParticipanteDTO dto, Long id);

    Response<?> create(ParticipanteDTO dto);

    Response<?> deleteById(Long id);

    Response<List<ParticipanteResDTO>> getAll(Long processoId);
    Response<List<ParticipanteResDTO>> getAllByProcessoNumero(String numero);

}
