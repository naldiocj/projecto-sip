package ao.gov.sic.sip.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacaoDTO {
    private Long id;
    private String titulo;
    private String mensagem;
    private Boolean lida;
    private Long userId;
    private String username; // To display the user's name
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
