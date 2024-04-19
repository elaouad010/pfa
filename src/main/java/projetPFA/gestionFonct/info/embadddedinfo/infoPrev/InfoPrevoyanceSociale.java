package projetPFA.gestionFonct.info.embadddedinfo.infoPrev;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoPrevoyanceSociale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrganismePrevoyanceSociale organismePrevoyanceSociale;
    private String numAffiliationCNOPS;
    private String numImmatriculationCNOPS;
    private Date dateAffiliationCNOPS;
}
