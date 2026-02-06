package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoInterrogatorioEmAditamentoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoInterrogatorioEmAditamento;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoInterrogatorioEmAditamentoMapper;
import ao.gov.sic.sip.repositories.AutoInterrogatorioEmAditamentoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoInterrogatorioEmAditamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoInterrogatorioEmAditamentoServiceImpl implements AutoInterrogatorioEmAditamentoService {
    private final AutoInterrogatorioEmAditamentoRepository autoInterrogatorioEmAditamentoRepository;
    private final AutoInterrogatorioEmAditamentoMapper autoInterrogatorioEmAditamentoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoInterrogatorioEmAditamentoDTO> getById(Long id) {
        AutoInterrogatorioEmAditamento autoInterrogatorioEmAditamento = autoInterrogatorioEmAditamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de interrogatório em aditamento não encontrado"));

        AutoInterrogatorioEmAditamentoDTO autoInterrogatorioEmAditamentoDTO = autoInterrogatorioEmAditamentoMapper.autoInterrogatorioEmAditamentoToAutoInterrogatorioEmAditamentoDTO(autoInterrogatorioEmAditamento);

        return Response.<AutoInterrogatorioEmAditamentoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de interrogatório em aditamento encontrado com sucesso")
                .data(autoInterrogatorioEmAditamentoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoInterrogatorioEmAditamentoDTO dto) {
        AutoInterrogatorioEmAditamento autoInterrogatorioEmAditamento = autoInterrogatorioEmAditamentoMapper.autoInterrogatorioEmAditamentoDTOToAutoInterrogatorioEmAditamento(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoInterrogatorioEmAditamento.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoInterrogatorioEmAditamento.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoInterrogatorioEmAditamento.setUser(user);
        }

        autoInterrogatorioEmAditamentoRepository.save(autoInterrogatorioEmAditamento);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de interrogatório em aditamento criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoInterrogatorioEmAditamentoRepository.existsById(id)) {
            throw new NotFoundException("Auto de interrogatório em aditamento não encontrado");
        }
        autoInterrogatorioEmAditamentoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de interrogatório em aditamento eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoInterrogatorioEmAditamentoDTO dto, Long id) {
        autoInterrogatorioEmAditamentoRepository.findById(id).ifPresentOrElse(autoInterrogatorioEmAditamento -> {
            if (dto.getNumeroFolha() != null) {
                autoInterrogatorioEmAditamento.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoInterrogatorioEmAditamento.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDefensorOficioso())) {
                autoInterrogatorioEmAditamento.setDefensorOficioso(dto.getDefensorOficioso());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoInterrogatorioEmAditamento.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoInterrogatorioEmAditamento.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoInterrogatorioEmAditamento.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoInterrogatorioEmAditamento.setUser(user);
            }
            autoInterrogatorioEmAditamentoRepository.save(autoInterrogatorioEmAditamento);
        }, () -> {
            throw new NotFoundException("Auto de interrogatório em aditamento não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de interrogatório em aditamento actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoInterrogatorioEmAditamentoDTO>> getAll() {
        List<AutoInterrogatorioEmAditamentoDTO> autosInterrogatoriosEmAditamentos = autoInterrogatorioEmAditamentoRepository.findAll()
                .stream().map(autoInterrogatorioEmAditamentoMapper::autoInterrogatorioEmAditamentoToAutoInterrogatorioEmAditamentoDTO)
                .toList();

        return Response.<List<AutoInterrogatorioEmAditamentoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosInterrogatoriosEmAditamentos.isEmpty() ? "Nenhum auto de interrogatório em aditamento encontrado" : "Autos de interrogatórios em aditamentos encontrados com sucesso")
                .data(autosInterrogatoriosEmAditamentos)
                .build();
    }
}
