package projetPFA.gestionFonct.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import projetPFA.gestionFonct.info.embadddedinfo.infoFamil.Conjoint;
import projetPFA.gestionFonct.info.embadddedinfo.infoFamil.Enfant;
import projetPFA.gestionFonct.info.embadddedinfo.infoFamil.InfoFamiliales;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class InfoFamilialesTest {

    private InfoFamiliales infoFamiliales;
    private Conjoint conjoint;
    private Enfant enfant;
    @BeforeEach
    void setUp() {
        // Initialize InfoFamiliales entity and its embedded entities
        infoFamiliales = new InfoFamiliales();
        infoFamiliales.setNomPere("John");
        infoFamiliales.setNomMere("Jane");
        infoFamiliales.setSituationFamiliale("Married");
        infoFamiliales.setNomConjoint("Mary");
        infoFamiliales.setCinConjoint("AB123456");
        infoFamiliales.setDateMariage(new java.util.Date());
        infoFamiliales.setDateNaissanceConjoint(new java.util.Date());
        infoFamiliales.setFonctionConjoint("Teacher");
        infoFamiliales.setNombreEnfants(2);

        // Initialize conjoints and enfants lists with ArrayList (modifiable)
        infoFamiliales.setConjoints(new ArrayList<>());
        infoFamiliales.setEnfants(new ArrayList<>());

        // Create and set Conjoint
        conjoint = new Conjoint();
        conjoint.setCinConjoint("AB123456");
        conjoint.setNomConjoint("Mary");
        conjoint.setDateMariage(new java.util.Date());
        conjoint.setDateNaissanceConjoint(new java.util.Date());
        conjoint.setFonctionConjoint("Teacher");

        // Create and set Enfant
        enfant = new Enfant();
        enfant.setPrenom("Alice");
        enfant.setNomMere("Jane");
        enfant.setDateNaissance(new java.util.Date());

        // Add Conjoint and Enfant to InfoFamiliales
        infoFamiliales.getConjoints().add(conjoint);  // Add Conjoint
        infoFamiliales.getEnfants().add(enfant);      // Add Enfant
    }

    @Test
    void testAddConjointToInfoFamiliales() {
        // Simulate adding a new Conjoint
        Conjoint newConjoint = new Conjoint();
        newConjoint.setNomConjoint("Sara");
        newConjoint.setCinConjoint("CD7891011");

        infoFamiliales.getConjoints().add(newConjoint);

        // Assert that the new Conjoint is added to the list
        assertTrue(infoFamiliales.getConjoints().contains(newConjoint));
    }

    @Test
    void testAddEnfantToInfoFamiliales() {
        // Simulate adding a new Enfant
        Enfant newEnfant = new Enfant();
        newEnfant.setPrenom("Eve");
        newEnfant.setNomMere("Jane");

        infoFamiliales.getEnfants().add(newEnfant);

        // Assert that the new Enfant is added to the list
        assertTrue(infoFamiliales.getEnfants().contains(newEnfant));
    }

    @Test
    void testInfoFamilialesEntityFields() {
        // Test basic fields and their getters/setters
        assertEquals("John", infoFamiliales.getNomPere());
        assertEquals("Jane", infoFamiliales.getNomMere());
        assertEquals("Married", infoFamiliales.getSituationFamiliale());
        assertEquals("Mary", infoFamiliales.getNomConjoint());
        assertEquals("AB123456", infoFamiliales.getCinConjoint());

        // Test the child list
        assertEquals(1, infoFamiliales.getEnfants().size());
        assertEquals("Alice", infoFamiliales.getEnfants().get(0).getPrenom());

        // Test the spouse list
        assertEquals(1, infoFamiliales.getConjoints().size());
        assertEquals("Mary", infoFamiliales.getConjoints().get(0).getNomConjoint());
    }

    @Test
    void testRemoveConjointFromInfoFamiliales() {
        // Remove the Conjoint from the InfoFamiliales entity
        infoFamiliales.getConjoints().remove(conjoint);

        // Assert that the list is now empty
        assertFalse(infoFamiliales.getConjoints().contains(conjoint));
    }

    @Test
    void testRemoveEnfantFromInfoFamiliales() {
        // Remove the Enfant from the InfoFamiliales entity
        infoFamiliales.getEnfants().remove(enfant);

        // Assert that the list is now empty
        assertFalse(infoFamiliales.getEnfants().contains(enfant));
    }

    @Test
    void testAddMultipleConjointsToInfoFamiliales() {
        // Act: Add multiple Conjoints
        Conjoint firstConjoint = new Conjoint();
        firstConjoint.setCinConjoint("XY123456");
        firstConjoint.setNomConjoint("Sophia");
        infoFamiliales.getConjoints().add(firstConjoint);

        Conjoint secondConjoint = new Conjoint();
        secondConjoint.setCinConjoint("XY654321");
        secondConjoint.setNomConjoint("David");
        infoFamiliales.getConjoints().add(secondConjoint);

        // Assert: The Conjoint list should contain 3 Conjoints
        assertEquals(3, infoFamiliales.getConjoints().size());  // We added two more Conjoints
        assertTrue(infoFamiliales.getConjoints().contains(firstConjoint));
        assertTrue(infoFamiliales.getConjoints().contains(secondConjoint));
    }
    @Test
    void testAddMultipleEnfantsToInfoFamiliales() {
        // Act: Add multiple Enfants
        Enfant firstEnfant = new Enfant();
        firstEnfant.setPrenom("Alice");
        firstEnfant.setNomMere("Jane");
        infoFamiliales.getEnfants().add(firstEnfant);

        Enfant secondEnfant = new Enfant();
        secondEnfant.setPrenom("Bob");
        secondEnfant.setNomMere("Jane");
        infoFamiliales.getEnfants().add(secondEnfant);

        // Assert: The Enfant list should contain 3 Enfants
        assertEquals(3, infoFamiliales.getEnfants().size());  // We added two more Enfants
        assertTrue(infoFamiliales.getEnfants().contains(firstEnfant));
        assertTrue(infoFamiliales.getEnfants().contains(secondEnfant));
    }
    @Test
    void testModifyInfoFamilialesDetails() {
        // Act: Modify details of InfoFamiliales
        infoFamiliales.setNomPere("John Updated");
        infoFamiliales.setNomMere("Jane Updated");
        infoFamiliales.setSituationFamiliale("Divorced");

        // Assert: Ensure details were updated
        assertEquals("John Updated", infoFamiliales.getNomPere());
        assertEquals("Jane Updated", infoFamiliales.getNomMere());
        assertEquals("Divorced", infoFamiliales.getSituationFamiliale());
    }

    @Test
    void testInfoFamilialesWithNullConjointOrEnfant() {
        // Act: Set Conjoints and Enfants to null
        infoFamiliales.setConjoints(null);
        infoFamiliales.setEnfants(null);

        // Assert: The lists should be null
        assertNull(infoFamiliales.getConjoints());
        assertNull(infoFamiliales.getEnfants());
    }

    @Test
    void testAddMultipleEnfantsWithSameAttributes() {
        // Act: Add multiple Enfants with the same attributes
        Enfant duplicateEnfant1 = new Enfant();
        duplicateEnfant1.setPrenom("Sam");
        duplicateEnfant1.setNomMere("Marie");
        infoFamiliales.getEnfants().add(duplicateEnfant1);

        Enfant duplicateEnfant2 = new Enfant();
        duplicateEnfant2.setPrenom("Sam");
        duplicateEnfant2.setNomMere("Marie");
        infoFamiliales.getEnfants().add(duplicateEnfant2);

        // Assert: The list should contain both duplicate Enfants if duplicates are allowed
        assertEquals(3, infoFamiliales.getEnfants().size());
        assertTrue(infoFamiliales.getEnfants().contains(duplicateEnfant1));
        assertTrue(infoFamiliales.getEnfants().contains(duplicateEnfant2));
    }


    @Test
    void testUpdateInfoFamilialesAfterAddingConjoint() {
        // Act: Add a Conjoint and update InfoFamiliales' details
        Conjoint newConjoint = new Conjoint();
        newConjoint.setCinConjoint("XY123456");
        newConjoint.setNomConjoint("Emily");
        infoFamiliales.getConjoints().add(newConjoint);

        // Update InfoFamiliales details
        infoFamiliales.setSituationFamiliale("Married");

        // Assert: Ensure the details and lists were updated
        assertEquals("Married", infoFamiliales.getSituationFamiliale());
        assertTrue(infoFamiliales.getConjoints().contains(newConjoint));
    }


    @Test
    void testAddConjointOrEnfantWithSpecialCharacters() {
        // Act: Add Conjoint with special characters in their name
        Conjoint specialConjoint = new Conjoint();
        specialConjoint.setCinConjoint("XY999888");
        specialConjoint.setNomConjoint("John!@#$");
        infoFamiliales.getConjoints().add(specialConjoint);

        Enfant specialEnfant = new Enfant();
        specialEnfant.setPrenom("Olivia$%&");
        specialEnfant.setNomMere("Marie");
        infoFamiliales.getEnfants().add(specialEnfant);

        // Assert: Special characters should be handled properly
        assertEquals(2, infoFamiliales.getConjoints().size());
        assertEquals(2, infoFamiliales.getEnfants().size());
    }
    @Test
    void testUpdateEnfantData() {
        // Act: Add an Enfant and then update its details
        Enfant existingEnfant = new Enfant();
        existingEnfant.setPrenom("OldName");
        existingEnfant.setNomMere("OldMother");
        infoFamiliales.getEnfants().add(existingEnfant);

        // Update Enfant data
        existingEnfant.setPrenom("NewName");
        existingEnfant.setNomMere("NewMother");

        // Assert: Ensure the details are updated
        assertEquals("NewName", existingEnfant.getPrenom());
        assertEquals("NewMother", existingEnfant.getNomMere());
    }
    @Test
    void testNullInfoFamiliales() {
        // Act: Try to add a Conjoint to a null InfoFamiliales object
        InfoFamiliales nullInfoFamiliales = null;
        Conjoint newConjoint = new Conjoint();
        newConjoint.setCinConjoint("111222333");
        newConjoint.setNomConjoint("Test Conjoint");

        // Act & Assert: Should throw NullPointerException
        assertThrows(NullPointerException.class, () -> {
            nullInfoFamiliales.getConjoints().add(newConjoint);
        });
    }

    @Test
    void testAddEnfantWithEmptyName() {
        // Act: Create Enfant with empty name
        Enfant invalidEnfant = new Enfant();
        invalidEnfant.setPrenom("");  // Invalid name
        invalidEnfant.setNomMere("MotherName");

        // Act: Add Enfant to the list
        infoFamiliales.getEnfants().add(invalidEnfant);

        // Assert: The list should contain the invalid Enfant, as there are no validations yet
        assertTrue(infoFamiliales.getEnfants().contains(invalidEnfant));
    }

    @Test
    void testRemoveMultipleEnfantsInBatch() {
        // Act: Add multiple Enfants
        Enfant enfant1 = new Enfant();
        enfant1.setPrenom("Alice");
        enfant1.setNomMere("Mother1");
        infoFamiliales.getEnfants().add(enfant1);

        Enfant enfant2 = new Enfant();
        enfant2.setPrenom("Bob");
        enfant2.setNomMere("Mother2");
        infoFamiliales.getEnfants().add(enfant2);

        // Act: Remove multiple Enfants
        infoFamiliales.getEnfants().removeIf(enfant -> "Alice".equals(enfant.getPrenom()) || "Bob".equals(enfant.getPrenom()));

        // Assert: Both Enfants should be removed
        assertTrue(infoFamiliales.getEnfants().isEmpty());
    }
    @Test
    void testAddInvalidEnfantNull() {
        // Act: Add a null Enfant
        infoFamiliales.getEnfants().add(null);

        // Assert: The list should contain the null Enfant object
        assertTrue(infoFamiliales.getEnfants().contains(null));
    }




}
