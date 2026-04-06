package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.CotaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Cota;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.CotaMapper;
import ao.gov.sic.sip.repositories.CotaRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.CotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CotaServiceImpl implements CotaService {
    private final CotaRepository cotaRepository;
    private final CotaMapper cotaMapper;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<CotaDTO> getById(Long id) {
        Cota cota = cotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cota não encontrada"));

        CotaDTO cotaDTO = cotaMapper.cotaToCotaDTO(cota);

        return Response.<CotaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cota encontrada com sucesso")
                .data(cotaDTO)
                .build();
    }

    @Override
    public Response<?> create(CotaDTO dto) {
        Cota cota = cotaMapper.cotaDTOToCota(dto);

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            cota.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            cota.setUser(user);
        }

        cotaRepository.save(cota);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Cota criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!cotaRepository.existsById(id)) {
            throw new NotFoundException("Cota não encontrada");
        }
        cotaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cota eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(CotaDTO dto, Long id) {
        cotaRepository.findById(id).ifPresentOrElse(cota -> {
            if (dto.getDataEmissao() != null) {
                cota.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                cota.setMateriaAutos(dto.getMateriaAutos());
            }
            if (StringUtils.hasText(dto.getTermoJuntada())) {
                cota.setTermoJuntada(dto.getTermoJuntada());
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                cota.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                cota.setUser(user);
            }
            cotaRepository.save(cota);
        }, () -> {
            throw new NotFoundException("Cota não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cota actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<CotaDTO>> getAll() {
        List<CotaDTO> cotas = cotaRepository.findAll()
                .stream().map(cotaMapper::cotaToCotaDTO)
                .toList();

        return Response.<List<CotaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(cotas.isEmpty() ? "Nenhuma cota encontrada" : "Cotas encontradas com sucesso")
                .data(cotas)
                .build();
    }
}
