package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.CargoDTO;
import ao.gov.sic.sip.dtos.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CargoService {
    Response<CargoDTO> getById(Long id);

    Response<?> updateById(CargoDTO dto, Long id);

    Response<?> create(CargoDTO dto);

    Response<?> deleteById(Long id);

    Response<?> bulkCargosByCsv(MultipartFile csvFile);

    Response<List<CargoDTO>> getAll();
}
