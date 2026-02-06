package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoInterrogatorioArguidoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoInterrogatorioArguido;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoInterrogatorioArguidoMapper;
import ao.gov.sic.sip.repositories.AutoInterrogatorioArguidoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoInterrogatorioArguidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoInterrogatorioArguidoServiceImpl implements AutoInterrogatorioArguidoService {
    private final AutoInterrogatorioArguidoRepository autoInterrogatorioArguidoRepository;
    private final AutoInterrogatorioArguidoMapper autoInterrogatorioArguidoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoInterrogatorioArguidoDTO> getById(Long id) {
        AutoInterrogatorioArguido autoInterrogatorioArguido = autoInterrogatorioArguidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de interrogatório de arguido não encontrado"));

        AutoInterrogatorioArguidoDTO autoInterrogatorioArguidoDTO = autoInterrogatorioArguidoMapper.autoInterrogatorioArguidoToAutoInterrogatorioArguidoDTO(autoInterrogatorioArguido);

        return Response.<AutoInterrogatorioArguidoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de interrogatório de arguido encontrado com sucesso")
                .data(autoInterrogatorioArguidoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoInterrogatorioArguidoDTO dto) {
        AutoInterrogatorioArguido autoInterrogatorioArguido = autoInterrogatorioArguidoMapper.autoInterrogatorioArguidoDTOToAutoInterrogatorioArguido(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoInterrogatorioArguido.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoInterrogatorioArguido.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoInterrogatorioArguido.setUser(user);
        }

        autoInterrogatorioArguidoRepository.save(autoInterrogatorioArguido);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de interrogatório de arguido criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoInterrogatorioArguidoRepository.existsById(id)) {
            throw new NotFoundException("Auto de interrogatório de arguido não encontrado");
        }
        autoInterrogatorioArguidoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de interrogatório de arguido eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoInterrogatorioArguidoDTO dto, Long id) {
        autoInterrogatorioArguidoRepository.findById(id).ifPresentOrElse(autoInterrogatorioArguido -> {
            if (dto.getNumeroFolha() != null) {
                autoInterrogatorioArguido.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoInterrogatorioArguido.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDefensorOficioso())) {
                autoInterrogatorioArguido.setDefensorOficioso(dto.getDefensorOficioso());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoInterrogatorioArguido.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoInterrogatorioArguido.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoInterrogatorioArguido.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoInterrogatorioArguido.setUser(user);
            }
            autoInterrogatorioArguidoRepository.save(autoInterrogatorioArguido);
        }, () -> {
            throw new NotFoundException("Auto de interrogatório de arguido não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de interrogatório de arguido actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoInterrogatorioArguidoDTO>> getAll() {
        List<AutoInterrogatorioArguidoDTO> autosInterrogatoriosArguidos = autoInterrogatorioArguidoRepository.findAll()
                .stream().map(autoInterrogatorioArguidoMapper::autoInterrogatorioArguidoToAutoInterrogatorioArguidoDTO)
                .toList();

        return Response.<List<AutoInterrogatorioArguidoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosInterrogatoriosArguidos.isEmpty() ? "Nenhum auto de interrogatório de arguido encontrado" : "Autos de interrogatórios de arguidos encontrados com sucesso")
                .data(autosInterrogatoriosArguidos)
                .build();
    }
}
