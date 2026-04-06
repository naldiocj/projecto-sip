package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoAcariacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AutoAcariacaoService {
    Response<AutoAcariacaoDTO> getById(Long id);

    Response<?> updateById(AutoAcariacaoDTO dto, Long id);

    Response<?> create(AutoAcariacaoDTO dto);

    Response<?> deleteById(Long id);

    Response<?> uploadArquivo(Long id, MultipartFile file);

    Response<List<AutoAcariacaoDTO>> getAll();
}
