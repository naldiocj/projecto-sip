package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.DirectorDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.DirectorMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.DirectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;
    private final PatenteRepository patenteRepository;
    private final CargoRepository cargoRepository;
    private final DirecaoRepository direcaoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<DirectorDTO> getById(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Director não encontrado"));

        DirectorDTO directorDTO = directorMapper.directorToDirectorDTO(director);

        return Response.<DirectorDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Director encontrado com sucesso")
                .data(directorDTO)
                .build();
    }

    @Override
    public Response<?> create(DirectorDTO dto) {
        Director founded = directorRepository.findByNomeCompletoIgnoreCase(dto.getNomeCompleto());
        if (founded != null) {
            throw new RuntimeException("Director já existe");
        }

        Director director = directorMapper.directorDTOToDirector(dto);

        if (dto.getPatenteId() != null) {
            Patente patente = patenteRepository.findById(dto.getPatenteId())
                    .orElseThrow(() -> new NotFoundException("Patente não encontrada"));
            director.setPatente(patente);
        }

        if (dto.getCargoId() != null) {
            Cargo cargo = cargoRepository.findById(dto.getCargoId())
                    .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));
            director.setCargo(cargo);
        }

        if (dto.getDirecaoId() != null) {
            Direcao direcao = direcaoRepository.findById(dto.getDirecaoId())
                    .orElseThrow(() -> new NotFoundException("Direção não encontrada"));
            director.setDirecao(direcao);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            director.setUser(user);
        }

        directorRepository.save(director);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Director criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!directorRepository.existsById(id)) {
            throw new NotFoundException("Director não encontrado");
        }
        directorRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Director eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(DirectorDTO dto, Long id) {
        directorRepository.findById(id).ifPresentOrElse(director -> {
            if (StringUtils.hasText(dto.getNomeCompleto())) {
                director.setNomeCompleto(dto.getNomeCompleto());
            }
            if (dto.getPatenteId() != null) {
                Patente patente = patenteRepository.findById(dto.getPatenteId())
                        .orElseThrow(() -> new NotFoundException("Patente não encontrada"));
                director.setPatente(patente);
            }
            if (dto.getCargoId() != null) {
                Cargo cargo = cargoRepository.findById(dto.getCargoId())
                        .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));
                director.setCargo(cargo);
            }
            if (dto.getDirecaoId() != null) {
                Direcao direcao = direcaoRepository.findById(dto.getDirecaoId())
                        .orElseThrow(() -> new NotFoundException("Direção não encontrada"));
                director.setDirecao(direcao);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                director.setUser(user);
            }
            if (StringUtils.hasText(dto.getNomeCompleto())
                    || dto.getPatenteId() != null
                    || dto.getCargoId() != null
                    || dto.getDirecaoId() != null) {
                director.setUpdatedAt(LocalDateTime.now());
            }
            directorRepository.save(director);
        }, () -> {
            throw new NotFoundException("Director não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Director actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<DirectorDTO>> getAll() {
        List<DirectorDTO> directores = directorRepository.findAll()
                .stream().map(directorMapper::directorToDirectorDTO)
                .toList();

        return Response.<List<DirectorDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(directores.isEmpty() ? "Nenhum director encontrado" : "Directores encontrados com sucesso")
                .data(directores)
                .build();
    }
}
