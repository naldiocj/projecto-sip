package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.QueixosoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.enums.TipoParticipante;
import ao.gov.sic.sip.exceptions.BadRequestException;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.QueixosoMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.QueixosoService;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueixosoServiceImpl implements QueixosoService {
    private final QueixosoRepository queixosoRepository;
    private final QueixosoMapper queixosoMapper;
    private final EnderecoRepository enderecoRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ParticipanteRepository participanteRepository;
    private final ProcessoRepository processoRepository;

    @Override
    public Response<QueixosoDTO> getById(Long id) {
        Queixoso queixoso = queixosoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Queixoso não encontrado"));

        QueixosoDTO queixosoDTO = queixosoMapper.queixosoToQueixosoDTO(queixoso);

        return Response.<QueixosoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Queixoso encontrado com sucesso")
                .data(queixosoDTO)
                .build();
    }

    @Override
    public Response<?> create(QueixosoDTO dto) {

        Processo processo = processoRepository.findFirstByNumero(dto.getProcessoNumero());

        if (processo == null) {
            throw new NotFoundException("Processo não encontrado");
        }

        Queixoso queixosoFounded = queixosoRepository.findByNomeCompletoIgnoreCase(dto.getNomeCompleto()); // TODO

        AtomicReference<Queixoso> queixosoAtomicReference = new AtomicReference<>(queixosoMapper.queixosoDTOToQueixoso(dto));

        if (queixosoFounded != null) {
            Queixoso updatedQueixoso = updateQueixoso(queixosoFounded.getId(), dto);

            queixosoRepository.save(updatedQueixoso);

            Participante participanteFounded = participanteRepository.findFirstByQueixoso(updatedQueixoso);

            if (participanteFounded == null) {
                participanteRepository.save(Participante.builder()
                        .advogado(null)
                        .arguido(null)
                        .queixoso(updatedQueixoso)
                        .testemunha(null)
                        .processo(processo)
                        .tipoParticipante(TipoParticipante.QUEIXOSO)
                        .build());
            } else {
                throw new BadRequestException("O queixoso já existe");
            }

        } else {
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                queixosoAtomicReference.get().setEndereco(endereco);
            }

            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                queixosoAtomicReference.get().setUser(user);
            } else {
                User user = userService.currentUser();
                queixosoAtomicReference.get().setUser(user);
            }

            Queixoso savedQueixoso = queixosoRepository.save(queixosoAtomicReference.get());

            Participante participanteFounded = participanteRepository.findFirstByQueixoso(savedQueixoso);

            if (participanteFounded == null) {
                participanteRepository.save(Participante.builder()
                        .advogado(null)
                        .arguido(null)
                        .queixoso(savedQueixoso)
                        .testemunha(null)
                        .processo(processo)
                        .tipoParticipante(TipoParticipante.QUEIXOSO)
                        .build());
            }
        }

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Queixoso criado com sucesso")
                .build();
    }

    private Queixoso updateQueixoso(Long queixosoFounded, QueixosoDTO dto) {
       Queixoso queixoso = queixosoRepository.findById(queixosoFounded)
               .orElseThrow(()-> new NotFoundException("Queixoso encontrado"));

        if (StringUtils.hasText(dto.getNomeCompleto())) {
            queixoso.setNomeCompleto(dto.getNomeCompleto());
        }
        if (StringUtils.hasText(dto.getNomePai())) {
            queixoso.setNomePai(dto.getNomePai());
        }
        if (StringUtils.hasText(dto.getNomeMae())) {
            queixoso.setNomeMae(dto.getNomeMae());
        }
        if (dto.getEstadoCivil() != null) {
            queixoso.setEstadoCivil(dto.getEstadoCivil());
        }
        if (dto.getIdade() != null) {
            queixoso.setIdade(dto.getIdade());
        }
        if (dto.getDataNascimento() != null) {
            queixoso.setDataNascimento(dto.getDataNascimento());
        }
        if (StringUtils.hasText(dto.getNaturalidade())) {
            queixoso.setNaturalidade(dto.getNaturalidade());
        }
        if (StringUtils.hasText(dto.getProfissao())) {
            queixoso.setProfissao(dto.getProfissao());
        }
        if (StringUtils.hasText(dto.getNumeroBi())) {
            queixoso.setNumeroBi(dto.getNumeroBi());
        }
        if (StringUtils.hasText(dto.getDataEmissaoBi())) {
            queixoso.setDataEmissaoBi(dto.getDataEmissaoBi());
        }
        if (StringUtils.hasText(dto.getEmail())) {
            queixoso.setEmail(dto.getEmail());
        }
        if (StringUtils.hasText(dto.getTelefone())) {
            queixoso.setTelefone(dto.getTelefone());
        }
        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            queixoso.setEndereco(endereco);
        }
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            queixoso.setUser(user);
        }

        return  queixoso;
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!queixosoRepository.existsById(id)) {
            throw new NotFoundException("Queixoso não encontrado");
        }
        queixosoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Queixoso eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(QueixosoDTO dto, Long id) {
        updateQueixoso(id, dto);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Queixoso actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<QueixosoDTO>> getAll() {
        List<QueixosoDTO> queixosos = queixosoRepository.findAll()
                .stream().map(queixosoMapper::queixosoToQueixosoDTO)
                .toList();

        return Response.<List<QueixosoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(queixosos.isEmpty() ? "Nenhum queixoso encontrado" : "Queixosos encontrados com sucesso")
                .data(queixosos)
                .build();
    }
}
