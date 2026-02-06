package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoExameDirectoAvaliacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoExameDirectoAvaliacaoMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.AutoExameDirectoAvaliacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoExameDirectoAvaliacaoServiceImpl implements AutoExameDirectoAvaliacaoService {
    private final AutoExameDirectoAvaliacaoRepository autoExameDirectoAvaliacaoRepository;
    private final AutoExameDirectoAvaliacaoMapper autoExameDirectoAvaliacaoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;
    private final PeritoExameDirectoAvaliacaoRepository peritoExameDirectoAvaliacaoRepository;

    @Override
    public Response<AutoExameDirectoAvaliacaoDTO> getById(Long id) {
        AutoExameDirectoAvaliacao autoExameDirectoAvaliacao = autoExameDirectoAvaliacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de exame directo de avaliação não encontrado"));

        AutoExameDirectoAvaliacaoDTO autoExameDirectoAvaliacaoDTO = autoExameDirectoAvaliacaoMapper.autoExameDirectoAvaliacaoToAutoExameDirectoAvaliacaoDTO(autoExameDirectoAvaliacao);

        return Response.<AutoExameDirectoAvaliacaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de exame directo de avaliação encontrado com sucesso")
                .data(autoExameDirectoAvaliacaoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoExameDirectoAvaliacaoDTO dto) {
        AutoExameDirectoAvaliacao autoExameDirectoAvaliacao = autoExameDirectoAvaliacaoMapper.autoExameDirectoAvaliacaoDTOToAutoExameDirectoAvaliacao(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoExameDirectoAvaliacao.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoExameDirectoAvaliacao.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoExameDirectoAvaliacao.setUser(user);
        }

        if (dto.getPeritoExameDirectosAvaliacoesIds() != null && !dto.getPeritoExameDirectosAvaliacoesIds().isEmpty()) {
            List<PeritoExameDirectoAvaliacao> peritos = peritoExameDirectoAvaliacaoRepository.findAllById(dto.getPeritoExameDirectosAvaliacoesIds());
            autoExameDirectoAvaliacao.setPeritoExameDirectosAvaliacoes(peritos);
        }

        autoExameDirectoAvaliacaoRepository.save(autoExameDirectoAvaliacao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de exame directo de avaliação criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoExameDirectoAvaliacaoRepository.existsById(id)) {
            throw new NotFoundException("Auto de exame directo de avaliação não encontrado");
        }
        autoExameDirectoAvaliacaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de exame directo de avaliação eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoExameDirectoAvaliacaoDTO dto, Long id) {
        autoExameDirectoAvaliacaoRepository.findById(id).ifPresentOrElse(autoExameDirectoAvaliacao -> {
            if (dto.getNumeroFolha() != null) {
                autoExameDirectoAvaliacao.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoExameDirectoAvaliacao.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getArtigoExaminado())) {
                autoExameDirectoAvaliacao.setArtigoExaminado(dto.getArtigoExaminado());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                autoExameDirectoAvaliacao.setDescricao(dto.getDescricao());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoExameDirectoAvaliacao.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoExameDirectoAvaliacao.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoExameDirectoAvaliacao.setUser(user);
            }
            if (dto.getPeritoExameDirectosAvaliacoesIds() != null) {
                List<PeritoExameDirectoAvaliacao> peritos = peritoExameDirectoAvaliacaoRepository.findAllById(dto.getPeritoExameDirectosAvaliacoesIds());
                autoExameDirectoAvaliacao.setPeritoExameDirectosAvaliacoes(peritos);
            }
            autoExameDirectoAvaliacaoRepository.save(autoExameDirectoAvaliacao);
        }, () -> {
            throw new NotFoundException("Auto de exame directo de avaliação não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de exame directo de avaliação actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoExameDirectoAvaliacaoDTO>> getAll() {
        List<AutoExameDirectoAvaliacaoDTO> autosExamesDirectosAvaliacoes = autoExameDirectoAvaliacaoRepository.findAll()
                .stream().map(autoExameDirectoAvaliacaoMapper::autoExameDirectoAvaliacaoToAutoExameDirectoAvaliacaoDTO)
                .toList();

        return Response.<List<AutoExameDirectoAvaliacaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosExamesDirectosAvaliacoes.isEmpty() ? "Nenhum auto de exame directo de avaliação encontrado" : "Autos de exames directos de avaliações encontrados com sucesso")
                .data(autosExamesDirectosAvaliacoes)
                .build();
    }
}
