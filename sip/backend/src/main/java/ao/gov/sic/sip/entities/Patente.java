package ao.gov.sic.sip.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "patentes")
@AllArgsConstructor
@NoArgsConstructor
public class Patente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    Categoria categoria;
}
