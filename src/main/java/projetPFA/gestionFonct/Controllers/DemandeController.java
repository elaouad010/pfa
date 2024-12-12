package projetPFA.gestionFonct.Controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFA.gestionFonct.Demande_absence;
import projetPFA.gestionFonct.Services.Demandeservice;

import java.util.List;

@Data
@CrossOrigin("http://localhost:3000")
@RestController
public class DemandeController {

    @Autowired
    private Demandeservice demandeservice;

    @Autowired
    public DemandeController (Demandeservice demandeservice){
        this.demandeservice=demandeservice;
    }

    @PostMapping(path = "api/demandes/add")
    public ResponseEntity<?> ajouterDemande(@RequestBody Demande_absence demande) {
        String cin = demande.getToncin();
        try {
            Demande_absence newDemande = demandeservice.saveDemandeForFonctionnaire(demande, cin);
            return new ResponseEntity<>(newDemande, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="api/demandes/getAll")
    public ResponseEntity<List<Demande_absence>> afficherTousDemandes() {
        List<Demande_absence> demandes = demandeservice.affichertousdemande();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @DeleteMapping(path="api/demandes/delete/{code}")
    public ResponseEntity<String> supprimerDemande(@PathVariable Long code) {
        demandeservice.deletedemande(code);
        return new ResponseEntity<>("Demande avec le code " + code + " supprimée avec succès", HttpStatus.OK);
    }

    @GetMapping(path="api/demandes/getByid/{code}")
    public ResponseEntity<Demande_absence> afficherDemandeParCode(@PathVariable Long code) {
        Demande_absence demande = demandeservice.getdemandeBycode(code);
        return new ResponseEntity<>(demande, HttpStatus.OK);
    }

    @PutMapping(path="api/demandes/update/{code}")
    public ResponseEntity<Demande_absence> modifierDemande(@PathVariable Long code, @RequestBody Demande_absence updatedDemande) {
        Demande_absence updated = demandeservice.updateDemande(code, updatedDemande);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countdemande() {
        long count = demandeservice.countdemandes();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/api/demandes/cin/{cin}")
    public ResponseEntity<List<Demande_absence>> getDemandesByCin(@PathVariable String cin) {
        List<Demande_absence> demandes = demandeservice.getDemandesByCin(cin);
        return ResponseEntity.ok(demandes);
    }
    @GetMapping(path="api/demandes/acceptedbycin/{cin}")
    public ResponseEntity<List<Demande_absence>> getAcceptedDemandesByCin(@PathVariable String cin) {
        List<Demande_absence> demandes = demandeservice.getAcceptedDemandesByCin(cin);
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }
    @GetMapping(path="api/demandes/refusedbycin/{cin}")
    public ResponseEntity<List<Demande_absence>> getRefusedDemandesByCin(@PathVariable String cin) {
        List<Demande_absence> demandes = demandeservice.getRefusedDemandesByCin(cin);
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }
    @GetMapping("/{code}")
    public ResponseEntity<Demande_absence> getDemandByCode(@PathVariable Long code) {
        Demande_absence demand = demandeservice.getDemandByCode(code);
        if (demand != null) {
            return new ResponseEntity<>(demand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }}
    @PostMapping(path="api/demandes/accepted/{code}")
    public ResponseEntity<Demande_absence> accepterDemande(@PathVariable Long code) {
        return demandeservice.accepterDemande(code);
    }

    @PostMapping(path="api/demandes/refused/{code}")
    public ResponseEntity<Demande_absence> refuserDemande(@PathVariable Long code) {
        return demandeservice.refuserDemande(code);
    }
    @GetMapping(path="api/demandes/getDaccepted")
    public List<Demande_absence> getDemandesAcceptees() {
        return demandeservice.getDemandesAcceptees();
    }

    @GetMapping(path="api/demandes/getDrefused")
    public List<Demande_absence> getDemandesRefusees() {
        return demandeservice.getDemandesRefusees();
    }



}