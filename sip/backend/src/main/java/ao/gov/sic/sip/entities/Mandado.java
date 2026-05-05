package ao.gov.sic.sip.entities;

import ao.gov.sic.sip.enums.EstadoMandado;
import ao.gov.sic.sip.enums.TipoMandado;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mandados")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE mandados SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class Mandado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMandado tipo;

    @Column(nullable = false)
    private String numeroProcesso;

    @Column(nullable = false)
    private String nomeRequerente;

    @Column(nullable = false)
    private String nomeExecutado;

    @Column(nullable = false)
    private LocalDate dataEmitido;

    @Column(nullable = false)
    private LocalDate dataValidade;

    @Column(nullable = false)
    private String emitidoPor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMandado estado;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    private String arquivo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
