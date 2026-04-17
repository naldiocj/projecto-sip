package ao.gov.sic.sip.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoDTO {
    private Long id;
    private String numeroProcesso;
    private String tipoModelo;
    private Long userId;
    private String userName; // Opcional: para exibição no frontend
    private Map<String, Object> conteudo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}