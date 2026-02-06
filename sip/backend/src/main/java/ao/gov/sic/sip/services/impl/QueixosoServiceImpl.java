package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.QueixosoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Queixoso;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.QueixosoMapper;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.QueixosoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.QueixosoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueixosoServiceImpl implements QueixosoService {
    private final QueixosoRepository queixosoRepository;
    private final QueixosoMapper queixosoMapper;
    private final EnderecoRepository enderecoRepository;
    private final UserRepository userRepository;

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
        Queixoso founded = queixosoRepository.findByNomeCompletoIgnoreCase(dto.getNomeCompleto());
        if (founded != null) {
            throw new RuntimeException("Queixoso já existe");
        }

        Queixoso queixoso = queixosoMapper.queixosoDTOToQueixoso(dto);

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

        queixosoRepository.save(queixoso);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Queixoso criado com sucesso")
                .build();
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
        queixosoRepository.findById(id).ifPresentOrElse(queixoso -> {
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
            queixosoRepository.save(queixoso);
        }, () -> {
            throw new NotFoundException("Queixoso não encontrado");
        });

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
