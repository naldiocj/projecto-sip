package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.Item;
import ao.gov.sic.sip.entities.Arguido;
import ao.gov.sic.sip.entities.TipoCrime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ItemMapper {

    @Mapping(source = "descricao", target = "nome")
    Item tipoCrimeToItem(TipoCrime tipoCrime);

    @Mapping(source = "nomeCompleto", target = "nome")
    Item arguidoToItem(Arguido arguido);
}
