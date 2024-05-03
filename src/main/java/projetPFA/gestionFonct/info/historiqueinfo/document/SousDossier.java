package projetPFA.gestionFonct.info.historiqueinfo.document;

import jakarta.persistence.*;
import lombok.*;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.info.embadddedinfo.infoFamil.Conjoint;

import java.util.List;


@Data
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SousDossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private SousDoussiers nomSousDoussier;


}
