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
public class AutoConstituicaoArguidoDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataEmissao;
    private String defensorOficioso;
    private Long enderecoId;
    private String meiosUtilizados;
    private String descricao;
    private String materiaAutos;
    private Long processoId;
    private Long userId;
}
