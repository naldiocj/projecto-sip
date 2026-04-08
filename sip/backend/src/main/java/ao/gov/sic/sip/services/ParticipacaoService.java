package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.ParticipacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ParticipacaoService {
    Response<ParticipacaoDTO> getById(Long id);

    Response<?> updateById(ParticipacaoDTO dto, Long id);

    Response<?> create(ParticipacaoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<ParticipacaoDTO>> getAll();

    Response<?> addFile(MultipartFile file, Long id);
}
