package projetPFA.gestionFonct.Formations.FormServ;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.Formations.BesoinFormation;
import projetPFA.gestionFonct.Formations.FormRepo.VoteRepo;
import projetPFA.gestionFonct.Formations.Vote;

import projetPFA.gestionFonct.Formations.FormRepo.FormRepository;
import projetPFA.gestionFonct.Repositories.FonctionnaireRepository;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
public class FormService {
    private FonctionnaireRepository fonctionnaireRepository;
    private VoteRepo voteRepository;
    private FormRepository formRepository;
    private Map<String, List<Long>> votes = new HashMap<>();


    @Autowired
    public FormService(FormRepository formRepository,FonctionnaireRepository fonctionnaireRepository,VoteRepo voteRepository) {
        this.formRepository = formRepository;
        this.voteRepository=voteRepository;
        this.fonctionnaireRepository=fonctionnaireRepository;
    }
    public BesoinFormation getFormationById(Long formationId) {
        return formRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation not found with id: " + formationId));
    }
    public BesoinFormation saveOrUpdate(BesoinFormation formation) {
        return formRepository.save(formation);
    }

    public BesoinFormation voteForFormation(Long formationId) {
        BesoinFormation formationVote = formRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation not found with id: " + formationId));
        formationVote.incrementVotes();
        formationVote.updateClassement();
        return formRepository.save(formationVote);

    }

    public List<BesoinFormation> getAllFormationVotes() {
        List<BesoinFormation> formations = formRepository.findAll();
        Map<String, List<BesoinFormation>> formationsByClass = formations.stream()
                .collect(Collectors.groupingBy(BesoinFormation::getDomaine));

        formationsByClass.forEach((classe, classeFormations) -> {
            classeFormations.sort(Comparator.comparingInt(BesoinFormation::getTotalVotes).reversed());
            int classement = 1;
            int previousVotes = -1;
            for (BesoinFormation formation : classeFormations) {
                if (formation.getTotalVotes() < previousVotes) {
                    classement++;
                }
                else {classement--;}
                formation.setClassement(classement);
                previousVotes = formation.getTotalVotes();
            }
        });
        return formations;
    }

    public void deleteFormation(Long formationId) {
        formRepository.deleteById(formationId);
    }

    public BesoinFormation updateFormation(Long formationId, BesoinFormation formationDetails) {
        BesoinFormation formation = formRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation not found with id: " + formationId));
        formation.setDomaine(formationDetails.getDomaine());
        formation.setThemeprop(formationDetails.getThemeprop());
        formation.setStructurep(formationDetails.getStructurep());
        formation.setClassement(formationDetails.getClassement());
        return formRepository.save(formation);
    }
    public ResponseEntity<?> voter(String fonctionnaireCin, Long formationId, boolean vote) {
        Fonctionnaire fonctionnaire = fonctionnaireRepository.findById(fonctionnaireCin).orElseThrow(()-> new IllegalStateException("NO fonctionnaire WITH THIS CIN : "+fonctionnaireCin+"IS FOUND"));
        BesoinFormation formation = formRepository.findById(formationId).orElseThrow(()-> new IllegalStateException("NO formation WITH THIS id : "+formationId+"IS FOUND"));
        if (vote) {
            formation.setTotalVotes(formation.getTotalVotes() + 1);
        }
        formRepository.save(formation);
        return ResponseEntity.ok("Vote enregistré avec succès");
    }



    public BesoinFormation voteForFormation(String fonctionnaireCin, Long formationId, boolean vote) {
        Fonctionnaire fonctionnaire = fonctionnaireRepository.findById(fonctionnaireCin).orElseThrow(()-> new IllegalStateException("NO fonctionnaire WITH THIS CIN : "+fonctionnaireCin+"IS FOUND"));
        BesoinFormation formation = formRepository.findById(formationId).orElseThrow(()-> new IllegalStateException("NO formation WITH THIS id : "+formationId+"IS FOUND"));
            // Vérifier si le fonctionnaire a déjà voté pour cette formation
            boolean hasVoted = formation.getVotes().stream()
                    .anyMatch(v -> v.getFonctionnaireCin().equals(fonctionnaireCin));

            if (!hasVoted) {
                // Enregistrer le vote
                formation.setTotalVotes(formation.getTotalVotes() + 1);
                Vote nouveauVote = new Vote();
                nouveauVote.setFonctionnaireCin(fonctionnaireCin);
                nouveauVote.setFormation(formation);
                nouveauVote.setVote(vote);
                formation.getVotes().add(nouveauVote);
                formRepository.save(formation);
                return formation;
            } else {
                throw new IllegalStateException("Ce fonctionnaire a déjà voté pour cette formation.");
            }
    }
    public boolean hasUserVoted(String fonctionnaireCin, Long formationId) {
        BesoinFormation formation = formRepository.findById(formationId).orElseThrow(()-> new IllegalStateException("NO formation WITH THIS id : "+formationId+"IS FOUND"));

        return formation.getVotes().stream().anyMatch(v -> v.getFonctionnaireCin().equals(fonctionnaireCin));
    }
    public List<Vote> getVotesByCin(String fonctionnaireCin) {
        return voteRepository.findByFonctionnaireCin(fonctionnaireCin);
    }

}
