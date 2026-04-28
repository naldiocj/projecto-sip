package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.SecretariaType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecretariaDTO {
    private Long id;
    private String nomeCompleto;
    private Long patenteId;
    private SecretariaType type;
    private Long direcaoId;
    private DirecaoDTO direcao;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
