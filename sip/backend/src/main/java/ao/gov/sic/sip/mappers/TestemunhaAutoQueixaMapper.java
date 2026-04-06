package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.TestemunhaAutoQueixaDTO;
import ao.gov.sic.sip.entities.TestemunhaAutoQueixa;
import org.mapstruct.Mapper;

@Mapper
public interface TestemunhaAutoQueixaMapper {
    TestemunhaAutoQueixa testemunhaAutoQueixaDTOToTestemunhaAutoQueixa(TestemunhaAutoQueixaDTO dto);
    TestemunhaAutoQueixaDTO testemunhaAutoQueixaToTestemunhaAutoQueixaDTO(TestemunhaAutoQueixa entity);
}
