package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.CategoriaDTO;
import ao.gov.sic.sip.entities.Categoria;
import org.mapstruct.Mapper;

@Mapper
public interface CategoriaMapper {
    Categoria categoriaDTOToCategoria(CategoriaDTO dto);

    CategoriaDTO categoriaToCategoryDTO(Categoria entity);
}
