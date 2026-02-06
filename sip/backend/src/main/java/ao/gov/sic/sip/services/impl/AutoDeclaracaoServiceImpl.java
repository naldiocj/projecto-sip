package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoDeclaracaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoDeclaracao;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoDeclaracaoMapper;
import ao.gov.sic.sip.repositories.AutoDeclaracaoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoDeclaracaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoDeclaracaoServiceImpl implements AutoDeclaracaoService {
    private final AutoDeclaracaoRepository autoDeclaracaoRepository;
    private final AutoDeclaracaoMapper autoDeclaracaoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoDeclaracaoDTO> getById(Long id) {
        AutoDeclaracao autoDeclaracao = autoDeclaracaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de declaração não encontrado"));

        AutoDeclaracaoDTO autoDeclaracaoDTO = autoDeclaracaoMapper.autoDeclaracaoToAutoDeclaracaoDTO(autoDeclaracao);

        return Response.<AutoDeclaracaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de declaração encontrado com sucesso")
                .data(autoDeclaracaoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoDeclaracaoDTO dto) {
        AutoDeclaracao autoDeclaracao = autoDeclaracaoMapper.autoDeclaracaoDTOToAutoDeclaracao(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoDeclaracao.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoDeclaracao.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoDeclaracao.setUser(user);
        }

        autoDeclaracaoRepository.save(autoDeclaracao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de declaração criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoDeclaracaoRepository.existsById(id)) {
            throw new NotFoundException("Auto de declaração não encontrado");
        }
        autoDeclaracaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de declaração eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoDeclaracaoDTO dto, Long id) {
        autoDeclaracaoRepository.findById(id).ifPresentOrElse(autoDeclaracao -> {
            if (dto.getNumeroFolha() != null) {
                autoDeclaracao.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoDeclaracao.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                autoDeclaracao.setDescricao(dto.getDescricao());
            }
            if (dto.getTipoDeclaracao() != null) {
                autoDeclaracao.setTipoDeclaracao(dto.getTipoDeclaracao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoDeclaracao.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoDeclaracao.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoDeclaracao.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoDeclaracao.setUser(user);
            }
            autoDeclaracaoRepository.save(autoDeclaracao);
        }, () -> {
            throw new NotFoundException("Auto de declaração não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de declaração actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoDeclaracaoDTO>> getAll() {
        List<AutoDeclaracaoDTO> autosDeclaracoes = autoDeclaracaoRepository.findAll()
                .stream().map(autoDeclaracaoMapper::autoDeclaracaoToAutoDeclaracaoDTO)
                .toList();

        return Response.<List<AutoDeclaracaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosDeclaracoes.isEmpty() ? "Nenhum auto de declaração encontrado" : "Autos de declarações encontrados com sucesso")
                .data(autosDeclaracoes)
                .build();
    }
}
