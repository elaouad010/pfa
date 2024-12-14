package projetPFA.gestionFonct.ControllorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import projetPFA.gestionFonct.Controllers.DemandeController;
import projetPFA.gestionFonct.Demande_absence;
import projetPFA.gestionFonct.Services.Demandeservice;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
 class DemandeControllerTest {

    @Mock
    private Demandeservice demandeservice;

    @InjectMocks
    private DemandeController demandeController;

    private Demande_absence demande;

    @BeforeEach
    void setUp() {
        // Initialisation d'une demande d'absence pour les tests
        demande = new Demande_absence();
        demande.setCode(1L);
        demande.setToncin("CIN12345");
        demande.setDatededepart(new Date());
        demande.setDateRetour(new Date());
        demande.setNbrjours(5);
        demande.setNbrjourdeduire(3);
        demande.setNbrjournepasdeduire(2);
        demande.setReliquat(10);
        demande.setCumul(8);
    }

    @Test
    void testAjouterDemande() {
        // Simulation du comportement de saveDemandeForFonctionnaire
        when(demandeservice.saveDemandeForFonctionnaire(any(Demande_absence.class), eq("CIN12345"))).thenReturn(demande);

        ResponseEntity<?> response = demandeController.ajouterDemande(demande);

        // Vérification que la réponse a un statut HTTP 201 (CREATED) et que la demande est correctement retournée
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        Demande_absence savedDemande = (Demande_absence) response.getBody();
        assertEquals(demande.getToncin(), savedDemande.getToncin());
    }

    @Test
    void testAfficherTousDemandes() {
        // Test de la méthode pour afficher toutes les demandes
        when(demandeservice.affichertousdemande()).thenReturn(List.of(demande));

        ResponseEntity<List<Demande_absence>> response = demandeController.afficherTousDemandes();

        // Vérification de la réponse
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testSupprimerDemande() {
        // Simulation de la suppression de la demande
        doNothing().when(demandeservice).deletedemande(1L);

        ResponseEntity<String> response = demandeController.supprimerDemande(1L);

        // Vérification que la suppression s'est bien effectuée
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("supprimée avec succès"));
    }

    @Test
    void testAfficherDemandeParCode() {
        // Simulation du comportement de recherche d'une demande par code
        when(demandeservice.getdemandeBycode(1L)).thenReturn(demande);

        ResponseEntity<Demande_absence> response = demandeController.afficherDemandeParCode(1L);

        // Vérification que la réponse contient la demande
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testModifierDemande() {
        // Simulation de la mise à jour d'une demande
        Demande_absence updatedDemande = new Demande_absence();
        updatedDemande.setCode(1L);
        updatedDemande.setToncin("CIN12345");

        when(demandeservice.updateDemande(eq(1L), any(Demande_absence.class))).thenReturn(updatedDemande);

        ResponseEntity<Demande_absence> response = demandeController.modifierDemande(1L, updatedDemande);

        // Vérification que la mise à jour s'est bien effectuée
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CIN12345", response.getBody().getToncin());
    }

    @Test
    void testAccepterDemande() {
        // Simulation de l'acceptation de la demande
        when(demandeservice.accepterDemande(1L)).thenReturn(new ResponseEntity<>(demande, HttpStatus.OK));

        ResponseEntity<Demande_absence> response = demandeController.accepterDemande(1L);

        // Vérification que la réponse est OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testRefuserDemande() {
        // Simulation du refus de la demande
        when(demandeservice.refuserDemande(1L)).thenReturn(new ResponseEntity<>(demande, HttpStatus.OK));

        ResponseEntity<Demande_absence> response = demandeController.refuserDemande(1L);

        // Vérification que la réponse est OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
