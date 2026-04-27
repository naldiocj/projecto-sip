package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.DocumentoDTO;
import ao.gov.sic.sip.entities.Documento;
import org.springframework.stereotype.Component;

@Component
public class DocumentoMapper {

    public DocumentoDTO toDTO(Documento entity) {
        if (entity == null) return null;
        
        return DocumentoDTO.builder()
                .id(entity.getId())
                .numeroProcesso(entity.getNumeroProcesso())
                .tipoModelo(entity.getTipoModelo())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .userName(entity.getUser() != null ? entity.getUser().getName() : null)
                .pagina(entity.getPagina())
                .conteudo(entity.getConteudo())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public Documento toEntity(DocumentoDTO dto) {
        if (dto == null) return null;

        return Documento.builder()
                .numeroProcesso(dto.getNumeroProcesso())
                .tipoModelo(dto.getTipoModelo())
                .conteudo(dto.getConteudo())
                .build();
    }
}