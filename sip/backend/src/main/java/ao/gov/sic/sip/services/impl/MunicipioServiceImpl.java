package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.MunicipioDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Municipio;
import ao.gov.sic.sip.entities.Provincia;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.MunicipioMapper;
import ao.gov.sic.sip.repositories.MunicipioRepository;
import ao.gov.sic.sip.repositories.ProvinciaRepository;
import ao.gov.sic.sip.services.MunicipioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MunicipioServiceImpl implements MunicipioService {
    private final MunicipioRepository municipioRepository;
    private final MunicipioMapper municipioMapper;
    private final ProvinciaRepository provinciaRepository;

    @Override
    public Response<MunicipioDTO> getById(Long id) {
        Municipio municipio = municipioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Município não encontrado"));

        MunicipioDTO municipioDTO = municipioMapper.municipioToMunicipioDTO(municipio);

        return Response.<MunicipioDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Município encontrado com sucesso")
                .data(municipioDTO)
                .build();
    }

    @Override
    public Response<?> create(MunicipioDTO dto) {
        Municipio founded = municipioRepository.findByNomeIgnoreCase(dto.getNome());
        if (founded != null) {
            throw new RuntimeException("Município já existe");
        }

        Municipio municipio = municipioMapper.municipioDTOToMunicipio(dto);

        if (dto.getProvinciaId() != null) {
            Provincia provincia = provinciaRepository.findById(dto.getProvinciaId())
                    .orElseThrow(() -> new NotFoundException("Província não encontrada"));
            municipio.setProvincia(provincia);
        }

        municipioRepository.save(municipio);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Município criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!municipioRepository.existsById(id)) {
            throw new NotFoundException("Município não encontrado");
        }
        municipioRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Município eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(MunicipioDTO dto, Long id) {
        municipioRepository.findById(id).ifPresentOrElse(municipio -> {
            if (StringUtils.hasText(dto.getNome())) {
                municipio.setNome(dto.getNome());
            }
            if (dto.getProvinciaId() != null) {
                Provincia provincia = provinciaRepository.findById(dto.getProvinciaId())
                        .orElseThrow(() -> new NotFoundException("Província não encontrada"));
                municipio.setProvincia(provincia);
            }
            municipioRepository.save(municipio);
        }, () -> {
            throw new NotFoundException("Município não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Município actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<MunicipioDTO>> getAll() {
        List<MunicipioDTO> municipios = municipioRepository.findAll()
                .stream().map(municipioMapper::municipioToMunicipioDTO)
                .toList();

        return Response.<List<MunicipioDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(municipios.isEmpty() ? "Nenhum município encontrado" : "Municípios encontrados com sucesso")
                .data(municipios)
                .build();
    }
}
