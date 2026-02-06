package ao.gov.sic.sip.entities;

import ao.gov.sic.sip.enums.TipoProcesso;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "processos")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE processos SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(unique = true)
    private String numero;

    @Enumerated(EnumType.STRING)
    private TipoProcesso tipoProcesso;

    private Integer ano;

    @ManyToMany
    @JoinTable(
            name = "processo_crimes",
            joinColumns = @JoinColumn(name = "processo_id"),
            inverseJoinColumns = @JoinColumn(name = "crime_id")
    )
    private Set<TipoCrime> crimes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "processo_arguidos",
            joinColumns = @JoinColumn(name = "processo_id"),
            inverseJoinColumns = @JoinColumn(name = "arguido_id")
    )
    private Set<Arguido> arguidos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "queixoso_id")
    private Queixoso queixoso;

    @ManyToOne
    @JoinColumn(name = "magistrado_id")
    Magistrado magistrado;

    @ManyToOne
    @JoinColumn(name = "instrutor_id")
    private Instrutor instrutor;

    @ManyToOne
    @JoinColumn(name = "advogado_id")
    private Advogado advogado;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

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
