package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoQueixaDTO;
import ao.gov.sic.sip.entities.ArguidoAutoQueixa;
import ao.gov.sic.sip.entities.AutoQueixa;
import ao.gov.sic.sip.entities.TestemunhaAutoQueixa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AutoQueixaMapper {
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "arguidosQueixas", ignore = true)
    @Mapping(target = "testemunhaQueixas", ignore = true)
    AutoQueixa autoQueixaDTOToAutoQueixa(AutoQueixaDTO dto);

    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "arguidosQueixas", target = "arguidosQueixasIds")
    @Mapping(source = "testemunhaQueixas", target = "testemunhaQueixasIds")
    AutoQueixaDTO autoQueixaToAutoQueixaDTO(AutoQueixa entity);

    default List<Long> mapArguidosToIds(List<ArguidoAutoQueixa> arguidos) {
        if (arguidos == null) {
            return null;
        }
        return arguidos.stream().map(ArguidoAutoQueixa::getId).collect(Collectors.toList());
    }

    default List<Long> mapTestemunhasToIds(List<TestemunhaAutoQueixa> testemunhas) {
        if (testemunhas == null) {
            return null;
        }
        return testemunhas.stream().map(TestemunhaAutoQueixa::getId).collect(Collectors.toList());
    }
}
