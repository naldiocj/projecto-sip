package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.ParticipanteAutoApreensaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.ParticipanteAutoApreensao;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ParticipanteAutoApreensaoMapper;
import ao.gov.sic.sip.repositories.ParticipanteAutoApreensaoRepository;
import ao.gov.sic.sip.services.ParticipanteAutoApreensaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipanteAutoApreensaoServiceImpl implements ParticipanteAutoApreensaoService {
    private final ParticipanteAutoApreensaoRepository participanteAutoApreensaoRepository;
    private final ParticipanteAutoApreensaoMapper participanteAutoApreensaoMapper;

    @Override
    public Response<ParticipanteAutoApreensaoDTO> getById(Long id) {
        ParticipanteAutoApreensao participanteAutoApreensao = participanteAutoApreensaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Participante de auto de apreensão não encontrado"));

        ParticipanteAutoApreensaoDTO participanteAutoApreensaoDTO = participanteAutoApreensaoMapper.participanteAutoApreensaoToParticipanteAutoApreensaoDTO(participanteAutoApreensao);

        return Response.<ParticipanteAutoApreensaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Participante de auto de apreensão encontrado com sucesso")
                .data(participanteAutoApreensaoDTO)
                .build();
    }

    @Override
    public Response<?> create(ParticipanteAutoApreensaoDTO dto) {
        ParticipanteAutoApreensao founded = participanteAutoApreensaoRepository.findAllByNome(dto.getNome());
        if (founded != null) {
            throw new RuntimeException("Participante de auto de apreensão já existe");
        }

        ParticipanteAutoApreensao participanteAutoApreensao = participanteAutoApreensaoMapper.participanteAutoApreensaoDTOToParticipanteAutoApreensao(dto);
        participanteAutoApreensaoRepository.save(participanteAutoApreensao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Participante de auto de apreensão criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!participanteAutoApreensaoRepository.existsById(id)) {
            throw new NotFoundException("Participante de auto de apreensão não encontrado");
        }
        participanteAutoApreensaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Participante de auto de apreensão eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(ParticipanteAutoApreensaoDTO dto, Long id) {
        participanteAutoApreensaoRepository.findById(id).ifPresentOrElse(participanteAutoApreensao -> {
            if (StringUtils.hasText(dto.getNome())) {
                participanteAutoApreensao.setNome(dto.getNome());
            }
            participanteAutoApreensaoRepository.save(participanteAutoApreensao);
        }, () -> {
            throw new NotFoundException("Participante de auto de apreensão não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Participante de auto de apreensão actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<ParticipanteAutoApreensaoDTO>> getAll() {
        List<ParticipanteAutoApreensaoDTO> participantesAutosApreensoes = participanteAutoApreensaoRepository.findAll()
                .stream().map(participanteAutoApreensaoMapper::participanteAutoApreensaoToParticipanteAutoApreensaoDTO)
                .toList();

        return Response.<List<ParticipanteAutoApreensaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(participantesAutosApreensoes.isEmpty() ? "Nenhum participante de auto de apreensão encontrado" : "Participantes de autos de apreensões encontrados com sucesso")
                .data(participantesAutosApreensoes)
                .build();
    }
}
