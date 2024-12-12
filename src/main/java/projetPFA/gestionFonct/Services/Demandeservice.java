package projetPFA.gestionFonct.Services;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projetPFA.gestionFonct.Demande_absence;
import projetPFA.gestionFonct.InsufficientAbsenceDaysException;
import projetPFA.gestionFonct.statusdemande;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.Repositories.DemandeRepository;
import projetPFA.gestionFonct.Repositories.FonctionnaireRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static projetPFA.gestionFonct.statusdemande.ACCEPTED;
import static projetPFA.gestionFonct.statusdemande.REFUSED;

@Data
@Service
public class Demandeservice {
    private DemandeRepository demandeRepository;
    private FonctionnaireRepository fonctionnaireRepository;
    @Autowired
    public Demandeservice(DemandeRepository demandeRepository,FonctionnaireRepository fonctionnaireRepository
) {
        this.demandeRepository = demandeRepository;
        this.fonctionnaireRepository = fonctionnaireRepository;

    }

    public void ajouterdemande(Demande_absence demande) {
        this.demandeRepository.save(demande);
    }

    public List<Demande_absence> affichertousdemande() {

        return this.demandeRepository.findAll();
    }

    public void deletedemande(Long code) {
        boolean exists = demandeRepository.existsById(code);
        if (!exists) {
            throw new IllegalStateException("Aucune demande avec ce code : " + code + " n'existe ");
        } else {
            demandeRepository.deleteById(code);
        }
    }

    public Demande_absence getdemandeBycode(Long code) {
        return demandeRepository.findById(code)
                .orElseThrow(() -> new IllegalStateException("Aucune demande avec ce code : " + code + " n'est trouvée"));
    }

    public Demande_absence updateDemande(Long code, Demande_absence updatedDemande) {
        // Recherche de la demande d'absence existante par son code
        Demande_absence existingDemande = demandeRepository.findById(code).orElseThrow(()-> new IllegalStateException("NO DEMANDE FOUND WITH THIS CODE : "+code+"IS FOUND"));

        if (existingDemande != null) {
            // Mettre à jour les champs de la demande existante avec les valeurs de la demande mise à jour
            existingDemande.setDatededepart(updatedDemande.getDatededepart());
            existingDemande.setNbrjours(updatedDemande.getNbrjours());
            existingDemande.setNbrjourdeduire(updatedDemande.getNbrjourdeduire());
            existingDemande.setNbrjournepasdeduire(updatedDemande.getNbrjournepasdeduire());
            existingDemande.setType(updatedDemande.getType());
            existingDemande.setReliquat(updatedDemande.getReliquat());
            existingDemande.setCinramplacant(updatedDemande.getCinramplacant());
            existingDemande.setCumul(updatedDemande.getCumul());
            existingDemande.setFonctionnaire(updatedDemande.getFonctionnaire());

            // Enregistrer les modifications dans la base de données
            return demandeRepository.save(existingDemande);
        } else {
            // La demande d'absence n'existe pas avec le code spécifié, renvoyer null ou gérer l'erreur selon votre cas
            return null;
        }
    }
    public List<Demande_absence> getDemandesByCin(String cin) {
        return demandeRepository.findByToncin(cin);
    }
    public List<Demande_absence> getAcceptedDemandesByCin(String cin) {
        return demandeRepository.findByToncinAndStatus(cin, statusdemande.ACCEPTED);
    }
    public List<Demande_absence> getRefusedDemandesByCin(String cin) {
        return demandeRepository.findByToncinAndStatus(cin, statusdemande.REFUSED);
    }
    public Demande_absence getDemandByCode(Long code) {
        return demandeRepository.findById(code).orElseThrow(()-> new IllegalStateException("NO DEMANDE FOUND WITH THIS CODE : "+code+"IS FOUND"));
    }
    public long countdemandes() {
        return demandeRepository.count();
    }

    public Demande_absence saveDemandeForFonctionnaire(Demande_absence demande, String cin) {
        Fonctionnaire fonctionnaire = fonctionnaireRepository.findById(cin)
                .orElseThrow(() -> new IllegalStateException("Aucun fonctionnaire trouvé avec ce CIN : " + cin));

        int nbrAbsenceRestant = fonctionnaire.getNbrAbsence();
        int nbrJourDemandes = demande.getNbrjours();

        if (nbrAbsenceRestant < nbrJourDemandes) {
            throw new InsufficientAbsenceDaysException("Le nombre de jours d'absence restants est insuffisant.");
        }

        int nouveauReliquat = nbrAbsenceRestant - nbrJourDemandes;
        fonctionnaire.setNbrAbsence(nouveauReliquat);
        fonctionnaireRepository.save(fonctionnaire);

        demande.setReliquat(nouveauReliquat);
        demande.setFonctionnaire(fonctionnaire);
        return demandeRepository.save(demande);
    }
    private LocalDate calculateReturnDate(LocalDate departureDate, int numberOfDays) {
        LocalDate returnDate = departureDate;
        int daysRemaining = numberOfDays;

        while (daysRemaining > 0) {
            returnDate = returnDate.plusDays(1);
            if (isWeekDay(returnDate)) {
                daysRemaining--;
            }
        }

        return returnDate;
    }

    private boolean isWeekDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    @Transactional
    public ResponseEntity<Demande_absence> accepterDemande(Long code) {
        Demande_absence demandeAbsence = demandeRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Demande d'absence non trouvée avec le code : " + code));
        demandeAbsence.setStatus(ACCEPTED);
        Demande_absence updatedDemande = demandeRepository.save(demandeAbsence);
        return ResponseEntity.ok(updatedDemande);
    }

    @Transactional
    public ResponseEntity<Demande_absence> refuserDemande(Long code) {
        Demande_absence demandeAbsence = demandeRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Demande d'absence non trouvée avec le code : " + code));
        demandeAbsence.setStatus(REFUSED);
        Demande_absence updatedDemande = demandeRepository.save(demandeAbsence);
        return ResponseEntity.ok(updatedDemande);
    }
    public List<Demande_absence> getDemandesAcceptees() {
        return demandeRepository.findByStatus(statusdemande.ACCEPTED);
    }

    public List<Demande_absence> getDemandesRefusees() {
        return demandeRepository.findByStatus(statusdemande.REFUSED);
    }


}