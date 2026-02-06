package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AvisoNotificacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Arguido;
import ao.gov.sic.sip.entities.AvisoNotificacao;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AvisoNotificacaoMapper;
import ao.gov.sic.sip.repositories.ArguidoRepository;
import ao.gov.sic.sip.repositories.AvisoNotificacaoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AvisoNotificacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvisoNotificacaoServiceImpl implements AvisoNotificacaoService {
    private final AvisoNotificacaoRepository avisoNotificacaoRepository;
    private final AvisoNotificacaoMapper avisoNotificacaoMapper;
    private final ArguidoRepository arguidoRepository;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AvisoNotificacaoDTO> getById(Long id) {
        AvisoNotificacao avisoNotificacao = avisoNotificacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aviso de notificação não encontrado"));

        AvisoNotificacaoDTO avisoNotificacaoDTO = avisoNotificacaoMapper.avisoNotificacaoToAvisoNotificacaoDTO(avisoNotificacao);

        return Response.<AvisoNotificacaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Aviso de notificação encontrado com sucesso")
                .data(avisoNotificacaoDTO)
                .build();
    }

    @Override
    public Response<?> create(AvisoNotificacaoDTO dto) {
        AvisoNotificacao avisoNotificacao = avisoNotificacaoMapper.avisoNotificacaoDTOToAvisoNotificacao(dto);

        if (dto.getArguidoId() != null) {
            Arguido arguido = arguidoRepository.findById(dto.getArguidoId())
                    .orElseThrow(() -> new NotFoundException("Arguido não encontrado"));
            avisoNotificacao.setArguido(arguido);
        }

        if (dto.getEnderecoDestinoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoDestinoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            avisoNotificacao.setEnderecoDestino(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            avisoNotificacao.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            avisoNotificacao.setUser(user);
        }

        avisoNotificacaoRepository.save(avisoNotificacao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Aviso de notificação criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!avisoNotificacaoRepository.existsById(id)) {
            throw new NotFoundException("Aviso de notificação não encontrado");
        }
        avisoNotificacaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Aviso de notificação eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AvisoNotificacaoDTO dto, Long id) {
        avisoNotificacaoRepository.findById(id).ifPresentOrElse(avisoNotificacao -> {
            if (dto.getNumeroFolha() != null) {
                avisoNotificacao.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                avisoNotificacao.setDataEmissao(dto.getDataEmissao());
            }
            if (dto.getNaQualidadeDe() != null) {
                avisoNotificacao.setNaQualidadeDe(dto.getNaQualidadeDe());
            }
            if (dto.getDataComparencia() != null) {
                avisoNotificacao.setDataComparencia(dto.getDataComparencia());
            }
            if (dto.getVistoDirector() != null) {
                avisoNotificacao.setVistoDirector(dto.getVistoDirector());
            }
            if (dto.getArguidoId() != null) {
                Arguido arguido = arguidoRepository.findById(dto.getArguidoId())
                        .orElseThrow(() -> new NotFoundException("Arguido não encontrado"));
                avisoNotificacao.setArguido(arguido);
            }
            if (dto.getEnderecoDestinoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoDestinoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                avisoNotificacao.setEnderecoDestino(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                avisoNotificacao.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                avisoNotificacao.setUser(user);
            }
            avisoNotificacaoRepository.save(avisoNotificacao);
        }, () -> {
            throw new NotFoundException("Aviso de notificação não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Aviso de notificação actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AvisoNotificacaoDTO>> getAll() {
        List<AvisoNotificacaoDTO> avisosNotificacoes = avisoNotificacaoRepository.findAll()
                .stream().map(avisoNotificacaoMapper::avisoNotificacaoToAvisoNotificacaoDTO)
                .toList();

        return Response.<List<AvisoNotificacaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(avisosNotificacoes.isEmpty() ? "Nenhum aviso de notificação encontrado" : "Avisos de notificações encontrados com sucesso")
                .data(avisosNotificacoes)
                .build();
    }
}
