package ao.gov.sic.sip.dtos;

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
public class TermoEntregaDTO {
    private Long id;
    private Integer numeroFolha;
    private Boolean vistoDirector;
    private LocalDate dataEmissao;
    private Long enderecoId;
    private String despachoMagistrado;
    private String artigoApreendido;
    private LocalDate dataEntrega;
    private Long processoId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
}
