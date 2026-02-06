package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoInterrogatorioArguidoDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoInterrogatorioArguidoService {
    Response<AutoInterrogatorioArguidoDTO> getById(Long id);

    Response<?> updateById(AutoInterrogatorioArguidoDTO dto, Long id);

    Response<?> create(AutoInterrogatorioArguidoDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoInterrogatorioArguidoDTO>> getAll();
}
