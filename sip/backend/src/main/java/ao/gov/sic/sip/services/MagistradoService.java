package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.MagistradoDTO;
import ao.gov.sic.sip.dtos.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MagistradoService {
    Response<MagistradoDTO> getById(Long id);

    Response<?> updateById(MagistradoDTO dto, Long id);

    Response<?> create(MagistradoDTO dto);

    Response<?> deleteById(Long id);

    Response<?> bulkMagistradosByCsv(MultipartFile csvFile);

    Response<List<MagistradoDTO>> getAll();
}
