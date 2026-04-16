package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstrutorDetailDTO {
    private Long id;
    private String nomeCompleto;
    private String patente;
    private String cargo;
    private String direcao;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
