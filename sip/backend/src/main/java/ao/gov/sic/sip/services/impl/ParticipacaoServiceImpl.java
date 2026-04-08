package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.ParticipacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ParticipacaoMapper;
import ao.gov.sic.sip.records.FileRecord;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.ParticipacaoService;
import ao.gov.sic.sip.services.StorageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipacaoServiceImpl implements ParticipacaoService {
    private final ParticipacaoRepository participacaoRepository;
    private final ParticipacaoMapper participacaoMapper;
    private final QueixosoRepository queixosoRepository;
    private final AutoApreensaoRepository autoApreensaoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;
    private final StorageFileService storageFileService;

    @Override
    public Response<ParticipacaoDTO> getById(Long id) {
        Participacao participacao = participacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Participação não encontrada"));

        ParticipacaoDTO participacaoDTO = participacaoMapper.participacaoToParticipacaoDTO(participacao);

        return Response.<ParticipacaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Participação encontrada com sucesso")
                .data(participacaoDTO)
                .build();
    }

    @Override
    public Response<?> create(ParticipacaoDTO dto) {
        Participacao participacao = participacaoMapper.participacaoDTOToParticipacao(dto);

        if (dto.getQueixosoId() != null) {
            Queixoso queixoso = queixosoRepository.findById(dto.getQueixosoId())
                    .orElseThrow(() -> new NotFoundException("Queixoso não encontrado"));
            participacao.setQueixoso(queixoso);
        } else {
            participacao.setQueixoso(null);
        }

        if (dto.getAutoApreensaoId() != null) {
            AutoApreensao autoApreensao = autoApreensaoRepository.findById(dto.getAutoApreensaoId())
                    .orElseThrow(() -> new NotFoundException("Auto de apreensão não encontrado"));
            participacao.setAutoApreensao(autoApreensao);
        } else {
            participacao.setAutoApreensao(null);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            participacao.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            participacao.setUser(user);
        }

        participacaoRepository.save(participacao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Participação criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!participacaoRepository.existsById(id)) {
            throw new NotFoundException("Participação não encontrada");
        }
        participacaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Participação eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(ParticipacaoDTO dto, Long id) {
        participacaoRepository.findById(id).ifPresentOrElse(participacao -> {
            if (dto.getDataEmissao() != null) {
                participacao.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getParticipante())) {
                participacao.setParticipante(dto.getParticipante());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                participacao.setMateriaAutos(dto.getMateriaAutos());
            }
            if (StringUtils.hasText(dto.getParteApresentacao())) {
                participacao.setParteApresentacao(dto.getParteApresentacao());
            }
            if (StringUtils.hasText(dto.getAutoApreensaoTexto())) {
                participacao.setAutoApreensaoTexto(dto.getAutoApreensaoTexto());
            }
            if (dto.getQueixosoId() != null) {
                Queixoso queixoso = queixosoRepository.findById(dto.getQueixosoId())
                        .orElseThrow(() -> new NotFoundException("Queixoso não encontrado"));
                participacao.setQueixoso(queixoso);
            }
            if (dto.getAutoApreensaoId() != null) {
                AutoApreensao autoApreensao = autoApreensaoRepository.findById(dto.getAutoApreensaoId())
                        .orElseThrow(() -> new NotFoundException("Auto de apreensão não encontrado"));
                participacao.setAutoApreensao(autoApreensao);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                participacao.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                participacao.setUser(user);
            }
            participacaoRepository.save(participacao);
        }, () -> {
            throw new NotFoundException("Participação não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Participação actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<ParticipacaoDTO>> getAll() {
        List<ParticipacaoDTO> participacoes = participacaoRepository.findAll()
                .stream().map(participacaoMapper::participacaoToParticipacaoDTO)
                .toList();

        return Response.<List<ParticipacaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(participacoes.isEmpty() ? "Nenhuma participação encontrada" : "Participações encontradas com sucesso")
                .data(participacoes)
                .build();
    }

    @Override
    public Response<?> addFile(MultipartFile file, Long participacaoId) {
        FileRecord savedFiled = storageFileService.saveToFolder(file, "Participacoes");

        participacaoRepository.findById(participacaoId).ifPresent(participacao -> {
            participacao.setArquivo(savedFiled.getFileName());

            participacaoRepository.save(participacao);
        });


        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Arquivo salvo com sucesso!")
                .data(savedFiled)
                .build();
    }
}
