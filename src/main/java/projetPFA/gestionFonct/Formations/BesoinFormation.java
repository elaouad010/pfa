package projetPFA.gestionFonct.Formations;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class BesoinFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String domaine;
    private String themeprop;
    private int totalVotes;
    private int totalVotesAcc;
    private String structurep;
    private int classement;
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vote> votes = new ArrayList<>();


    /* @ManyToOne
    private Fonctionnaire fonctionnaire;*/
    public void incrementVotes() {
        totalVotes++;
    }

    public void updateClassement() {
        // Assurez-vous que le classement est compris entre 1 et 5
        classement = Math.min(Math.max(1, classement), 5);
    }


}
