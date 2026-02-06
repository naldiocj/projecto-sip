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
public class AutoAditamentoDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataEmissao;
    private String descricao;
    private Long enderecoId;
    private Long processoId;
    private String materiaAutos;
    private Long userId;
}
