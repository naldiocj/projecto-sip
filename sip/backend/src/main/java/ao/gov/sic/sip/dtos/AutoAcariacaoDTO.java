package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.TipoDeclaracao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoAcariacaoDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataEmissao;
    private String descricao;
    private Long enderecoId;
    private Long processoId;
    private TipoDeclaracao tipoDeclaracao;
    private String materiaAutos;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String arquivo;
    private Long userId;
}
