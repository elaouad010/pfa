package projetPFA.gestionFonct.info.embadddedinfo.infoFamil;

import jakarta.persistence.*;
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
public class Enfant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String prenom;
    String nomMere;
    Date dateNaissance;

}
