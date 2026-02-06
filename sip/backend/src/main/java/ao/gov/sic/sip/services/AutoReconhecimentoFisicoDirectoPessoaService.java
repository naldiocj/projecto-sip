package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoReconhecimentoFisicoDirectoPessoaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoReconhecimentoFisicoDirectoPessoaService {
    Response<AutoReconhecimentoFisicoDirectoPessoaDTO> getById(Long id);

    Response<?> updateById(AutoReconhecimentoFisicoDirectoPessoaDTO dto, Long id);

    Response<?> create(AutoReconhecimentoFisicoDirectoPessoaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoReconhecimentoFisicoDirectoPessoaDTO>> getAll();
}
