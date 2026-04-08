package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.AutoReconhecimentoDescricaoPessoaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface AutoReconhecimentoDescricaoPessoaService {
    Response<AutoReconhecimentoDescricaoPessoaDTO> getById(Long id);

    Response<?> updateById(AutoReconhecimentoDescricaoPessoaDTO dto, Long id);

    Response<?> create(AutoReconhecimentoDescricaoPessoaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<AutoReconhecimentoDescricaoPessoaDTO>> getAll();
}
