package projetPFA.gestionFonct.ServiceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import projetPFA.gestionFonct.Demande_absence;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.InsufficientAbsenceDaysException;
import projetPFA.gestionFonct.Repositories.DemandeRepository;
import projetPFA.gestionFonct.Repositories.FonctionnaireRepository;
import projetPFA.gestionFonct.Services.Demandeservice;
import projetPFA.gestionFonct.statusdemande;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@SpringBootTest
class DemandeTest {

    @Mock
    private DemandeRepository demandeRepository;

    @Mock
    private FonctionnaireRepository fonctionnaireRepository;

    @InjectMocks
    private Demandeservice demandeservice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAjouterDemande() {
        Demande_absence demande = new Demande_absence();
        demandeservice.ajouterdemande(demande);

        ArgumentCaptor<Demande_absence> demandeCaptor = ArgumentCaptor.forClass(Demande_absence.class);
        verify(demandeRepository).save(demandeCaptor.capture());

        Demande_absence capturedDemande = demandeCaptor.getValue();
        assertThat(capturedDemande).isEqualTo(demande);
    }

    @Test
    void testAffichertousdemande() {
        List<Demande_absence> demandes = new ArrayList<>();
        demandes.add(new Demande_absence());
        demandes.add(new Demande_absence());

        when(demandeRepository.findAll()).thenReturn(demandes);

        List<Demande_absence> result = demandeservice.affichertousdemande();

        assertThat(result).hasSize(2);
        verify(demandeRepository, times(1)).findAll();
    }

    @Test
    void testDeletedemande() {
        Long code = 1L;
        when(demandeRepository.existsById(code)).thenReturn(true);

        demandeservice.deletedemande(code);

        verify(demandeRepository, times(1)).deleteById(code);
    }

    @Test
    void testDeletedemandeThrowsException() {
        Long code = 1L;
        when(demandeRepository.existsById(code)).thenReturn(false);

        Exception exception = assertThrows(IllegalStateException.class, () -> demandeservice.deletedemande(code));

        assertThat(exception.getMessage()).isEqualTo("Aucune demande avec ce code : 1 n'existe ");
        verify(demandeRepository, never()).deleteById(code);
    }

    @Test
    void testGetdemandeBycode() {
        Long code = 1L;
        Demande_absence demande = new Demande_absence();
        when(demandeRepository.findById(code)).thenReturn(Optional.of(demande));

        Demande_absence result = demandeservice.getdemandeBycode(code);

        assertThat(result).isEqualTo(demande);
        verify(demandeRepository).findById(code);
    }

    @Test
    void testGetdemandeBycodeThrowsException() {
        Long code = 1L;
        when(demandeRepository.findById(code)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> demandeservice.getdemandeBycode(code));

        assertThat(exception.getMessage()).isEqualTo("Aucune demande avec ce code : 1 n'est trouvée");
        verify(demandeRepository).findById(code);
    }

    @Test
    void testUpdateDemande() {
        Long code = 1L;
        Demande_absence existingDemande = new Demande_absence();
        existingDemande.setNbrjours(5);

        Demande_absence updatedDemande = new Demande_absence();
        updatedDemande.setNbrjours(10);

        when(demandeRepository.findById(code)).thenReturn(Optional.of(existingDemande));
        when(demandeRepository.save(any(Demande_absence.class))).thenReturn(existingDemande);

        Demande_absence result = demandeservice.updateDemande(code, updatedDemande);

        assertThat(result.getNbrjours()).isEqualTo(10);
        verify(demandeRepository).save(existingDemande);
    }

    @Test
    void testSaveDemandeForFonctionnaire() {
        String cin = "CIN123";
        Fonctionnaire fonctionnaire = new Fonctionnaire();
        fonctionnaire.setNbrAbsence(10);

        Demande_absence demande = new Demande_absence();
        demande.setNbrjours(5);

        when(fonctionnaireRepository.findById(cin)).thenReturn(Optional.of(fonctionnaire));
        when(demandeRepository.save(any(Demande_absence.class))).thenReturn(demande);
        when(fonctionnaireRepository.save(any(Fonctionnaire.class))).thenReturn(fonctionnaire);

        Demande_absence result = demandeservice.saveDemandeForFonctionnaire(demande, cin);

        assertThat(result.getReliquat()).isEqualTo(5);
        verify(fonctionnaireRepository).save(fonctionnaire);
        verify(demandeRepository).save(demande);
    }

    @Test
    void testSaveDemandeForFonctionnaireThrowsException() {
        String cin = "CIN123";
        Fonctionnaire fonctionnaire = new Fonctionnaire();
        fonctionnaire.setNbrAbsence(3);

        Demande_absence demande = new Demande_absence();
        demande.setNbrjours(5);

        when(fonctionnaireRepository.findById(cin)).thenReturn(Optional.of(fonctionnaire));

        Exception exception = assertThrows(InsufficientAbsenceDaysException.class, () -> demandeservice.saveDemandeForFonctionnaire(demande, cin));

        assertThat(exception.getMessage()).isEqualTo("Le nombre de jours d'absence restants est insuffisant.");
        verify(demandeRepository, never()).save(demande);
    }


