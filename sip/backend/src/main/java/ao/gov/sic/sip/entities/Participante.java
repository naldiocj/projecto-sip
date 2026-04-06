package ao.gov.sic.sip.entities;

import ao.gov.sic.sip.enums.TipoParticipante;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "participantes")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE participantes SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "queixoso_id")
    private Queixoso queixoso;

    @ManyToOne
    @JoinColumn(name = "advogado_id")
    private Advogado advogado;

    @ManyToOne
    @JoinColumn(name = "arguido_id")
    private Arguido arguido;

    @ManyToOne
    @JoinColumn(name = "testemunha_id")
    private Testemunha testemunha;

    @Enumerated(EnumType.STRING)
    private TipoParticipante tipoParticipante;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
