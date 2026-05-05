package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.EstadoMandado;
import ao.gov.sic.sip.enums.TipoMandado;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MandadoDTO {
    private Long id;
    private TipoMandado tipo;
    private String numeroProcesso;
    private String nomeRequerente;
    private String nomeExecutado;
    private LocalDate dataEmitido;
    private LocalDate dataValidade;
    private String emitidoPor;
    private EstadoMandado estado;
    private String observaciones;
    private MultipartFile arquivo;
    private String arquivoUrl;
    private Long userId; // Added userId
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
