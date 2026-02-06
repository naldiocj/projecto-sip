package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoAditamentoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoAditamento;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoAditamentoMapper;
import ao.gov.sic.sip.repositories.AutoAditamentoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoAditamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoAditamentoServiceImpl implements AutoAditamentoService {
    private final AutoAditamentoRepository autoAditamentoRepository;
    private final AutoAditamentoMapper autoAditamentoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoAditamentoDTO> getById(Long id) {
        AutoAditamento autoAditamento = autoAditamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de aditamento não encontrado"));

        AutoAditamentoDTO autoAditamentoDTO = autoAditamentoMapper.autoAditamentoToAutoAditamentoDTO(autoAditamento);

        return Response.<AutoAditamentoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de aditamento encontrado com sucesso")
                .data(autoAditamentoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoAditamentoDTO dto) {
        AutoAditamento autoAditamento = autoAditamentoMapper.autoAditamentoDTOToAutoAditamento(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoAditamento.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoAditamento.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoAditamento.setUser(user);
        }

        autoAditamentoRepository.save(autoAditamento);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de aditamento criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoAditamentoRepository.existsById(id)) {
            throw new NotFoundException("Auto de aditamento não encontrado");
        }
        autoAditamentoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de aditamento eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoAditamentoDTO dto, Long id) {
        autoAditamentoRepository.findById(id).ifPresentOrElse(autoAditamento -> {
            if (dto.getNumeroFolha() != null) {
                autoAditamento.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoAditamento.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                autoAditamento.setDescricao(dto.getDescricao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoAditamento.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoAditamento.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoAditamento.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoAditamento.setUser(user);
            }
            autoAditamentoRepository.save(autoAditamento);
        }, () -> {
            throw new NotFoundException("Auto de aditamento não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de aditamento actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoAditamentoDTO>> getAll() {
        List<AutoAditamentoDTO> autosAditamentos = autoAditamentoRepository.findAll()
                .stream().map(autoAditamentoMapper::autoAditamentoToAutoAditamentoDTO)
                .toList();

        return Response.<List<AutoAditamentoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosAditamentos.isEmpty() ? "Nenhum auto de aditamento encontrado" : "Autos de aditamentos encontrados com sucesso")
                .data(autosAditamentos)
                .build();
    }
}
