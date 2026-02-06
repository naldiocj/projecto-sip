package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.InstrutorDTO;
import ao.gov.sic.sip.dtos.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InstrutorService {
    Response<InstrutorDTO> getById(Long id);

    Response<?> updateById(InstrutorDTO dto, Long id);

    Response<?> create(InstrutorDTO dto);

    Response<?> deleteById(Long id);

    Response<?> bulkInstrutoresByCsv(MultipartFile csvFile);

    Response<List<InstrutorDTO>> getAll();
}
