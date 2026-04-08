package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.PedidoComparenciaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.PedidoComparenciaMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.PedidoComparenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoComparenciaServiceImpl implements PedidoComparenciaService {
    private final PedidoComparenciaRepository pedidoComparenciaRepository;
    private final PedidoComparenciaMapper pedidoComparenciaMapper;
    private final EnderecoRepository enderecoRepository;
    private final ArguidoRepository arguidoRepository;
    private final TestemunhaRepository testemunhaRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<PedidoComparenciaDTO> getById(Long id) {
        PedidoComparencia pedidoComparencia = pedidoComparenciaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido de comparência não encontrado"));

        PedidoComparenciaDTO pedidoComparenciaDTO = pedidoComparenciaMapper.pedidoComparenciaToPedidoComparenciaDTO(pedidoComparencia);

        return Response.<PedidoComparenciaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Pedido de comparência encontrado com sucesso")
                .data(pedidoComparenciaDTO)
                .build();
    }

    @Override
    public Response<?> create(PedidoComparenciaDTO dto) {
        PedidoComparencia pedidoComparencia = pedidoComparenciaMapper.pedidoComparenciaDTOToPedidoComparencia(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            pedidoComparencia.setEndereco(endereco);
        }

        if (dto.getArguidoId() != null) {
            Arguido arguido = arguidoRepository.findById(dto.getArguidoId())
                    .orElseThrow(() -> new NotFoundException("Arguido não encontrado"));
            pedidoComparencia.setArguido(arguido);
        }

        if (dto.getTestemunhaId() != null) {
            Testemunha testemunha = testemunhaRepository.findById(dto.getTestemunhaId())
                    .orElseThrow(() -> new NotFoundException("Testemunha não encontrada"));
            pedidoComparencia.setTestemunha(testemunha);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            pedidoComparencia.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            pedidoComparencia.setUser(user);
        }

        pedidoComparenciaRepository.save(pedidoComparencia);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Pedido de comparência criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!pedidoComparenciaRepository.existsById(id)) {
            throw new NotFoundException("Pedido de comparência não encontrado");
        }
        pedidoComparenciaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Pedido de comparência eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(PedidoComparenciaDTO dto, Long id) {
        pedidoComparenciaRepository.findById(id).ifPresentOrElse(pedidoComparencia -> {
            if (dto.getNumeroFolha() != null) {
                pedidoComparencia.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataComparencia() != null) {
                pedidoComparencia.setDataComparencia(dto.getDataComparencia());
            }
            if (dto.getNaQualidadeDe() != null) {
                pedidoComparencia.setNaQualidadeDe(dto.getNaQualidadeDe());
            }
            if (StringUtils.hasText(dto.getAssunto())) {
                pedidoComparencia.setAssunto(dto.getAssunto());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                pedidoComparencia.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                pedidoComparencia.setEndereco(endereco);
            }
            if (dto.getArguidoId() != null) {
                Arguido arguido = arguidoRepository.findById(dto.getArguidoId())
                        .orElseThrow(() -> new NotFoundException("Arguido não encontrado"));
                pedidoComparencia.setArguido(arguido);
            }
            if (dto.getTestemunhaId() != null) {
                Testemunha testemunha = testemunhaRepository.findById(dto.getTestemunhaId())
                        .orElseThrow(() -> new NotFoundException("Testemunha não encontrada"));
                pedidoComparencia.setTestemunha(testemunha);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                pedidoComparencia.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                pedidoComparencia.setUser(user);
            }
            pedidoComparenciaRepository.save(pedidoComparencia);
        }, () -> {
            throw new NotFoundException("Pedido de comparência não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Pedido de comparência actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<PedidoComparenciaDTO>> getAll() {
        List<PedidoComparenciaDTO> pedidosComparencias = pedidoComparenciaRepository.findAll()
                .stream().map(pedidoComparenciaMapper::pedidoComparenciaToPedidoComparenciaDTO)
                .toList();

        return Response.<List<PedidoComparenciaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(pedidosComparencias.isEmpty() ? "Nenhum pedido de comparência encontrado" : "Pedidos de comparência encontrados com sucesso")
                .data(pedidosComparencias)
                .build();
    }
}
