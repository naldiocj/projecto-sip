package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoConstituicaoArguidoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoConstituicaoArguido;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoConstituicaoArguidoMapper;
import ao.gov.sic.sip.repositories.AutoConstituicaoArguidoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoConstituicaoArguidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoConstituicaoArguidoServiceImpl implements AutoConstituicaoArguidoService {
    private final AutoConstituicaoArguidoRepository autoConstituicaoArguidoRepository;
    private final AutoConstituicaoArguidoMapper autoConstituicaoArguidoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoConstituicaoArguidoDTO> getById(Long id) {
        AutoConstituicaoArguido autoConstituicaoArguido = autoConstituicaoArguidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de constituição de arguido não encontrado"));

        AutoConstituicaoArguidoDTO autoConstituicaoArguidoDTO = autoConstituicaoArguidoMapper.autoConstituicaoArguidoToAutoConstituicaoArguidoDTO(autoConstituicaoArguido);

        return Response.<AutoConstituicaoArguidoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de constituição de arguido encontrado com sucesso")
                .data(autoConstituicaoArguidoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoConstituicaoArguidoDTO dto) {
        AutoConstituicaoArguido autoConstituicaoArguido = autoConstituicaoArguidoMapper.autoConstituicaoArguidoDTOToAutoConstituicaoArguido(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoConstituicaoArguido.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoConstituicaoArguido.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoConstituicaoArguido.setUser(user);
        }

        autoConstituicaoArguidoRepository.save(autoConstituicaoArguido);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de constituição de arguido criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoConstituicaoArguidoRepository.existsById(id)) {
            throw new NotFoundException("Auto de constituição de arguido não encontrado");
        }
        autoConstituicaoArguidoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de constituição de arguido eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoConstituicaoArguidoDTO dto, Long id) {
        autoConstituicaoArguidoRepository.findById(id).ifPresentOrElse(autoConstituicaoArguido -> {
            if (dto.getNumeroFolha() != null) {
                autoConstituicaoArguido.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoConstituicaoArguido.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDefensorOficioso())) {
                autoConstituicaoArguido.setDefensorOficioso(dto.getDefensorOficioso());
            }
            if (StringUtils.hasText(dto.getMeiosUtilizados())) {
                autoConstituicaoArguido.setMeiosUtilizados(dto.getMeiosUtilizados());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                autoConstituicaoArguido.setDescricao(dto.getDescricao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoConstituicaoArguido.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoConstituicaoArguido.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoConstituicaoArguido.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoConstituicaoArguido.setUser(user);
            }
            autoConstituicaoArguidoRepository.save(autoConstituicaoArguido);
        }, () -> {
            throw new NotFoundException("Auto de constituição de arguido não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de constituição de arguido actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoConstituicaoArguidoDTO>> getAll() {
        List<AutoConstituicaoArguidoDTO> autosConstituicoesArguidos = autoConstituicaoArguidoRepository.findAll()
                .stream().map(autoConstituicaoArguidoMapper::autoConstituicaoArguidoToAutoConstituicaoArguidoDTO)
                .toList();

        return Response.<List<AutoConstituicaoArguidoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosConstituicoesArguidos.isEmpty() ? "Nenhum auto de constituição de arguido encontrado" : "Autos de constituições de arguidos encontrados com sucesso")
                .data(autosConstituicoesArguidos)
                .build();
    }
}
