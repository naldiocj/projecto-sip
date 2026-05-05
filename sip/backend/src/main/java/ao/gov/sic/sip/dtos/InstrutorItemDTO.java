package ao.gov.sic.sip.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstrutorItemDTO {
    private Long id;
    private String nomeCompleto;
    private Long patenteId;
    private String patente;
    private Long cargoId;
    private Long direcaoId;
    private DirecaoDTO direcao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
