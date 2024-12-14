package projetPFA.gestionFonct.info.embadddedinfo.infoFamil;

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
public class Conjoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cinConjoint;
    private Date dateMariage;
    private Date dateDivorce;
    private String nomConjoint;
    private Date dateNaissanceConjoint;
    private String fonctionConjoint;
    @ManyToOne
    private  InfoFamiliales infoFamiliales;
}
