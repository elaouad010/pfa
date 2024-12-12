package projetPFA.gestionFonct.Formations;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.info.historiqueinfo.Notations;

import java.util.ArrayList;
import java.util.List;

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
