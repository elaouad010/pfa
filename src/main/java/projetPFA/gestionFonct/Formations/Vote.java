package projetPFA.gestionFonct.Formations;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

@Data
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional

public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fonctionnaireCin;

    @ManyToOne
    private BesoinFormation formation;

    private boolean vote;

    // Getters and setters
}
