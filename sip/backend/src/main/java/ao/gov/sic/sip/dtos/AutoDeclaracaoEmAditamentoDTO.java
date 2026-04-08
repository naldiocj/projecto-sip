package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.TipoDeclaracao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoDeclaracaoEmAditamentoDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataEmissao;
    private Long enderecoId;
    private String descricao;
    private TipoDeclaracao tipoDeclaracao;
    private String materiaAutos;
    private Long processoId;
    private Long userId;
}
