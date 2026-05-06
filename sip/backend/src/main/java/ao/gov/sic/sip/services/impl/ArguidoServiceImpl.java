package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.ArguidoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.enums.TipoParticipante;
import ao.gov.sic.sip.exceptions.BadRequestException;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ArguidoMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.ArguidoAccessService;
import ao.gov.sic.sip.services.ArguidoService;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArguidoServiceImpl implements ArguidoService {
    private final ArguidoRepository arguidoRepository;
    private final ArguidoMapper arguidoMapper;
    private final EnderecoRepository enderecoRepository;
    private final UserRepository userRepository;
    private final ProcessoRepository processoRepository;
    private final ParticipanteRepository participanteRepository;
    private final UserService userService;
    private final ArguidoAccessService arguidoAccessService;

    @Override
    public Response<ArguidoDTO> getById(Long id) {
        Arguido arguido = arguidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Arguido não encontrado"));

        ArguidoDTO arguidoDTO = arguidoMapper.arguidoToArguidoDTO(arguido);

        return Response.<ArguidoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Arguido encontrado com sucesso")
                .data(arguidoDTO)
                .build();
    }

    @Override
    public Response<?> create(ArguidoDTO dto) {
        Processo processo = processoRepository.findFirstByNumero(dto.getProcessoNumero());

        if (processo == null) {
            throw new NotFoundException("Processo não encontrado");
        }

        Arguido arguidoFounded = arguidoRepository.findByNomeCompletoIgnoreCase(dto.getNomeCompleto()); // TODO

        AtomicReference<Arguido> arguidoAtomicReference = new AtomicReference<>(arguidoMapper.arguidoDTOToArguido(dto));

        if (arguidoFounded != null) {
            Arguido updatedArguido = updateQueixoso(arguidoFounded.getId(), dto);

            arguidoRepository.save(updatedArguido);

            Participante participanteFounded = participanteRepository.findFirstByArguido(updatedArguido);

            if (participanteFounded == null) {
                participanteRepository.save(Participante.builder()
                        .advogado(null)
                        .arguido(updatedArguido)
                        .queixoso(null)
                        .testemunha(null)
                        .processo(processo)
                        .tipoParticipante(TipoParticipante.ARGUIDO)
                        .build());
            } else {
                throw new BadRequestException("O queixoso já existe");
            }

        } else {
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                arguidoAtomicReference.get().setEndereco(endereco);
            } else {
                arguidoAtomicReference.get().setEndereco(null);
            }

            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                arguidoAtomicReference.get().setUser(user);
            } else {
                User user = userService.currentUser();
                arguidoAtomicReference.get().setUser(user);
            }

            Arguido savedArguido = arguidoRepository.save(arguidoAtomicReference.get());

            Participante participanteFounded = participanteRepository.findFirstByArguido(savedArguido);

            if (participanteFounded == null) {
                participanteRepository.save(Participante.builder()
                        .advogado(null)
                        .arguido(savedArguido)
                        .queixoso(null)
                        .testemunha(null)
                        .processo(processo)
                        .tipoParticipante(TipoParticipante.ARGUIDO)
                        .build());
            }
        }


        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Arguido criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!arguidoRepository.existsById(id)) {
            throw new NotFoundException("Arguido não encontrado");
        }
        arguidoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Arguido eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(ArguidoDTO dto, Long id) {
        arguidoRepository.findById(id).ifPresentOrElse(arguido -> {
            if (StringUtils.hasText(dto.getNomeCompleto())) {
                arguido.setNomeCompleto(dto.getNomeCompleto());
            }
            if (StringUtils.hasText(dto.getNomePai())) {
                arguido.setNomePai(dto.getNomePai());
            }
            if (StringUtils.hasText(dto.getNomeMae())) {
                arguido.setNomeMae(dto.getNomeMae());
            }
            if (dto.getEstadoCivil() != null) {
                arguido.setEstadoCivil(dto.getEstadoCivil());
            }
            if (dto.getIdade() != null) {
                arguido.setIdade(dto.getIdade());
            }
            if (dto.getDataNascimento() != null) {
                arguido.setDataNascimento(dto.getDataNascimento());
            }
            if (StringUtils.hasText(dto.getNaturalidade())) {
                arguido.setNaturalidade(dto.getNaturalidade());
            }
            if (StringUtils.hasText(dto.getProfissao())) {
                arguido.setProfissao(dto.getProfissao());
            }
            if (StringUtils.hasText(dto.getNumeroBi())) {
                arguido.setNumeroBi(dto.getNumeroBi());
            }
            if (StringUtils.hasText(dto.getDataEmissaoBi())) {
                arguido.setDataEmissaoBi(dto.getDataEmissaoBi());
            }
            if (StringUtils.hasText(dto.getEmail())) {
                arguido.setEmail(dto.getEmail());
            }
            if (StringUtils.hasText(dto.getTelefone())) {
                arguido.setTelefone(dto.getTelefone());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                arguido.setEndereco(endereco);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                arguido.setUser(user);
            }
            arguidoRepository.save(arguido);
        }, () -> {
            throw new NotFoundException("Arguido não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Arguido actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<ArguidoDTO>> getAll() {
        List<ArguidoDTO> arguidos = arguidoAccessService.getFilteredArguidos();

        return Response.<List<ArguidoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(arguidos.isEmpty() ? "Nenhum arguido encontrado" : "Arguidos encontrados com sucesso")
                .data(arguidos)
                .build();
    }

    private Arguido updateQueixoso(Long id, ArguidoDTO dto) {
        Arguido arguido = arguidoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Arguido encontrado"));

        if (StringUtils.hasText(dto.getNomeCompleto())) {
            arguido.setNomeCompleto(dto.getNomeCompleto());
        }
        if (StringUtils.hasText(dto.getNomePai())) {
            arguido.setNomePai(dto.getNomePai());
        }
        if (StringUtils.hasText(dto.getNomeMae())) {
            arguido.setNomeMae(dto.getNomeMae());
        }
        if (dto.getEstadoCivil() != null) {
            arguido.setEstadoCivil(dto.getEstadoCivil());
        }
        if (dto.getIdade() != null) {
            arguido.setIdade(dto.getIdade());
        }
        if (dto.getDataNascimento() != null) {
            arguido.setDataNascimento(dto.getDataNascimento());
        }
        if (StringUtils.hasText(dto.getNaturalidade())) {
            arguido.setNaturalidade(dto.getNaturalidade());
        }
        if (StringUtils.hasText(dto.getProfissao())) {
            arguido.setProfissao(dto.getProfissao());
        }
        if (StringUtils.hasText(dto.getNumeroBi())) {
            arguido.setNumeroBi(dto.getNumeroBi());
        }
        if (StringUtils.hasText(dto.getDataEmissaoBi())) {
            arguido.setDataEmissaoBi(dto.getDataEmissaoBi());
        }
        if (StringUtils.hasText(dto.getEmail())) {
            arguido.setEmail(dto.getEmail());
        }
        if (StringUtils.hasText(dto.getTelefone())) {
            arguido.setTelefone(dto.getTelefone());
        }
        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            arguido.setEndereco(endereco);
        }
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            arguido.setUser(user);
        }

        return  arguido;
    }
}
