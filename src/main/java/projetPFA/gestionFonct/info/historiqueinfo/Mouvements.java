package projetPFA.gestionFonct.info.historiqueinfo;

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
public class Mouvements{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String administration;
    private Date dateDebut;
    private Date dateFin;

}
