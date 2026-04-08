package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.InformacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Informacao;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.InformacaoMapper;
import ao.gov.sic.sip.repositories.InformacaoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.InformacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InformacaoServiceImpl implements InformacaoService {
    private final InformacaoRepository informacaoRepository;
    private final InformacaoMapper informacaoMapper;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<InformacaoDTO> getById(Long id) {
        Informacao informacao = informacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Informação não encontrada"));

        InformacaoDTO informacaoDTO = informacaoMapper.informacaoToInformacaoDTO(informacao);

        return Response.<InformacaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Informação encontrada com sucesso")
                .data(informacaoDTO)
                .build();
    }

    @Override
    public Response<?> create(InformacaoDTO dto) {
        Informacao informacao = informacaoMapper.informacaoDTOToInformacao(dto);

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            informacao.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            informacao.setUser(user);
        }

        informacaoRepository.save(informacao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Informação criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!informacaoRepository.existsById(id)) {
            throw new NotFoundException("Informação não encontrada");
        }
        informacaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Informação eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(InformacaoDTO dto, Long id) {
        informacaoRepository.findById(id).ifPresentOrElse(informacao -> {
            if (dto.getDataEmissao() != null) {
                informacao.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                informacao.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                informacao.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                informacao.setUser(user);
            }
            informacaoRepository.save(informacao);
        }, () -> {
            throw new NotFoundException("Informação não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Informação actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<InformacaoDTO>> getAll() {
        List<InformacaoDTO> informacoes = informacaoRepository.findAll()
                .stream().map(informacaoMapper::informacaoToInformacaoDTO)
                .toList();

        return Response.<List<InformacaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(informacoes.isEmpty() ? "Nenhuma informação encontrada" : "Informações encontradas com sucesso")
                .data(informacoes)
                .build();
    }
}
