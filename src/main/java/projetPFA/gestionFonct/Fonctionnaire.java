package projetPFA.gestionFonct;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import projetPFA.gestionFonct.info.embadddedinfo.*;
import projetPFA.gestionFonct.info.embadddedinfo.infoAdmin.InfoAdministratives;
import projetPFA.gestionFonct.info.embadddedinfo.infoFamil.InfoFamiliales;
import projetPFA.gestionFonct.info.embadddedinfo.infoPrev.InfoPrevoyanceSociale;
import projetPFA.gestionFonct.info.embadddedinfo.infoRetr.InfoRetraite;
import projetPFA.gestionFonct.info.historiqueinfo.Affectation.Affectations;
import projetPFA.gestionFonct.info.historiqueinfo.Diplomes;
import projetPFA.gestionFonct.info.historiqueinfo.Mouvements;
import projetPFA.gestionFonct.info.historiqueinfo.Notations;
import projetPFA.gestionFonct.info.historiqueinfo.Sanctions;
import projetPFA.gestionFonct.info.historiqueinfo.document.DocumentPieceJointe;

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
public class Fonctionnaire {
    @Id
    private String cin;
    private String nom;
    private String prenom;
    private String sexe;
    private Date dateNaissance;
    private String lieuNaissance;
    private String adresse;
    private String numeroTel;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "infofam_id", referencedColumnName = "id")
    private InfoFamiliales infoFamiliales;
    @OneToOne(cascade = CascadeType.ALL)
    private InfoAdministratives infoAdministratives;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "infoprev_id", referencedColumnName = "id")
    private InfoPrevoyanceSociale infoPrevoyanceSociale;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organismesoc_id", referencedColumnName = "id")
    private OrganismesSociales organismesSociales;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inforetraite_id", referencedColumnName = "id")
    private InfoRetraite infoRetraite;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "infoassur_id", referencedColumnName = "id") //la colomne ou va etre la cle etrngere de infoassurance
    private InfoAssurance infoAssurance;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name="fonctionnaire_id",referencedColumnName = "cin")
    private List<Notations> notations;
    //@OneToMany(mappedBy = "fonctionnaire")
   // private List<FormationContinue> formationsContinues;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name="fonctionnaire_id",referencedColumnName = "cin")
    private List<Diplomes> diplomes;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name="fonctionnaire_id",referencedColumnName = "cin")
    private List<Mouvements> mouvements;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name="fonctionnaire_id",referencedColumnName = "cin")
    private List<Affectations> affectations;
    //@OneToMany(mappedBy = "fonctionnaire")
    //private List<AutorisationAbsence> autorisationsAbsences;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name="fonctionnaire_id",referencedColumnName = "cin")
    private List<Sanctions> sanctions;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn (name="fonctionnaire_id",referencedColumnName = "cin")
    private List<DocumentPieceJointe> documentsPiecesJointes;

}
