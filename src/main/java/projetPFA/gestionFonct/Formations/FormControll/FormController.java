package projetPFA.gestionFonct.Formations.FormControll;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFA.gestionFonct.Formations.BesoinFormation;
import projetPFA.gestionFonct.Formations.FormServ.FormService;
import projetPFA.gestionFonct.Formations.Vote;
import projetPFA.gestionFonct.Services.FonctionnaireService;

import java.util.Comparator;
import java.util.List;

@Data
@CrossOrigin("http://localhost:3000")
@RestController
public class FormController {

    private FormService formService;
    @Autowired
    public FormController (FormService formService){
        this.formService=formService;
    }
    @PostMapping(path="api/info/formation/add")
    public BesoinFormation addFormation(@RequestBody BesoinFormation formation) {
        return formService.saveOrUpdate(formation);
    }
    @PutMapping("api/info/formation/update/{formationId}")
    public BesoinFormation updateFormation(@PathVariable Long formationId, @RequestBody BesoinFormation formationDetails) {
        return formService.updateFormation(formationId, formationDetails);
    }
    @GetMapping("api/info/formation/get/{formationId}")
    public BesoinFormation getFormationById(@PathVariable Long formationId) {
        return formService.getFormationById(formationId);
    }
    @PostMapping(path="api/info/formation/vote")
    public BesoinFormation voteForFormation(@PathVariable Long formationId) {
        return formService.voteForFormation(formationId);
    }

    @GetMapping("/api/info/formation/getAll")
    public List<BesoinFormation> getAllFormationVotes() {
        return formService.getAllFormationVotes();
    }

    @DeleteMapping(path="api/info/formation/delete/{formationId}")
    public void deleteFormation(@PathVariable Long formationId) {
        formService.deleteFormation(formationId);
    }



    @PostMapping("api/info/formation/voter")
    public BesoinFormation voteForFormation(@RequestParam String fonctionnaireCin,
                                            @RequestParam Long formationId,
                                            @RequestParam boolean vote) {
        return formService.voteForFormation(fonctionnaireCin, formationId, vote);
    }
    @GetMapping("/api/info/formation/voterCheck/{fonctionnaireCin}/{formationId}")
    public ResponseEntity<?> checkIfUserVoted(@PathVariable String fonctionnaireCin, @PathVariable Long formationId) {
        boolean hasVoted = formService.hasUserVoted(fonctionnaireCin, formationId);
        return ResponseEntity.ok().body(hasVoted);
    }
    @GetMapping("/api/info/formation/voterCheckAll/{fonctionnaireCin}")
    public ResponseEntity<List<Vote>> getUserVotes(@PathVariable String fonctionnaireCin) {
        List<Vote> votes = formService.getVotesByCin(fonctionnaireCin);
        return ResponseEntity.ok(votes);
    }





}
