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
@Table(name = "pedidos_comparencias")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE pedidos_comparencias SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class PedidoComparencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroFolha;

    private LocalDate dataComparencia;

    @Enumerated(EnumType.STRING)
    private NaQualidade naQualidadeDe;

    private String assunto;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "arguido_id")
    private Arguido arguido;

    @ManyToOne
    @JoinColumn(name = "testemunha_id")
    private Testemunha testemunha;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;

    @Column(columnDefinition = "TEXT")
    private String materiaAutos;

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
