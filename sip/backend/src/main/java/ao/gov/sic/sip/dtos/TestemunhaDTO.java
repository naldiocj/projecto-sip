package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.EstadoCivil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestemunhaDTO {
    private Long id;
    private String nomeCompleto;
    private String nomePai;
    private String nomeMae;
    private EstadoCivil estadoCivil;
    private Integer idade;
    private LocalDate dataNascimento;
    private String naturalidade;
    private String profissao;
    private String numeroBi;
    private String dataEmissaoBi;
    private String email;
    private String telefone;
    private Long enderecoId;
    private Long userId;
}
