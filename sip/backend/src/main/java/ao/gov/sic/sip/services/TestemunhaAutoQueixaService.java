package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.dtos.TestemunhaAutoQueixaDTO;

import java.util.List;

public interface TestemunhaAutoQueixaService {
    Response<TestemunhaAutoQueixaDTO> getById(Long id);

    Response<?> updateById(TestemunhaAutoQueixaDTO dto, Long id);

    Response<?> create(TestemunhaAutoQueixaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<TestemunhaAutoQueixaDTO>> getAll();
}
