package projetPFA.gestionFonct.Controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.WhereJoinTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.Services.FonctionnaireService;
import projetPFA.gestionFonct.info.embadddedinfo.InfoAssurance;
import projetPFA.gestionFonct.info.embadddedinfo.OrganismesSociales;
import projetPFA.gestionFonct.info.embadddedinfo.infoAdmin.InfoAdministratives;
import projetPFA.gestionFonct.info.embadddedinfo.infoFamil.InfoFamiliales;
import projetPFA.gestionFonct.info.embadddedinfo.infoPrev.InfoPrevoyanceSociale;
import projetPFA.gestionFonct.info.embadddedinfo.infoRetr.InfoRetraite;
import projetPFA.gestionFonct.info.historiqueinfo.Affectation.Affectations;
import projetPFA.gestionFonct.info.historiqueinfo.Diplomes;
import projetPFA.gestionFonct.info.historiqueinfo.Mouvements;
import projetPFA.gestionFonct.info.historiqueinfo.Notations;
import projetPFA.gestionFonct.info.historiqueinfo.Sanctions;
import projetPFA.gestionFonct.info.historiqueinfo.document.DocumentPieceJointe;
import projetPFA.gestionFonct.test;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping
@Data
@CrossOrigin("http://localhost:3000")
public class FonctionnaireController {

    private FonctionnaireService fonctionnaireService;
    @Autowired
    public FonctionnaireController (FonctionnaireService fonctionnaireService){
    this.fonctionnaireService=fonctionnaireService;
    }
    @PostMapping(path="api/info/add")
    public void ajouterFonctionnaire(@RequestBody Fonctionnaire fonctionnaire) {
        fonctionnaireService.ajouterFonctionnaire(fonctionnaire);
    }
    @GetMapping(path="api/info/displayAll")
    public List<Fonctionnaire> getAllFonct(){
        return fonctionnaireService.getAllFonct();
    }
    @GetMapping(path="api/info/displayById/{cin}")
    public Fonctionnaire getFonctById(@PathVariable("cin") String cin){
        return fonctionnaireService.getFonctById(cin);
    }
    @PutMapping(path="api/info/update/{cin}")
    public ResponseEntity<Fonctionnaire> updateFonct(@PathVariable("cin") String cin, @RequestBody Fonctionnaire fonctionnaire){
        Fonctionnaire updatedFonctionnaire = fonctionnaireService.updateFonct(cin,fonctionnaire);
        return ResponseEntity.ok(updatedFonctionnaire);    }
    @DeleteMapping(path="api/info/delete/{cin}")
    public void deleteFonct(@PathVariable("cin") String cin){
        fonctionnaireService.deleteFonct(cin);
    }


}
