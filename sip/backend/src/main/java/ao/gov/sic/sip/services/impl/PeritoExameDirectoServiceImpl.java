package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.PeritoExameDirectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.PeritoExameDirecto;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.PeritoExameDirectoMapper;
import ao.gov.sic.sip.repositories.PeritoExameDirectoRepository;
import ao.gov.sic.sip.services.PeritoExameDirectoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeritoExameDirectoServiceImpl implements PeritoExameDirectoService {
    private final PeritoExameDirectoRepository peritoExameDirectoRepository;
    private final PeritoExameDirectoMapper peritoExameDirectoMapper;

    @Override
    public Response<PeritoExameDirectoDTO> getById(Long id) {
        PeritoExameDirecto peritoExameDirecto = peritoExameDirectoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Perito de exame directo não encontrado"));

        PeritoExameDirectoDTO peritoExameDirectoDTO = peritoExameDirectoMapper.peritoExameDirectoToPeritoExameDirectoDTO(peritoExameDirecto);

        return Response.<PeritoExameDirectoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Perito de exame directo encontrado com sucesso")
                .data(peritoExameDirectoDTO)
                .build();
    }

    @Override
    public Response<?> create(PeritoExameDirectoDTO dto) {
        PeritoExameDirecto founded = peritoExameDirectoRepository.findAllByNome(dto.getNome());
        if (founded != null) {
            throw new RuntimeException("Perito de exame directo já existe");
        }

        PeritoExameDirecto peritoExameDirecto = peritoExameDirectoMapper.peritoExameDirectoDTOToPeritoExameDirecto(dto);
        peritoExameDirectoRepository.save(peritoExameDirecto);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Perito de exame directo criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!peritoExameDirectoRepository.existsById(id)) {
            throw new NotFoundException("Perito de exame directo não encontrado");
        }
        peritoExameDirectoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Perito de exame directo eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(PeritoExameDirectoDTO dto, Long id) {
        peritoExameDirectoRepository.findById(id).ifPresentOrElse(peritoExameDirecto -> {
            if (StringUtils.hasText(dto.getNome())) {
                peritoExameDirecto.setNome(dto.getNome());
            }
            peritoExameDirectoRepository.save(peritoExameDirecto);
        }, () -> {
            throw new NotFoundException("Perito de exame directo não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Perito de exame directo actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<PeritoExameDirectoDTO>> getAll() {
        List<PeritoExameDirectoDTO> peritosExamesDirectos = peritoExameDirectoRepository.findAll()
                .stream().map(peritoExameDirectoMapper::peritoExameDirectoToPeritoExameDirectoDTO)
                .toList();

        return Response.<List<PeritoExameDirectoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(peritosExamesDirectos.isEmpty() ? "Nenhum perito de exame directo encontrado" : "Peritos de exames directos encontrados com sucesso")
                .data(peritosExamesDirectos)
                .build();
    }
}
