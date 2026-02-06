package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.CargoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Cargo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.CargoMapper;
import ao.gov.sic.sip.records.CargoCSVRecord;
import ao.gov.sic.sip.records.FileRecord;
import ao.gov.sic.sip.repositories.CargoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.CargoCSVService;
import ao.gov.sic.sip.services.CargoService;
import ao.gov.sic.sip.services.StorageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements CargoService {
    private final CargoRepository cargoRepository;
    private final CargoMapper cargoMapper;
    private final CargoCSVService cargoCSVService;
    private final StorageFileService storageFileService;
    private final UserRepository userRepository;

    @Override
    public Response<CargoDTO> getById(Long id) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));

        CargoDTO cargoDTO = cargoMapper.cargoToCargoDTO(cargo);

        return Response.<CargoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cargo encontrado com sucesso")
                .data(cargoDTO)
                .build();
    }

    @Override
    public Response<?> create(CargoDTO dto) {
        Cargo founded = cargoRepository.findByNomeIgnoreCase(dto.getNome());
        if (founded != null) {
            throw new RuntimeException("Cargo já existe");
        }

        Cargo cargo = cargoMapper.cargoDTOToCargo(dto);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            cargo.setUser(user);
        }

        cargoRepository.save(cargo);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Cargo criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!cargoRepository.existsById(id)) {
            throw new NotFoundException("Cargo não encontrado");
        }
        cargoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cargo eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> bulkCargosByCsv(MultipartFile csvFile) {
        try {
            FileRecord fileRecord = storageFileService.save(csvFile);

            List<CargoCSVRecord> csvRecords = cargoCSVService
                    .convertCSV(fileRecord.getFile());

            AtomicReference<Integer> atomicTotalReference = new AtomicReference<>(0);

            for (CargoCSVRecord record : csvRecords) {
                if (record != null) {

                    Cargo founded = cargoRepository.findByNomeIgnoreCase(record.getNome());

                    if (founded == null) {
                        Cargo cargo = new Cargo();
                        cargo.setNome(record.getNome());
                        cargo.setSigla(record.getSigla());
                        cargo.setDescricao(record.getDescricao());
                        cargoRepository.save(cargo);
                        atomicTotalReference.set(atomicTotalReference.get() + 1);
                    }
                }
            }

            storageFileService.remove(fileRecord.getFileName());


            return Response.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(atomicTotalReference.get() == 0 ?
                            "Nenhum cargo foi adicionado, verifique o ficheiro CSV" :
                            "Foram adicionados " + atomicTotalReference.get() + " cargos")
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("e: ", e);
            throw new RuntimeException("Erro ao processar o ficheiro CSV");
        }
    }

    @Override
    public Response<?> updateById(CargoDTO dto, Long id) {
        cargoRepository.findById(id).ifPresentOrElse(cargo -> {
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                cargo.setUser(user);
            }

            if (StringUtils.hasText(dto.getNome())) {
                cargo.setNome(dto.getNome());
            }

            if (StringUtils.hasText(dto.getSigla())) {
                cargo.setSigla(dto.getSigla());
            }

            if (StringUtils.hasText(dto.getDescricao())) {
                cargo.setDescricao(dto.getDescricao());
            }

            if (StringUtils.hasText(dto.getNome()) || StringUtils.hasText(dto.getSigla())
                    || StringUtils.hasText(dto.getDescricao())) {
                cargo.setUpdatedAt(LocalDateTime.now());
            }

            cargoRepository.save(cargo);
        }, () -> {
            throw new NotFoundException("Cargo não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cargo actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<CargoDTO>> getAll() {
        List<CargoDTO> cargos = cargoRepository.findAll()
                .stream().map(cargoMapper::cargoToCargoDTO)
                .toList();

        return Response.<List<CargoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(cargos.isEmpty() ? "Nenhum cargo encontrado" : "Cargos encontrados com sucesso")
                .data(cargos)
                .build();
    }
}
