package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.DirecaoDTO;
import ao.gov.sic.sip.dtos.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DirecaoService {
    Response<DirecaoDTO> getDirecaoById(Long id);

    Response<?> updateDirecaoById(DirecaoDTO dto, Long id);

    Response<?> createDirecao(DirecaoDTO dto);

    Response<?> deleteDirecaoById(Long id);

    Response<?> bulkDirecoesByCsv(MultipartFile csvFile);

    Response<List<DirecaoDTO>> getAllDirecoes();
}
