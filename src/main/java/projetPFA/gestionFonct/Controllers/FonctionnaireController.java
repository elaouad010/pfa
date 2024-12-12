package projetPFA.gestionFonct.Controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.Services.FonctionnaireService;

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
