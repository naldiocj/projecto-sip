package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.EstadoDetencao;
import ao.gov.sic.sip.enums.Sexo;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetidoDTO {
    private Long id;
    private String numeroProcesso;
    private String nomeCompleto;
    private String nomePai;
    private String nomeMae;
    private LocalDate dataNascimento;
    private String naturalidade;
    private String profissao;
    private String numeroBi;
    private LocalDate dataEmissaoBi;
    private String localEmissaoBi;
    private String telefone;
    private String endereco;
    private String estadoCivil;
    private Integer idade;
    private Sexo sexo;
    private EstadoDetencao estadoDetencao;
    private LocalDateTime dataDetencao;
    private String localDetencao;
    private String motivoDetencao;
    private String observacoes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
