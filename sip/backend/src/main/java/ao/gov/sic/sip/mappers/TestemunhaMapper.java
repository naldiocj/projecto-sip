package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.TestemunhaDTO;
import ao.gov.sic.sip.entities.Testemunha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TestemunhaMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "processoId", target = "processo.id")
    Testemunha testemunhaDTOToTestemunha(TestemunhaDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "processo.numero", target = "processoNumero")
    TestemunhaDTO testemunhaToTestemunhaDTO(Testemunha entity);
}
