package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.TestemunhaDTO;
import ao.gov.sic.sip.dtos.Response;

import java.util.List;

public interface TestemunhaService {
    Response<TestemunhaDTO> getById(Long id);

    Response<?> updateById(TestemunhaDTO dto, Long id);

    Response<?> create(TestemunhaDTO dto);

    Response<?> deleteById(Long id);

    Response<List<TestemunhaDTO>> getAll();
}
