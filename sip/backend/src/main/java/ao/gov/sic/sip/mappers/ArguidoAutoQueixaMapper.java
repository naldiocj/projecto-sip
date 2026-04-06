package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.ArguidoAutoQueixaDTO;
import ao.gov.sic.sip.entities.ArguidoAutoQueixa;
import org.mapstruct.Mapper;

@Mapper
public interface ArguidoAutoQueixaMapper {
    ArguidoAutoQueixa arguidoAutoQueixaDTOToArguidoAutoQueixa(ArguidoAutoQueixaDTO dto);
    ArguidoAutoQueixaDTO arguidoAutoQueixaToArguidoAutoQueixaDTO(ArguidoAutoQueixa entity);
}
