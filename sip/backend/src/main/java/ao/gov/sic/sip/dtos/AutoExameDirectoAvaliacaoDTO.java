package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoExameDirectoAvaliacaoDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataEmissao;
    private Long enderecoId;
    private String artigoExaminado;
    private String descricao;
    private List<Long> peritoExameDirectosAvaliacoesIds;
    private Long processoId;
    private Long userId;
}
