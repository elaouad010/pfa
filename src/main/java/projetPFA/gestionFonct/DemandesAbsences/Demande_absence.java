package projetPFA.gestionFonct.DemandesAbsences;
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
public class Demande_absence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long code;
    private String toncin;
    private Date datededepart;
    private int nbrjours;
    private int nbrjourdeduire;
    private int nbrjournepasdeduire;
    private Typeabsence type= Typeabsence.maladie;
    private int reliquat;
    private  String cinramplacant;
    private int cumul;
    private statusdemande status=statusdemande.notyet;

    @ManyToOne
    private Fonctionnaire fonctionnaire;




}
