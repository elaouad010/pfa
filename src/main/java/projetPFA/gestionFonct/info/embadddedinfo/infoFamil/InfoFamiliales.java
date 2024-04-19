package projetPFA.gestionFonct.info.embadddedinfo.infoFamil;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class InfoFamiliales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomPere;
    private String nomMere;
    private String situationFamiliale;
    private Date dateMariage;
    private String nomConjoint;
    private String cinConjoint;
    private Date dateNaissanceConjoint;
    private String fonctionConjoint;
    private Integer nombreEnfants;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name="InfoFamiliales_id",referencedColumnName = "id")
    private List<Conjoint> conjoints;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name="InfoFamiliales_id",referencedColumnName = "id")
    private List<Enfant> enfants;

}
