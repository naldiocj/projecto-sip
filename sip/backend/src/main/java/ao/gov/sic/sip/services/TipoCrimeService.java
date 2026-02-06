package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.TipoCrimeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TipoCrimeService {
    Response<TipoCrimeDTO> getById(Long id);

    Response<?> updateById(TipoCrimeDTO dto, Long id);

    Response<?> create(TipoCrimeDTO dto);

    Response<?> deleteById(Long id);

    Response<?> bulkTipoCrimesByCsv(MultipartFile csvFile);

    Response<List<TipoCrimeDTO>> getAll();
}
