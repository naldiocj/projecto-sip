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
public class AutoInterrogatorioArguidoDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataEmissao;
    private Long enderecoId;
    private String defensorOficioso;
    private Long processoId;
    private String materiaAutos;
    private Long userId;
}
