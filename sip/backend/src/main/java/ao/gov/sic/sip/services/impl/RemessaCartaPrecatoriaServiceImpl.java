package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.RemessaCartaPrecatoriaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.RemessaCartaPrecatoria;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.RemessaCartaPrecatoriaMapper;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.RemessaCartaPrecatoriaRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.RemessaCartaPrecatoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemessaCartaPrecatoriaServiceImpl implements RemessaCartaPrecatoriaService {
    private final RemessaCartaPrecatoriaRepository remessaCartaPrecatoriaRepository;
    private final RemessaCartaPrecatoriaMapper remessaCartaPrecatoriaMapper;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<RemessaCartaPrecatoriaDTO> getById(Long id) {
        RemessaCartaPrecatoria remessaCartaPrecatoria = remessaCartaPrecatoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Remessa de carta precatória não encontrada"));

        RemessaCartaPrecatoriaDTO remessaCartaPrecatoriaDTO = remessaCartaPrecatoriaMapper.remessaCartaPrecatoriaToRemessaCartaPrecatoriaDTO(remessaCartaPrecatoria);

        return Response.<RemessaCartaPrecatoriaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Remessa de carta precatória encontrada com sucesso")
                .data(remessaCartaPrecatoriaDTO)
                .build();
    }

    @Override
    public Response<?> create(RemessaCartaPrecatoriaDTO dto) {
        RemessaCartaPrecatoria founded = remessaCartaPrecatoriaRepository.findAllByNumeroOficio(dto.getNumeroOficio());
        if (founded != null) {
            throw new RuntimeException("Remessa de carta precatória já existe");
        }

        RemessaCartaPrecatoria remessaCartaPrecatoria = remessaCartaPrecatoriaMapper.remessaCartaPrecatoriaDTOToRemessaCartaPrecatoria(dto);

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            remessaCartaPrecatoria.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            remessaCartaPrecatoria.setUser(user);
        }

        remessaCartaPrecatoriaRepository.save(remessaCartaPrecatoria);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Remessa de carta precatória criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!remessaCartaPrecatoriaRepository.existsById(id)) {
            throw new NotFoundException("Remessa de carta precatória não encontrada");
        }
        remessaCartaPrecatoriaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Remessa de carta precatória eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(RemessaCartaPrecatoriaDTO dto, Long id) {
        remessaCartaPrecatoriaRepository.findById(id).ifPresentOrElse(remessaCartaPrecatoria -> {
            if (StringUtils.hasText(dto.getNumeroOficio())) {
                remessaCartaPrecatoria.setNumeroOficio(dto.getNumeroOficio());
            }
            if (StringUtils.hasText(dto.getNumeroCarta())) {
                remessaCartaPrecatoria.setNumeroCarta(dto.getNumeroCarta());
            }
            if (StringUtils.hasText(dto.getAssunto())) {
                remessaCartaPrecatoria.setAssunto(dto.getAssunto());
            }
            if (dto.getDataEmissao() != null) {
                remessaCartaPrecatoria.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                remessaCartaPrecatoria.setDescricao(dto.getDescricao());
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                remessaCartaPrecatoria.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                remessaCartaPrecatoria.setUser(user);
            }
            remessaCartaPrecatoriaRepository.save(remessaCartaPrecatoria);
        }, () -> {
            throw new NotFoundException("Remessa de carta precatória não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Remessa de carta precatória actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<RemessaCartaPrecatoriaDTO>> getAll() {
        List<RemessaCartaPrecatoriaDTO> remessasCartasPrecatorias = remessaCartaPrecatoriaRepository.findAll()
                .stream().map(remessaCartaPrecatoriaMapper::remessaCartaPrecatoriaToRemessaCartaPrecatoriaDTO)
                .toList();

        return Response.<List<RemessaCartaPrecatoriaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(remessasCartasPrecatorias.isEmpty() ? "Nenhuma remessa de carta precatória encontrada" : "Remessas de cartas precatórias encontradas com sucesso")
                .data(remessasCartasPrecatorias)
                .build();
    }
}
