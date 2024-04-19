package projetPFA.gestionFonct.info.historiqueinfo.Affectation;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import projetPFA.gestionFonct.Fonctionnaire;

import java.util.Date;
@Data
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Affectations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Entite entite;
    private Date dateAffectation;
    private String poste ;
}
