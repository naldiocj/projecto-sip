package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.ArguidoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Arguido;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ArguidoMapper;
import ao.gov.sic.sip.repositories.ArguidoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.ArguidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArguidoServiceImpl implements ArguidoService {
    private final ArguidoRepository arguidoRepository;
    private final ArguidoMapper arguidoMapper;
    private final EnderecoRepository enderecoRepository;
    private final UserRepository userRepository;

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
        Arguido founded = arguidoRepository.findByNomeCompletoIgnoreCase(dto.getNomeCompleto());
        if (founded != null) {
            throw new RuntimeException("Arguido já existe");
        }

        Arguido arguido = arguidoMapper.arguidoDTOToArguido(dto);

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
        List<ArguidoDTO> arguidos = arguidoRepository.findAll()
                .stream().map(arguidoMapper::arguidoToArguidoDTO)
                .toList();

        return Response.<List<ArguidoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(arguidos.isEmpty() ? "Nenhum arguido encontrado" : "Arguidos encontrados com sucesso")
                .data(arguidos)
                .build();
    }
}
