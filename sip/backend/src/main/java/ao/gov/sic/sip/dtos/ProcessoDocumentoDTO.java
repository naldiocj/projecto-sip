package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoDocumentoDTO {
    private Long id;

    private String titulo;
    private String tipo;
    private String descricao;
    private MultipartFile arquivo;
    private String processoNumero;
    private Long documentoId;
    private DocumentoDTO documentoDTO;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
