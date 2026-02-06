package ao.gov.sic.sip.entities;

import ao.gov.sic.sip.enums.EstadoCivil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "testemunhas")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE testemunhas SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class Testemunha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;
    private String nomePai;
    private String nomeMae;

    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    private Integer idade;
    private LocalDate dataNascimento;
    private String naturalidade;
    private String profissao;
    private String numeroBi;
    private String dataEmissaoBi;
    private String email;
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

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
