package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.TestemunhaDTO;
import ao.gov.sic.sip.entities.Testemunha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TestemunhaMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "userId", target = "user.id")
    Testemunha testemunhaDTOToTestemunha(TestemunhaDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "user.id", target = "userId")
    TestemunhaDTO testemunhaToTestemunhaDTO(Testemunha entity);
}
