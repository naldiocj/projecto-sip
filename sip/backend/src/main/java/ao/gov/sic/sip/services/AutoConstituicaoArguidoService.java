package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoConstituicaoArguidoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoConstituicaoArguidoService {
    Response<AutoConstituicaoArguidoDTO> getById(Long id);

    Response<?> updateById(AutoConstituicaoArguidoDTO dto, Long id);

    Response<?> create(AutoConstituicaoArguidoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoConstituicaoArguidoDTO>> getAll();
}
