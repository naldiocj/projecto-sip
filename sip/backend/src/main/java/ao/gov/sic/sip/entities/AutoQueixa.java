package ao.gov.sic.sip.entities;

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
@Table(name = "autos_queixas")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE autos_queixas SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class AutoQueixa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEmissao;

    @Column(columnDefinition = "TEXT")
    private String materiaAutos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "autos_queixas_arguidos",
            joinColumns = @JoinColumn(name = "auto_queixa_id"),
            inverseJoinColumns = @JoinColumn(name = "arguido_id")
    )
    private List<ArguidoAutoQueixa> arguidosQueixas = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "autos_queixas_testemunhas",
            joinColumns = @JoinColumn(name = "auto_queixa_id"),
            inverseJoinColumns = @JoinColumn(name = "testemunha_id")
    )
    private List<TestemunhaAutoQueixa> testemunhaQueixas = new ArrayList<>();

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
