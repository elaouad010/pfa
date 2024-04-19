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
public class Diplomes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;
    private String specialite;
    private Date dateObtention;
    private String etablissement;

}
