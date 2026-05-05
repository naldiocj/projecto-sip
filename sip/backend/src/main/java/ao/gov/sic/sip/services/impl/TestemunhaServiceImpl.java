package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.TestemunhaDTO;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.enums.TipoParticipante;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.TestemunhaMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.TestemunhaService;
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
public class TestemunhaServiceImpl implements TestemunhaService {
    private final TestemunhaRepository testemunhaRepository;
    private final TestemunhaMapper testemunhaMapper;
    private final EnderecoRepository enderecoRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProcessoRepository processoRepository;
    private final ParticipanteRepository participanteRepository;

    @Override
    public Response<TestemunhaDTO> getById(Long id) {
        Testemunha testemunha = testemunhaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Testemunha não encontrada"));

        TestemunhaDTO testemunhaDTO = testemunhaMapper.testemunhaToTestemunhaDTO(testemunha);

        return Response.<TestemunhaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Testemunha encontrada com sucesso")
                .data(testemunhaDTO)
                .build();
    }

    @Override
    public Response<?> create(TestemunhaDTO dto) {
        Processo processo = processoRepository.findFirstByNumero(dto.getProcessoNumero());

        if (processo == null) {
            throw new NotFoundException("Processo não encontrado");
        }

        testemunhaRepository.findAll().stream()
                .filter(t -> t.getNomeCompleto().equals(dto.getNomeCompleto()) && t.getNumeroBi().equals(dto.getNumeroBi()))
                .findFirst().ifPresent(t -> {
                    throw new RuntimeException("Testemunha já existe");
                });

        Testemunha testemunha = testemunhaMapper.testemunhaDTOToTestemunha(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            testemunha.setEndereco(endereco);
        } else {
            testemunha.setEndereco(null);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            testemunha.setUser(user);
        } else {
            User user = userService.currentUser();
            testemunha.setUser(user);
        }

        Testemunha savedTestemunha = testemunhaRepository.save(testemunha);



        participanteRepository.save(Participante.builder()
                .advogado(null)
                .arguido(null)
                .queixoso(null)
                .testemunha(savedTestemunha)
                .processo(processo)
                .tipoParticipante(TipoParticipante.TESTEMUNHA)
                .build());

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Testemunha criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!testemunhaRepository.existsById(id)) {
            throw new NotFoundException("Testemunha não encontrada");
        }
        testemunhaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Testemunha eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(TestemunhaDTO dto, Long id) {
        testemunhaRepository.findById(id).ifPresentOrElse(testemunha -> {
            if (StringUtils.hasText(dto.getNomeCompleto())) {
                testemunha.setNomeCompleto(dto.getNomeCompleto());
            }
            if (StringUtils.hasText(dto.getNomePai())) {
                testemunha.setNomePai(dto.getNomePai());
            }
            if (StringUtils.hasText(dto.getNomeMae())) {
                testemunha.setNomeMae(dto.getNomeMae());
            }
            if (dto.getEstadoCivil() != null) {
                testemunha.setEstadoCivil(dto.getEstadoCivil());
            }
            if (dto.getIdade() != null) {
                testemunha.setIdade(dto.getIdade());
            }
            if (dto.getDataNascimento() != null) {
                testemunha.setDataNascimento(dto.getDataNascimento());
            }
            if (StringUtils.hasText(dto.getNaturalidade())) {
                testemunha.setNaturalidade(dto.getNaturalidade());
            }
            if (StringUtils.hasText(dto.getProfissao())) {
                testemunha.setProfissao(dto.getProfissao());
            }
            if (StringUtils.hasText(dto.getNumeroBi())) {
                testemunha.setNumeroBi(dto.getNumeroBi());
            }
            if (StringUtils.hasText(dto.getDataEmissaoBi())) {
                testemunha.setDataEmissaoBi(dto.getDataEmissaoBi());
            }
            if (StringUtils.hasText(dto.getEmail())) {
                testemunha.setEmail(dto.getEmail());
            }
            if (StringUtils.hasText(dto.getTelefone())) {
                testemunha.setTelefone(dto.getTelefone());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                testemunha.setEndereco(endereco);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                testemunha.setUser(user);
            }
            testemunhaRepository.save(testemunha);
        }, () -> {
            throw new NotFoundException("Testemunha não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Testemunha actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<TestemunhaDTO>> getAll() {
        List<TestemunhaDTO> testemunhas = testemunhaRepository.findAll()
                .stream().map(testemunhaMapper::testemunhaToTestemunhaDTO)
                .toList();

        return Response.<List<TestemunhaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(testemunhas.isEmpty() ? "Nenhuma testemunha encontrada" : "Testemunhas encontradas com sucesso")
                .data(testemunhas)
                .build();
    }
}
