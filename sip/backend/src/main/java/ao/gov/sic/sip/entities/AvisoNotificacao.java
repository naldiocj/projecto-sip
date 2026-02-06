package ao.gov.sic.sip.entities;

import ao.gov.sic.sip.enums.NaQualidade;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "avisos_notificacoes")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE avisos_notificacoes SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class AvisoNotificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroFolha;

    private LocalDate dataEmissao;

    @ManyToOne
    @JoinColumn(name = "arguido_id")
    private Arguido arguido;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco enderecoDestino;

    @Enumerated(EnumType.STRING)
    private NaQualidade naQualidadeDe;

    private LocalDate dataComparencia;

    private Boolean vistoDirector;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isDeleted = Boolean.FALSE;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
