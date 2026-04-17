package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.EstadoProcesso;
import ao.gov.sic.sip.enums.TipoProcesso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoResDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String numero;
    private TipoProcesso tipoProcesso;
    private EstadoProcesso estadoProcesso;
    private Integer ano;
    private Set<Item> crimes;
    private Set<Item> arguidos;
    private DirecaoDTO direcao;
    private SecretariaDTO secretaria;
    private QueixosoDTO queixoso;
    private MagistradoDTO magistrado;
    private InstrutorDTO instrutor;
    private UserDTO user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
