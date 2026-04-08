package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoDeclaracaoEmAditamentoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoDeclaracaoEmAditamento;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoDeclaracaoEmAditamentoMapper;
import ao.gov.sic.sip.repositories.AutoDeclaracaoEmAditamentoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoDeclaracaoEmAditamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoDeclaracaoEmAditamentoServiceImpl implements AutoDeclaracaoEmAditamentoService {
    private final AutoDeclaracaoEmAditamentoRepository autoDeclaracaoEmAditamentoRepository;
    private final AutoDeclaracaoEmAditamentoMapper autoDeclaracaoEmAditamentoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoDeclaracaoEmAditamentoDTO> getById(Long id) {
        AutoDeclaracaoEmAditamento autoDeclaracaoEmAditamento = autoDeclaracaoEmAditamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de declaração em aditamento não encontrado"));

        AutoDeclaracaoEmAditamentoDTO autoDeclaracaoEmAditamentoDTO = autoDeclaracaoEmAditamentoMapper.autoDeclaracaoEmAditamentoToAutoDeclaracaoEmAditamentoDTO(autoDeclaracaoEmAditamento);

        return Response.<AutoDeclaracaoEmAditamentoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de declaração em aditamento encontrado com sucesso")
                .data(autoDeclaracaoEmAditamentoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoDeclaracaoEmAditamentoDTO dto) {
        AutoDeclaracaoEmAditamento autoDeclaracaoEmAditamento = autoDeclaracaoEmAditamentoMapper.autoDeclaracaoEmAditamentoDTOToAutoDeclaracaoEmAditamento(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoDeclaracaoEmAditamento.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoDeclaracaoEmAditamento.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoDeclaracaoEmAditamento.setUser(user);
        }

        autoDeclaracaoEmAditamentoRepository.save(autoDeclaracaoEmAditamento);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de declaração em aditamento criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoDeclaracaoEmAditamentoRepository.existsById(id)) {
            throw new NotFoundException("Auto de declaração em aditamento não encontrado");
        }
        autoDeclaracaoEmAditamentoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de declaração em aditamento eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoDeclaracaoEmAditamentoDTO dto, Long id) {
        autoDeclaracaoEmAditamentoRepository.findById(id).ifPresentOrElse(autoDeclaracaoEmAditamento -> {
            if (dto.getNumeroFolha() != null) {
                autoDeclaracaoEmAditamento.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoDeclaracaoEmAditamento.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                autoDeclaracaoEmAditamento.setDescricao(dto.getDescricao());
            }
            if (dto.getTipoDeclaracao() != null) {
                autoDeclaracaoEmAditamento.setTipoDeclaracao(dto.getTipoDeclaracao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoDeclaracaoEmAditamento.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoDeclaracaoEmAditamento.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoDeclaracaoEmAditamento.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoDeclaracaoEmAditamento.setUser(user);
            }
            autoDeclaracaoEmAditamentoRepository.save(autoDeclaracaoEmAditamento);
        }, () -> {
            throw new NotFoundException("Auto de declaração em aditamento não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de declaração em aditamento actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoDeclaracaoEmAditamentoDTO>> getAll() {
        List<AutoDeclaracaoEmAditamentoDTO> autosDeclaracoesEmAditamentos = autoDeclaracaoEmAditamentoRepository.findAll()
                .stream().map(autoDeclaracaoEmAditamentoMapper::autoDeclaracaoEmAditamentoToAutoDeclaracaoEmAditamentoDTO)
                .toList();

        return Response.<List<AutoDeclaracaoEmAditamentoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosDeclaracoesEmAditamentos.isEmpty() ? "Nenhum auto de declaração em aditamento encontrado" : "Autos de declarações em aditamentos encontrados com sucesso")
                .data(autosDeclaracoesEmAditamentos)
                .build();
    }
}
