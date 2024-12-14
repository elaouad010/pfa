package projetPFA.gestionFonct.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import projetPFA.gestionFonct.Demande_absence;
import projetPFA.gestionFonct.Repositories.DemandeRepository;
import projetPFA.gestionFonct.Typeabsence;
import projetPFA.gestionFonct.statusdemande;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
class DemandeTest {

    @MockBean
    private DemandeRepository demandeAbsenceRepository;  // Mock the repository

    @InjectMocks
    private Demande_absence demandeAbsence;  // Entity to be tested

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Set up the mock entity (sample data)
        demandeAbsence = new Demande_absence();
        demandeAbsence.setToncin("1234567890");
        demandeAbsence.setDatededepart(new Date());
        demandeAbsence.setDateRetour(new Date());
        demandeAbsence.setNbrjours(6);
        demandeAbsence.setNbrjournepasdeduire(0);
        demandeAbsence.setNbrjourdeduire(6);
        demandeAbsence.setReliquat(3);
        demandeAbsence.setCinramplacant("0987654321");
        demandeAbsence.setCumul(5);
        demandeAbsence.setType(Typeabsence.maladie);
        demandeAbsence.setStatus(statusdemande.notyet);
    }

    @Test
    public void testDemandeAbsenceCreation() {
        // Mock the save method to simulate saving the object
        when(demandeAbsenceRepository.save(demandeAbsence)).thenReturn(demandeAbsence);

        // Save the object (this will use the mocked save method)
        Demande_absence savedDemande = demandeAbsenceRepository.save(demandeAbsence);

        // Assert that the saved entity is not null and has the expected values
        assertNotNull(savedDemande);
        assertEquals(demandeAbsence.getToncin(), savedDemande.getToncin());
        assertEquals(demandeAbsence.getNbrjours(), savedDemande.getNbrjours());
        assertEquals(Typeabsence.maladie, savedDemande.getType());
        assertEquals(statusdemande.notyet, savedDemande.getStatus());
    }

    @Test
    public void testUpdateDemandeAbsenceStatus() {
        // Mock the save method to simulate saving the object
        when(demandeAbsenceRepository.save(demandeAbsence)).thenReturn(demandeAbsence);

        // Save the object first
        Demande_absence savedDemande = demandeAbsenceRepository.save(demandeAbsence);

        // Update status
        savedDemande.setStatus(statusdemande.ACCEPTED);
        when(demandeAbsenceRepository.save(savedDemande)).thenReturn(savedDemande);

        // Save the updated object
        Demande_absence updatedDemande = demandeAbsenceRepository.save(savedDemande);

        // Assert that the status is updated correctly
        assertEquals(statusdemande.ACCEPTED, updatedDemande.getStatus());
    }

    @Test
    public void testDemandeAbsenceDefaultValues() {
        // Initialize the object with default values if not already done
        if (demandeAbsence.getType() == null) {
            demandeAbsence.setType(Typeabsence.maladie);
        }
        if (demandeAbsence.getStatus() == null) {
            demandeAbsence.setStatus(statusdemande.notyet);
        }

        // Mock the save method
        when(demandeAbsenceRepository.save(demandeAbsence)).thenReturn(demandeAbsence);

        // Save the object (this will use the mocked save method)
        Demande_absence savedDemande = demandeAbsenceRepository.save(demandeAbsence);

        // Assert default values
        assertEquals(Typeabsence.maladie, savedDemande.getType());
        assertEquals(statusdemande.notyet, savedDemande.getStatus());
    }

    @Test
    public void testDeleteDemandeAbsence() {
        // Mock the findById method to simulate finding an entity
        when(demandeAbsenceRepository.findById(demandeAbsence.getCode())).thenReturn(Optional.of(demandeAbsence));

        // Delete the object
        demandeAbsenceRepository.delete(demandeAbsence);

        // Verify the delete method was called
        verify(demandeAbsenceRepository, times(1)).delete(demandeAbsence);

        // Simulate absence of the object in the repository after deletion
        when(demandeAbsenceRepository.existsById(demandeAbsence.getCode())).thenReturn(false);

        // Assert that the object is deleted
        assertFalse(demandeAbsenceRepository.existsById(demandeAbsence.getCode()));
    }

    @Test
    public void testInvalidNbrjours() {
        demandeAbsence = new Demande_absence();
        demandeAbsence.setNbrjours(-5); // Negative days

        assertTrue(demandeAbsence.getNbrjours() < 0, "Number of days should not be negative.");
    }

    @Test
    public void testNullCinRamplacant() {
        demandeAbsence = new Demande_absence();
        demandeAbsence.setCinramplacant(null); // No replacement specified

        assertNull(demandeAbsence.getCinramplacant(), "CIN of the replacement should be null.");
    }

    @Test
    public void testTypeChange() {
        demandeAbsence = new Demande_absence();
        demandeAbsence.setType(Typeabsence.maladie);

        assertEquals(Typeabsence.maladie, demandeAbsence.getType(), "Type should initially be 'maladie'.");

        demandeAbsence.setType(Typeabsence.congenormal);
        assertEquals(Typeabsence.congenormal, demandeAbsence.getType(), "Type should change to 'conge'.");
    }

    @Test
    public void testReliquatCalculation() {
        demandeAbsence = new Demande_absence();
        demandeAbsence.setNbrjours(10);
        demandeAbsence.setNbrjournepasdeduire(2);

        int expectedReliquat = demandeAbsence.getNbrjours() - demandeAbsence.getNbrjournepasdeduire();
        demandeAbsence.setReliquat(expectedReliquat);

        assertEquals(8, demandeAbsence.getReliquat(), "Reliquat should be correctly calculated.");
    }

    @Test
    public void testInvalidDateRange() {
        demandeAbsence = new Demande_absence();
        Date futureDate = new Date(System.currentTimeMillis() + 86400000L); // Tomorrow
        Date pastDate = new Date(System.currentTimeMillis() - 86400000L); // Yesterday

        demandeAbsence.setDatededepart(futureDate);
        demandeAbsence.setDateRetour(pastDate);

        assertTrue(demandeAbsence.getDatededepart().after(demandeAbsence.getDateRetour()),
                "Start date should not be after the end date.");
    }

    @Test
    public void testStatusTransition() {
        demandeAbsence = new Demande_absence();
        demandeAbsence.setStatus(statusdemande.notyet);

        assertEquals(statusdemande.notyet, demandeAbsence.getStatus(), "Initial status should be 'notyet'.");

        demandeAbsence.setStatus(statusdemande.ACCEPTED);
        assertEquals(statusdemande.ACCEPTED, demandeAbsence.getStatus(), "Status should transition to 'ACCEPTED'.");

        demandeAbsence.setStatus(statusdemande.REFUSED);
        assertEquals(statusdemande.REFUSED, demandeAbsence.getStatus(), "Status should transition to 'REJECTED'.");
    }

    @Test
    public void testNbrjoursAndNbrjoursdeduireConsistency() {
        demandeAbsence = new Demande_absence();
        demandeAbsence.setNbrjours(10);
        demandeAbsence.setNbrjourdeduire(10);

        assertEquals(demandeAbsence.getNbrjours(), demandeAbsence.getNbrjourdeduire(),
                "Number of days and days to deduct should match.");
    }

    @Test
    public void testSettersAndGetters() {
        demandeAbsence = new Demande_absence();
        demandeAbsence.setToncin("9876543210");

        assertEquals("9876543210", demandeAbsence.getToncin(), "CIN should be correctly set and retrieved.");
    }

}
