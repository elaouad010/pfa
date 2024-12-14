package projetPFA.gestionFonct.Models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import projetPFA.gestionFonct.info.embadddedinfo.infoAdmin.Affectation;
import projetPFA.gestionFonct.info.embadddedinfo.infoAdmin.InfoAdministratives;
import projetPFA.gestionFonct.info.embadddedinfo.infoAdmin.SituationAdministrativeEnum;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
@SpringBootTest
class InfoAdministrativesTest {

    @Test
    void testInfoAdministrativesCreation() {
        // Créer une instance de InfoAdministratives
        InfoAdministratives infoAdmin = new InfoAdministratives();

        // Définir les valeurs
        infoAdmin.setPpr("123456");
        infoAdmin.setPb("SomeValue");
        infoAdmin.setDateRecrutement(new Date());
        infoAdmin.setDiplomeRecrutement("Master en Informatique");
        infoAdmin.setAdministrationRecrutement("Administration Publique");
        infoAdmin.setDateTitularisation(new Date());
        infoAdmin.setGrade("Grade A");
        infoAdmin.setEchelle("Echelle 10");
        infoAdmin.setEchelon("Echelon 3");
        infoAdmin.setIndice(500);
        infoAdmin.setStatutAdministratif("Actif");
        infoAdmin.setSituationAdministrative(SituationAdministrativeEnum.en_fonction);
        infoAdmin.setAdminAcc("AdminUser");
        infoAdmin.setAffectation(Affectation.service);
        infoAdmin.setDateSortie(new Date());

        // Vérifier les valeurs
        assertEquals("123456", infoAdmin.getPpr());
        assertEquals("SomeValue", infoAdmin.getPb());
        assertNotNull(infoAdmin.getDateRecrutement());
        assertEquals("Master en Informatique", infoAdmin.getDiplomeRecrutement());
        assertEquals("Administration Publique", infoAdmin.getAdministrationRecrutement());
        assertNotNull(infoAdmin.getDateTitularisation());
        assertEquals("Grade A", infoAdmin.getGrade());
        assertEquals("Echelle 10", infoAdmin.getEchelle());
        assertEquals("Echelon 3", infoAdmin.getEchelon());
        assertEquals(500, infoAdmin.getIndice());
        assertEquals("Actif", infoAdmin.getStatutAdministratif());
        assertEquals(SituationAdministrativeEnum.en_fonction, infoAdmin.getSituationAdministrative());
        assertEquals("AdminUser", infoAdmin.getAdminAcc());
        assertEquals(Affectation.service, infoAdmin.getAffectation());
        assertNotNull(infoAdmin.getDateSortie());
    }

    @Test
    void testEnums() {
        // Tester les énumérations
        assertEquals("en_fonction", SituationAdministrativeEnum.en_fonction.name());
        assertEquals("service", Affectation.service.name());
    }
    @Test
    void testSettersAndGetters() {
        InfoAdministratives infoAdmin = new InfoAdministratives();

        // Définitions et vérifications
        infoAdmin.setPpr("PPR123");
        assertEquals("PPR123", infoAdmin.getPpr());

        infoAdmin.setIndice(100);
        assertEquals(100, infoAdmin.getIndice());

        infoAdmin.setSituationAdministrative(SituationAdministrativeEnum.en_disposition);
        assertEquals(SituationAdministrativeEnum.en_disposition, infoAdmin.getSituationAdministrative());
    }
    @Test
    void testNullValues() {
        InfoAdministratives infoAdmin = new InfoAdministratives();

        // Vérifier que les valeurs par défaut sont nulles (sauf les énumérations)
        assertNull(infoAdmin.getPpr());
        assertNull(infoAdmin.getPb());
        assertNull(infoAdmin.getDateRecrutement());
        assertNull(infoAdmin.getDiplomeRecrutement());
        assertNull(infoAdmin.getAdministrationRecrutement());
        assertNull(infoAdmin.getDateTitularisation());
        assertNull(infoAdmin.getGrade());
        assertNull(infoAdmin.getEchelle());
        assertNull(infoAdmin.getEchelon());
        assertEquals(0, infoAdmin.getIndice());
        assertNull(infoAdmin.getStatutAdministratif());
        assertEquals(SituationAdministrativeEnum.en_fonction, infoAdmin.getSituationAdministrative());
        assertNull(infoAdmin.getAdminAcc());
        assertNull(infoAdmin.getAffectation());
        assertNull(infoAdmin.getDateSortie());
    }
    @Test
    void testUpdateMultipleFields() {
        InfoAdministratives infoAdmin = new InfoAdministratives();
        Date dateRecrutement = new Date();
        Date dateTitularisation = new Date();

        // Définir plusieurs champs
        infoAdmin.setPpr("PPR789");
        infoAdmin.setGrade("Grade B");
        infoAdmin.setDateRecrutement(dateRecrutement);
        infoAdmin.setDateTitularisation(dateTitularisation);

        // Vérifications
        assertEquals("PPR789", infoAdmin.getPpr());
        assertEquals("Grade B", infoAdmin.getGrade());
        assertEquals(dateRecrutement, infoAdmin.getDateRecrutement());
        assertEquals(dateTitularisation, infoAdmin.getDateTitularisation());
    }
    @Test
    void testSituationAndAffectationChanges() {
        InfoAdministratives infoAdmin = new InfoAdministratives();
        infoAdmin.setSituationAdministrative(SituationAdministrativeEnum.en_mutation);
        infoAdmin.setAffectation(Affectation.bureau);

        // Vérification avant modification
        assertEquals(SituationAdministrativeEnum.en_mutation, infoAdmin.getSituationAdministrative());
        assertEquals(Affectation.bureau, infoAdmin.getAffectation());

        // Modifications
        infoAdmin.setSituationAdministrative(SituationAdministrativeEnum.en_disposition);
        infoAdmin.setAffectation(Affectation.divisions);

        // Vérifications après modification
        assertEquals(SituationAdministrativeEnum.en_disposition, infoAdmin.getSituationAdministrative());
        assertEquals(Affectation.divisions, infoAdmin.getAffectation());
    }


    @Test
    void testInvalidIndice() {
        InfoAdministratives infoAdmin = new InfoAdministratives();

        // Affecter un indice négatif
        infoAdmin.setIndice(-10);

        // Vérifier que l'indice est bien défini
        assertEquals(-10, infoAdmin.getIndice(), "L'indice ne devrait pas être négatif mais aucun contrôle n'est fait ici.");
    }
    @Test
    void testNullPpr() {
        InfoAdministratives infoAdmin = new InfoAdministratives();

        // Ne pas définir de PPR
        infoAdmin.setPpr(null);

        // Vérifier que le PPR est nul
        assertNull(infoAdmin.getPpr(), "Le champ PPR est nul par défaut.");
    }
    @Test
    void testEmptyFields() {
        InfoAdministratives infoAdmin = new InfoAdministratives();

        // Affecter des champs vides
        infoAdmin.setPpr("");
        infoAdmin.setGrade("");

        // Vérifications
        assertEquals("", infoAdmin.getPpr());
        assertEquals("", infoAdmin.getGrade());
    }
    @Test
    void testMaxIndiceValue() {
        InfoAdministratives infoAdmin = new InfoAdministratives();

        // Affecter une valeur d'indice très grande
        infoAdmin.setIndice(Integer.MAX_VALUE);

        // Vérification
        assertEquals(Integer.MAX_VALUE, infoAdmin.getIndice());
    }

}
