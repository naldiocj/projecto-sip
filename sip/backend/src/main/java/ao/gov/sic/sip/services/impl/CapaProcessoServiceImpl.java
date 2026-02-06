package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.CapaProcessoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.CapaProcessoMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.CapaProcessoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CapaProcessoServiceImpl implements CapaProcessoService {
    private final CapaProcessoRepository capaProcessoRepository;
    private final CapaProcessoMapper capaProcessoMapper;
    private final EnderecoRepository enderecoRepository;
    private final LivroRegistoRepository livroRegistoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<CapaProcessoDTO> getById(Long id) {
        CapaProcesso capaProcesso = capaProcessoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Capa de processo não encontrada"));

        CapaProcessoDTO capaProcessoDTO = capaProcessoMapper.capaProcessoToCapaProcessoDTO(capaProcesso);

        return Response.<CapaProcessoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Capa de processo encontrada com sucesso")
                .data(capaProcessoDTO)
                .build();
    }

    @Override
    public Response<?> create(CapaProcessoDTO dto) {
        CapaProcesso founded = capaProcessoRepository.findByNumeroExpediente(dto.getNumeroExpediente());
        if (founded != null) {
            throw new RuntimeException("Capa de processo já existe");
        }

        CapaProcesso capaProcesso = capaProcessoMapper.capaProcessoDTOToCapaProcesso(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            capaProcesso.setEndereco(endereco);
        }

        if (dto.getLivroRegistoId() != null) {
            LivroRegisto livroRegisto = livroRegistoRepository.findById(dto.getLivroRegistoId())
                    .orElseThrow(() -> new NotFoundException("Livro de registo não encontrado"));
            capaProcesso.setLivroRegisto(livroRegisto);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            capaProcesso.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            capaProcesso.setUser(user);
        }

        capaProcessoRepository.save(capaProcesso);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Capa de processo criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!capaProcessoRepository.existsById(id)) {
            throw new NotFoundException("Capa de processo não encontrada");
        }
        capaProcessoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Capa de processo eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(CapaProcessoDTO dto, Long id) {
        capaProcessoRepository.findById(id).ifPresentOrElse(capaProcesso -> {
            if (dto.getAno() != null) {
                capaProcesso.setAno(dto.getAno());
            }
            if (StringUtils.hasText(dto.getNumeroExpediente())) {
                capaProcesso.setNumeroExpediente(dto.getNumeroExpediente());
            }
            if (dto.getDataEmissao() != null) {
                capaProcesso.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                capaProcesso.setDescricao(dto.getDescricao());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                capaProcesso.setEndereco(endereco);
            }
            if (dto.getLivroRegistoId() != null) {
                LivroRegisto livroRegisto = livroRegistoRepository.findById(dto.getLivroRegistoId())
                        .orElseThrow(() -> new NotFoundException("Livro de registo não encontrado"));
                capaProcesso.setLivroRegisto(livroRegisto);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                capaProcesso.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                capaProcesso.setUser(user);
            }
            capaProcessoRepository.save(capaProcesso);
        }, () -> {
            throw new NotFoundException("Capa de processo não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Capa de processo actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<CapaProcessoDTO>> getAll() {
        List<CapaProcessoDTO> capasProcessos = capaProcessoRepository.findAll()
                .stream().map(capaProcessoMapper::capaProcessoToCapaProcessoDTO)
                .toList();

        return Response.<List<CapaProcessoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(capasProcessos.isEmpty() ? "Nenhuma capa de processo encontrada" : "Capas de processos encontradas com sucesso")
                .data(capasProcessos)
                .build();
    }
}
