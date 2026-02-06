package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.CategoriaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface CategoriaService {
    Response<CategoriaDTO> getById(Long id);

    Response<?> updateById(CategoriaDTO dto, Long id);

    Response<?> create(CategoriaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<CategoriaDTO>> getAll();
}
