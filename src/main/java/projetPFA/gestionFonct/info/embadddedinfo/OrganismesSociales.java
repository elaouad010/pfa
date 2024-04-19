package projetPFA.gestionFonct.info.embadddedinfo;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganismesSociales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numAffiliationFondationHassan2;
}
