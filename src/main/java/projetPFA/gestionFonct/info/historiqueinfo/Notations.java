package projetPFA.gestionFonct.info.historiqueinfo;

import jakarta.persistence.*;
import lombok.*;
import projetPFA.gestionFonct.Fonctionnaire;

@Data
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int annee;
    private int note;


    /*Notations
| id | annee | note | fonctionnaire_id |
|----|-------|------|------------------|
| 1  | 2022  | 15   | 1                |
| 2  | 2023  | 14   | 1                |
| 3  | 2021  | 16   | 2                |
*/

}
