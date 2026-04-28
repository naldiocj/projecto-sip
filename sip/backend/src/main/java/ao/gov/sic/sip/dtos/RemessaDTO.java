package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.StatusRemessa;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemessaDTO {
    private Long id;
    private String codigoRastreio;
    private String origem;
    private String destino;
    private LocalDateTime dataEnvio;
    private LocalDateTime dataRecebimento;
    private StatusRemessa status;
    private Set<Long> documentosIds;
    private String responsavelEnvio;
    private MultipartFile arquivo; // For file upload
    private String arquivoUrl; // For file download/display
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
