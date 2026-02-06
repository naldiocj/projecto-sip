package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.ProvinciaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Provincia;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ProvinciaMapper;
import ao.gov.sic.sip.repositories.ProvinciaRepository;
import ao.gov.sic.sip.services.ProvinciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProvinciaServiceImpl implements ProvinciaService {
    private final ProvinciaRepository provinciaRepository;
    private final ProvinciaMapper provinciaMapper;

    @Override
    public Response<ProvinciaDTO> getById(Long id) {
        Provincia provincia = provinciaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Província não encontrada"));

        ProvinciaDTO provinciaDTO = provinciaMapper.provinciaToProvinciaDTO(provincia);

        return Response.<ProvinciaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Província encontrada com sucesso")
                .data(provinciaDTO)
                .build();
    }

    @Override
    public Response<?> create(ProvinciaDTO dto) {
        Provincia founded = provinciaRepository.findByNomeIgnoreCase(dto.getNome());
        if (founded != null) {
            throw new RuntimeException("Província já existe");
        }

        Provincia provincia = provinciaMapper.provinciaDTOToProvincia(dto);
        provinciaRepository.save(provincia);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Província criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!provinciaRepository.existsById(id)) {
            throw new NotFoundException("Província não encontrada");
        }
        provinciaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Província eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(ProvinciaDTO dto, Long id) {
        provinciaRepository.findById(id).ifPresentOrElse(provincia -> {
            if (StringUtils.hasText(dto.getNome())) {
                provincia.setNome(dto.getNome());
            }
            provinciaRepository.save(provincia);
        }, () -> {
            throw new NotFoundException("Província não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Província actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<ProvinciaDTO>> getAll() {
        List<ProvinciaDTO> provincias = provinciaRepository.findAll()
                .stream().map(provinciaMapper::provinciaToProvinciaDTO)
                .toList();

        return Response.<List<ProvinciaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(provincias.isEmpty() ? "Nenhuma província encontrada" : "Províncias encontradas com sucesso")
                .data(provincias)
                .build();
    }
}
