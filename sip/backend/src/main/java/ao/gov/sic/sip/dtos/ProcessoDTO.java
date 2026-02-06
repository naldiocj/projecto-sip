package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.TipoProcesso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String numero;
    private TipoProcesso tipoProcesso;
    private Integer ano;
    private Set<Long> crimesIds;
    private Set<Long> arguidosIds;
    private Long queixosoId;
    private Long magistradoId;
    private Long instrutorId;
    private Long userId;
}
