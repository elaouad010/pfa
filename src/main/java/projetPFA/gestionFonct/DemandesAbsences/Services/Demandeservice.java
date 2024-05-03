package projetPFA.gestionFonct.DemandesAbsences.Services;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projetPFA.gestionFonct.DemandesAbsences.Demande_absence;
import projetPFA.gestionFonct.DemandesAbsences.statusdemande;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.DemandesAbsences.Repositories.DemandeRepository;
import projetPFA.gestionFonct.Repositories.FonctionnaireRepository;

import java.util.List;

import static projetPFA.gestionFonct.DemandesAbsences.statusdemande.ACCEPTED;
import static projetPFA.gestionFonct.DemandesAbsences.statusdemande.REFUSED;

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
    public Demande_absence getDemandByCode(Long code) {
        return demandeRepository.findById(code).orElseThrow(()-> new IllegalStateException("NO DEMANDE FOUND WITH THIS CODE : "+code+"IS FOUND"));
    }
    public long countdemandes() {
        return demandeRepository.count();
    }
    public Demande_absence saveDemandeForFonctionnaire(Demande_absence demande, String cin) {
        Fonctionnaire fonctionnaire = fonctionnaireRepository.findById(cin).orElseThrow(()-> new IllegalStateException("NO fonctionnaire FOUND WITH THIS cin : "+cin+"IS FOUND"));;
        if (fonctionnaire != null) {
            demande.setFonctionnaire(fonctionnaire);
            return demandeRepository.save(demande);
        } else {
            // Gérer le cas où le fonctionnaire n'est pas trouvé
            throw new RuntimeException("Fonctionnaire not found with cin: " + cin);
        }
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