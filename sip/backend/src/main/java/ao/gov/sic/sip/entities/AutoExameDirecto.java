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
@Table(name = "autos_exames_directos")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE autos_exames_directos SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class AutoExameDirecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroFolha;

    private LocalDate dataEmissao;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private String artigoExaminado;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "autos_exames_directos_peritos",
            joinColumns = @JoinColumn(name = "auto_exame_directo_id"),
            inverseJoinColumns = @JoinColumn(name = "perito_exame_directo_id")
    )
    private List<PeritoExameDirecto> peritoExameDirectos = new ArrayList<>();

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
