package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.RemessaDTO;
import ao.gov.sic.sip.entities.Documento;
import ao.gov.sic.sip.entities.Remessa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RemessaMapper {
    @Mapping(target = "documentosIds", expression = "java(mapDocumentosToIds(entity.getDocumentos()))")
    @Mapping(target = "arquivo", ignore = true)
    @Mapping(source = "arquivo", target = "arquivoUrl")
    RemessaDTO toDTO(Remessa entity);

    @Mapping(target = "documentos", ignore = true)
    @Mapping(target = "arquivo", ignore = true)
    Remessa toEntity(RemessaDTO dto);

    default Set<Long> mapDocumentosToIds(Set<Documento> documentos) {
        if (documentos == null) return null;
        return documentos.stream().map(Documento::getId).collect(Collectors.toSet());
    }
}
