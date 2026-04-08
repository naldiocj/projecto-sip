package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.DiligenciaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Diligencia;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.DiligenciaMapper;
import ao.gov.sic.sip.repositories.DiligenciaRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.DiligenciaService;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiligenciaServiceImpl implements DiligenciaService {
    private final DiligenciaRepository diligenciaRepository;
    private final DiligenciaMapper diligenciaMapper;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Response<DiligenciaDTO> getById(Long id) {
        Diligencia diligencia = diligenciaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diligência não encontrada"));

        DiligenciaDTO diligenciaDTO = diligenciaMapper.diligenciaToDiligenciaDTO(diligencia);

        return Response.<DiligenciaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Diligência encontrada com sucesso")
                .data(diligenciaDTO)
                .build();
    }

    @Override
    @Transactional
    public Response<?> create(DiligenciaDTO dto) {
        Diligencia diligencia = diligenciaMapper.diligenciaDTOToDiligencia(dto);

        if (dto.getOrdem() != null && diligenciaRepository.existsByOrdem(dto.getOrdem())) {
            diligenciaRepository.incrementOrdemFrom(dto.getOrdem());
            diligencia.setOrdem(dto.getOrdem());
        } else if (dto.getOrdem() == null) {
            Integer maxOrdem = diligenciaRepository.findMaxOrdem();
            diligencia.setOrdem(maxOrdem != null ? maxOrdem + 1 : 1);
        }

        if (dto.getProcessoNumero() != null) {
            Processo processo = processoRepository.findFirstByNumero(dto.getProcessoNumero());

            if (processo == null) {
                throw new NotFoundException("Processo não encontrado");
            }

            diligencia.setProcesso(processo);
        }

        if (diligencia.getEstado() == null && dto.getEstado() != null) {
            diligencia.setEstado(dto.getEstado());
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            diligencia.setUser(user);
        } else {
            User user = userService.currentUser();
            diligencia.setUser(user);
        }

        diligenciaRepository.save(diligencia);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Diligência criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!diligenciaRepository.existsById(id)) {
            throw new NotFoundException("Diligência não encontrada");
        }
        diligenciaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Diligência eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(DiligenciaDTO dto, Long id) {
        diligenciaRepository.findById(id).ifPresentOrElse(diligencia -> {
            if (dto.getOrdem() != null) {
                diligencia.setOrdem(dto.getOrdem());
            }
            if (StringUtils.hasText(dto.getEtapa())) {
                diligencia.setEtapa(dto.getEtapa());
            }
            if (StringUtils.hasText(dto.getTitulo())) {
                diligencia.setTitulo(dto.getTitulo());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                diligencia.setDescricao(dto.getDescricao());
            }
            if (dto.getPrazo() != null) {
                diligencia.setPrazo(dto.getPrazo());
            }
            if (dto.getEstado() != null) {
                diligencia.setEstado(dto.getEstado());
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                diligencia.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                diligencia.setUser(user);
            }
            diligenciaRepository.save(diligencia);
        }, () -> {
            throw new NotFoundException("Diligência não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Diligência actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<DiligenciaDTO>> getAll() {
        List<DiligenciaDTO> diligencias = diligenciaRepository.findAll()
                .stream()
                .map(diligenciaMapper::diligenciaToDiligenciaDTO)
                .sorted(Comparator.comparing(DiligenciaDTO::getOrdem,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();


        return Response.<List<DiligenciaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(diligencias.isEmpty() ? "Nenhuma diligência encontrada" : "Diligências encontradas com sucesso")
                .data(diligencias)
                .build();
    }

    @Override
    public Response<List<DiligenciaDTO>> getAllByProcessoId(Long processoId) {
        List<DiligenciaDTO> diligencias = diligenciaRepository.findAllByProcessoId(processoId)
                .stream()
                .map(diligenciaMapper::diligenciaToDiligenciaDTO)
                .sorted(Comparator.comparing(DiligenciaDTO::getOrdem,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        return Response.<List<DiligenciaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(diligencias.isEmpty() ? "Nenhuma diligência encontrada para este processo" : "Diligências do processo encontradas com sucesso")
                .data(diligencias)
                .build();
    }

    @Override
    public Response<List<DiligenciaDTO>> getAllByProcessoNumero(String numero) {
        List<DiligenciaDTO> diligencias = diligenciaRepository.findAllByProcessoNumero(numero)
                .stream()
                .map(diligenciaMapper::diligenciaToDiligenciaDTO)
                .sorted(Comparator.comparing(DiligenciaDTO::getOrdem,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        return Response.<List<DiligenciaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(diligencias.isEmpty() ? "Nenhuma diligência encontrada para este processo" : "Diligências do processo encontradas com sucesso")
                .data(diligencias)
                .build();
    }

}
