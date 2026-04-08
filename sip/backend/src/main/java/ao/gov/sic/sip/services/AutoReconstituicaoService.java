package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoReconstituicaoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoReconstituicaoService {
    Response<AutoReconstituicaoDTO> getById(Long id);

    Response<?> updateById(AutoReconstituicaoDTO dto, Long id);

    Response<?> create(AutoReconstituicaoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoReconstituicaoDTO>> getAll();
}
