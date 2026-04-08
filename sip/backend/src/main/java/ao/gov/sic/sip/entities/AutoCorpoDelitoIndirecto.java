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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autos_corpo_delitos_indirectos")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE autos_corpo_delitos_indirectos SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class AutoCorpoDelitoIndirecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroFolha;

    private LocalDate dataEmissao;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private NaQualidade naQualidadeDe;

    @Column(columnDefinition = "TEXT")
    private String naturezaCaracteristicas;

    @Column(columnDefinition = "TEXT")
    private String materiaAutos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "autos_corpo_delitos_indirectos_testemunhas",
            joinColumns = @JoinColumn(name = "auto_corpo_delito_indirecto_id"),
            inverseJoinColumns = @JoinColumn(name = "testemunha_id")
    )
    @Builder.Default
    private List<Testemunha> testemunhas = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;

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
