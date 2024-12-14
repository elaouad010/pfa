package projetPFA.gestionFonct.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.info.historiqueinfo.Affectation.Affectations;
import projetPFA.gestionFonct.info.historiqueinfo.Affectation.Entite;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
 class AffectationsTest {

    private Affectations affectation;

    @BeforeEach
    public void setUp() {
        affectation = new Affectations();
        affectation.setEntite(Entite.divisions);
        affectation.setDateAffectation(new Date());
        affectation.setPoste("Manager");
        affectation.setFonctionnaire(new Fonctionnaire());  // Assurez-vous d'avoir un constructeur valide pour Fonctionnaire
    }

    @Test
    public void testAffectationFields() {
        // Vérification des champs de l'entité
        assertThat(affectation.getEntite()).isEqualTo(Entite.divisions);
        assertThat(affectation.getPoste()).isEqualTo("Manager");
    }

    @Test
    public void testAffectationDate() {
        Date date = new Date();
        affectation.setDateAffectation(date);
        assertThat(affectation.getDateAffectation()).isEqualTo(date);
    }

    @Test
    public void testFonctionnaireAssociation() {
        Fonctionnaire fonctionnaire = new Fonctionnaire();  // Remplacez par une instance valide de Fonctionnaire
        affectation.setFonctionnaire(fonctionnaire);
        assertThat(affectation.getFonctionnaire()).isEqualTo(fonctionnaire);
    }

    @Test
    public void testAffectationConstructor() {
        Affectations affectation = new Affectations(1L, Entite.secretariat_generale, new Date(), "Director", new Fonctionnaire());
        assertThat(affectation.getEntite()).isEqualTo(Entite.secretariat_generale);
        assertThat(affectation.getPoste()).isEqualTo("Director");
    }
}
