package ao.gov.sic.sip.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DespachoDTO {
    private Long id;
    private String numeroProcesso;
    private String decisao;
    private LocalDateTime dataDespacho;
    private String autoridadeResponsavel;
    private String observacoes;
    private Long processoId;
    private boolean isFinalizado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
