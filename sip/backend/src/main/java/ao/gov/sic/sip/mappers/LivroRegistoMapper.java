package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.LivroRegistoDTO;
import ao.gov.sic.sip.entities.LivroRegisto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LivroRegistoMapper {
    @Mapping(source = "userId", target = "user.id")
    LivroRegisto livroRegistoDTOToLivroRegisto(LivroRegistoDTO dto);

    @Mapping(source = "user.id", target = "userId")
    LivroRegistoDTO livroRegistoToLivroRegistoDTO(LivroRegisto entity);
}