    @Test
    void testCountDemandes() {
        when(demandeRepository.count()).thenReturn(5L);

        long count = demandeservice.countdemandes();
        assertThat(count).isEqualTo(5);
    }

    @Test
    void testCalculateReturnDate() {
        LocalDate startDate = LocalDate.of(2024, 12, 11);
        int days = 3;

        LocalDate result = demandeservice.calculateReturnDate(startDate, days);
        assertThat(result).isEqualTo(LocalDate.of(2024, 12, 16)); // Skips weekends
    }

    @Test
    void testGetAcceptedDemandes() {
        List<Demande_absence> demandes = new ArrayList<>();
        demandes.add(new Demande_absence());
        when(demandeRepository.findByStatus(statusdemande.ACCEPTED)).thenReturn(demandes);

        List<Demande_absence> result = demandeservice.getDemandesAcceptees();
        assertThat(result).hasSize(1);
    }




    @Test
    void testAfficherToutesDemandes_EmptyList() {
        when(demandeRepository.findAll()).thenReturn(new ArrayList<>());
        List<Demande_absence> result = demandeservice.affichertousdemande();
        assertThat(result).isEmpty();
    }



    @Test
    void testDeleteDemande_NonExistentCode() {
        Long code = 999L;
        when(demandeRepository.existsById(code)).thenReturn(false);
        Exception exception = assertThrows(IllegalStateException.class, () -> demandeservice.deletedemande(code));
        assertThat(exception.getMessage()).isEqualTo("Aucune demande avec ce code : " + code + " n'existe ");
    }





//    @Test
//    void testUpdateDemande_PartialUpdate() {
//        Long code = 1L;
//        Demande_absence existingDemande = new Demande_absence();
//        existingDemande.setNbrjours(5);
//        existingDemande.setDatededepart(LocalDate.of(2024,12,10));
//
//        Demande_absence updatedDemande = new Demande_absence();
//        updatedDemande.setNbrjours(10); // Only updating the number of days
//
//        when(demandeRepository.findById(code)).thenReturn(Optional.of(existingDemande));
//        when(demandeRepository.save(existingDemande)).thenReturn(existingDemande);
//
//        Demande_absence result = demandeservice.updateDemande(code, updatedDemande);
//
//        assertThat(result.getNbrjours()).isEqualTo(10);
//        assertThat(result.getDatededepart()).isEqualTo(LocalDate.of(2024, 12, 10));
//    }

    @Test
    void testSaveDemandeForFonctionnaire_NonExistentFonctionnaire() {
        String cin = "INVALID_CIN";
        Demande_absence demande = new Demande_absence();
        when(fonctionnaireRepository.findById(cin)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> demandeservice.saveDemandeForFonctionnaire(demande, cin));
        assertThat(exception.getMessage()).isEqualTo("Aucun fonctionnaire trouvé avec ce CIN : " + cin);
    }



    @Test
    void testSaveDemandeForFonctionnaire_ZeroDaysRemaining() {
        String cin = "CIN123";
        Fonctionnaire fonctionnaire = new Fonctionnaire();
        fonctionnaire.setNbrAbsence(0); // No remaining absence days

        Demande_absence demande = new Demande_absence();
        demande.setNbrjours(1);

        when(fonctionnaireRepository.findById(cin)).thenReturn(Optional.of(fonctionnaire));

        Exception exception = assertThrows(InsufficientAbsenceDaysException.class, () -> demandeservice.saveDemandeForFonctionnaire(demande, cin));
        assertThat(exception.getMessage()).isEqualTo("Le nombre de jours d'absence restants est insuffisant.");
    }

    @Test
    void testCountDemandes_NoDemandes() {
        when(demandeRepository.count()).thenReturn(0L);
        long count = demandeservice.countdemandes();
        assertThat(count).isZero();
    }

    @Test
    void testCalculateReturnDate_ExactWeekend() {
        LocalDate startDate = LocalDate.of(2024, 12, 13); // Friday
        int days = 2; // Saturday and Sunday should be skipped

        LocalDate result = demandeservice.calculateReturnDate(startDate, days);
        assertThat(result).isEqualTo(LocalDate.of(2024, 12, 17)); // Tuesday
    }

    @Test
    void testCalculateReturnDate_ZeroDays() {
        LocalDate startDate = LocalDate.of(2024, 12, 10);
        int days = 0;

        LocalDate result = demandeservice.calculateReturnDate(startDate, days);
        assertThat(result).isEqualTo(startDate);
    }

    @Test
    void testGetAcceptedDemandes_EmptyResult() {
        when(demandeRepository.findByStatus(statusdemande.ACCEPTED)).thenReturn(new ArrayList<>());
        List<Demande_absence> result = demandeservice.getDemandesAcceptees();
        assertThat(result).isEmpty();
    }

    @Test
    void testGetAcceptedDemandes_MultipleResults() {
        List<Demande_absence> demandes = new ArrayList<>();
        demandes.add(new Demande_absence());
        demandes.add(new Demande_absence());

        when(demandeRepository.findByStatus(statusdemande.ACCEPTED)).thenReturn(demandes);

        List<Demande_absence> result = demandeservice.getDemandesAcceptees();
        assertThat(result).hasSize(2);
        verify(demandeRepository, times(1)).findByStatus(statusdemande.ACCEPTED);
    }

}
