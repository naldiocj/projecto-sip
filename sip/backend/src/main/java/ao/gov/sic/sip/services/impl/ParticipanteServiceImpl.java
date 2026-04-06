package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.ParticipanteDTO;
import ao.gov.sic.sip.dtos.ParticipanteResDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ParticipanteMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.ParticipanteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipanteServiceImpl implements ParticipanteService {
    private final ParticipanteRepository participanteRepository;
    private final ParticipanteMapper participanteMapper;
    private final QueixosoRepository queixosoRepository;
    private final AdvogadoRepository advogadoRepository;
    private final ArguidoRepository arguidoRepository;
    private final TestemunhaRepository testemunhaRepository;
    private final ProcessoRepository processoRepository;

    @Override
    public Response<ParticipanteDTO> getById(Long id) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Participante não encontrado"));

        ParticipanteDTO participanteDTO = participanteMapper.participanteToParticipanteDTO(participante);

        return Response.<ParticipanteDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Participante encontrado com sucesso")
                .data(participanteDTO)
                .build();
    }

    @Override
    public Response<?> create(ParticipanteDTO dto) {
        Participante participante = participanteMapper.participanteDTOToParticipante(dto);

        if (dto.getQueixosoId() != null) {
            Queixoso queixoso = queixosoRepository.findById(dto.getQueixosoId())
                    .orElseThrow(() -> new NotFoundException("Queixoso não encontrado"));
            participante.setQueixoso(queixoso);
        } else {
            participante.setQueixoso(null);
        }

        if (dto.getAdvogadoId() != null) {
            Advogado advogado = advogadoRepository.findById(dto.getAdvogadoId())
                    .orElseThrow(() -> new NotFoundException("Advogado não encontrado"));
            participante.setAdvogado(advogado);
        } else {
            participante.setAdvogado(null);
        }

        if (dto.getArguidoId() != null) {
            Arguido arguido = arguidoRepository.findById(dto.getArguidoId())
                    .orElseThrow(() -> new NotFoundException("Arguido não encontrado"));
            participante.setArguido(arguido);
        } else {
            participante.setArguido(null);
        }

        if (dto.getTestemunhaId() != null) {
            Testemunha testemunha = testemunhaRepository.findById(dto.getTestemunhaId())
                    .orElseThrow(() -> new NotFoundException("Testemunha não encontrada"));
            participante.setTestemunha(testemunha);
        } else {
            participante.setTestemunha(null);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            participante.setProcesso(processo);
        }

        participanteRepository.save(participante);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Participante criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!participanteRepository.existsById(id)) {
            throw new NotFoundException("Participante não encontrado");
        }
        participanteRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Participante eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(ParticipanteDTO dto, Long id) {
        participanteRepository.findById(id).ifPresentOrElse(participante -> {
            if (dto.getTipoParticipante() != null) {
                participante.setTipoParticipante(dto.getTipoParticipante());
            }
            if (dto.getQueixosoId() != null) {
                Queixoso queixoso = queixosoRepository.findById(dto.getQueixosoId())
                        .orElseThrow(() -> new NotFoundException("Queixoso não encontrado"));
                participante.setQueixoso(queixoso);
            }
            if (dto.getAdvogadoId() != null) {
                Advogado advogado = advogadoRepository.findById(dto.getAdvogadoId())
                        .orElseThrow(() -> new NotFoundException("Advogado não encontrado"));
                participante.setAdvogado(advogado);
            }
            if (dto.getArguidoId() != null) {
                Arguido arguido = arguidoRepository.findById(dto.getArguidoId())
                        .orElseThrow(() -> new NotFoundException("Arguido não encontrado"));
                participante.setArguido(arguido);
            }
            if (dto.getTestemunhaId() != null) {
                Testemunha testemunha = testemunhaRepository.findById(dto.getTestemunhaId())
                        .orElseThrow(() -> new NotFoundException("Testemunha não encontrada"));
                participante.setTestemunha(testemunha);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                participante.setProcesso(processo);
            }
            participanteRepository.save(participante);
        }, () -> {
            throw new NotFoundException("Participante não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Participante actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<ParticipanteResDTO>> getAll(Long processoId) {
        List<Participante> participantes;
        if (processoId != null) {
            participantes = participanteRepository.findAllByProcessoId(processoId);
        } else {
            participantes = participanteRepository.findAll();
        }

        List<ParticipanteResDTO> participanteDTOS = participantes.stream()
                .map(participanteMapper::participanteToParticipanteResDTO)
                .toList();

        return Response.<List<ParticipanteResDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(participanteDTOS.isEmpty() ? "Nenhum participante encontrado" : "Participantes encontrados com sucesso")
                .data(participanteDTOS)
                .build();
    }

    @Override
    public Response<List<ParticipanteResDTO>> getAllByProcessoNumero(String numero) {
        List<Participante> participantes;
        if (StringUtils.hasText(numero)) {
            participantes = participanteRepository.findAllByProcesso_Numero(numero);
        } else {
            participantes = participanteRepository.findAll();
        }

        List<ParticipanteResDTO> participanteDTOS = participantes.stream()
                .map(participanteMapper::participanteToParticipanteResDTO)
                .toList();

        return Response.<List<ParticipanteResDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(participanteDTOS.isEmpty() ? "Nenhum participante encontrado" : "Participantes encontrados com sucesso")
                .data(participanteDTOS)
                .build();
    }
}
