package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CapaProcessoDTO {
    private Long id;
    private Integer ano;
    private String numeroExpediente;
    private LocalDate dataEmissao;
    private Long enderecoId;
    private Long livroRegistoId;
    private String descricao;
    private Long processoId;
    private Long userId;
}
