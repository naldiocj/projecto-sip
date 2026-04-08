package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AdvogadoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Advogado;
import ao.gov.sic.sip.entities.Participante;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.enums.TipoParticipante;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AdvogadoMapper;
import ao.gov.sic.sip.repositories.AdvogadoRepository;
import ao.gov.sic.sip.repositories.ParticipanteRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AdvogadoService;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvogadoServiceImpl implements AdvogadoService {
    private final AdvogadoRepository advogadoRepository;
    private final AdvogadoMapper advogadoMapper;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;
    private final ParticipanteRepository participanteRepository;
    private final UserService userService;

    @Override
    public Response<AdvogadoDTO> getById(Long id) {
        Advogado advogado = advogadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Advogado não encontrado"));

        AdvogadoDTO advogadoDTO = advogadoMapper.advogadoToAdvogadoDTO(advogado);

        return Response.<AdvogadoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Advogado encontrado com sucesso")
                .data(advogadoDTO)
                .build();
    }

    @Override
    public Response<?> create(AdvogadoDTO dto) {
        Processo processoFounded = processoRepository.findFirstByNumero(dto.getProcessoNumero());

        if (processoFounded == null) {
            throw new NotFoundException("Processo não encontrado");
        }

        dto.setProcessoId(processoFounded.getId());

        Advogado founded = advogadoRepository.findByNumeroCedula(dto.getNumeroCedula());
        if (founded != null) {
            throw new RuntimeException("Advogado já existe");
        }

        Advogado advogado = advogadoMapper.advogadoDTOToAdvogado(dto);

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            advogado.setProcesso(processo);
        } else {
            advogado.setProcesso(null);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            advogado.setUser(user);
        } else {
            User user = userService.currentUser();
            advogado.setUser(user);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));

            Advogado savedAdvogado = advogadoRepository.save(advogado);

            Participante participanteFounded = participanteRepository.findFirstByAdvogado(advogado);

            if (participanteFounded == null) {
                participanteRepository.save(Participante.builder()
                        .advogado(savedAdvogado)
                        .arguido(null)
                        .queixoso(null)
                        .testemunha(null)
                        .processo(processo)
                        .tipoParticipante(TipoParticipante.ADVOGADO)
                        .build());
            }
        }

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Advogado criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!advogadoRepository.existsById(id)) {
            throw new NotFoundException("Advogado não encontrado");
        }
        advogadoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Advogado eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AdvogadoDTO dto, Long id) {
        advogadoRepository.findById(id).ifPresentOrElse(advogado -> {
            if (StringUtils.hasText(dto.getNomeCompleto())) {
                advogado.setNomeCompleto(dto.getNomeCompleto());
            }
            if (StringUtils.hasText(dto.getNumeroCedula())) {
                advogado.setNumeroCedula(dto.getNumeroCedula());
            }
            if (StringUtils.hasText(dto.getTelefone())) {
                advogado.setTelefone(dto.getTelefone());
            }
            if (dto.getTipoAdvogado() != null) {
                advogado.setTipoAdvogado(dto.getTipoAdvogado());
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                advogado.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                advogado.setUser(user);
            }
            advogadoRepository.save(advogado);
        }, () -> {
            throw new NotFoundException("Advogado não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Advogado actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AdvogadoDTO>> getAll() {
        List<AdvogadoDTO> advogados = advogadoRepository.findAll()
                .stream().map(advogadoMapper::advogadoToAdvogadoDTO)
                .toList();

        return Response.<List<AdvogadoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(advogados.isEmpty() ? "Nenhum advogado encontrado" : "Advogados encontrados com sucesso")
                .data(advogados)
                .build();
    }
}
