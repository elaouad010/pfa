package projetPFA.gestionFonct.info.embadddedinfo.infoAdmin;

import jakarta.persistence.*;
import lombok.*;
import projetPFA.gestionFonct.info.historiqueinfo.Notations;

import java.util.Date;
import java.util.List;


@Data
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoAdministratives {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ppr;
    private String pb;
    private Date dateRecrutement;
    private String diplomeRecrutement;
    private String administrationRecrutement;
    private Date dateTitularisation;
    private String grade;
    private String echelle;
    private String echelon;
    private int indice;
    private String statutAdministratif;
    private SituationAdministrativeEnum situationAdministrative=SituationAdministrativeEnum.en_fonction;
    private String AdminAcc ;
    private Affectation Affectation ;
    private Date DateSortie;



}
