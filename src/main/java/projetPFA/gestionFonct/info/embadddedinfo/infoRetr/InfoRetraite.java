package projetPFA.gestionFonct.info.embadddedinfo.infoRetr;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoRetraite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OrganismeRetraite organismeRetraite;
    private String numeroAffiliationRetraite;
    private Date dateAffiliationRetraite;
}
