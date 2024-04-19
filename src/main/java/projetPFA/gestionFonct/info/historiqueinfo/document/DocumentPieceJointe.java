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

public class DocumentPieceJointe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String cheminStockage;
    private String type;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name="SousDossier_id",referencedColumnName = "id")
    private SousDossier sousDossier;

    /*DocumentPieceJointe
| id | nom                | cheminStockage    | type | fonctionnaire_id | sous_dossier_id |
|----|--------------------|-------------------|------|------------------|-----------------|
| 1  | Document1.pdf      | /chemin/vers/doc | pdf  | 1                | 1               |
| 2  | Document2.docx     | /chemin/vers/doc | docx | 1                | 2               |
| 3  | Document3.jpg      | /chemin/vers/doc | jpg  | 2                | 1               |
*/
}
