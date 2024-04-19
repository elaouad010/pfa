package projetPFA.gestionFonct.Services;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.Repositories.FonctionnaireRepository;
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
import java.util.Objects;

@Data
@Service
public class FonctionnaireService {
    private FonctionnaireRepository fonctionnaireRepository;
    @Autowired
    public FonctionnaireService(FonctionnaireRepository fonctionnaireRepository){
        this.fonctionnaireRepository=fonctionnaireRepository;
    }
    public void ajouterFonctionnaire(Fonctionnaire fonctionnaire) {

        fonctionnaireRepository.save(fonctionnaire);
    }

    public List<Fonctionnaire> getAllFonct() {
        return fonctionnaireRepository.findAll();
    }
@Transactional
    public void updateFonct(String cin, Fonctionnaire fonctionnaire) {
    Fonctionnaire fonct=fonctionnaireRepository.findById(cin).orElseThrow(()-> new IllegalStateException("NO INFOPERSO WITH THIS CIN : "+cin+"IS FOUND"));
    if(fonctionnaire.getNom()!= null && fonctionnaire.getNom().length()>0 && !Objects.equals(fonct.getNom(),fonctionnaire.getNom()))
    fonct.setNom(fonctionnaire.getNom());
    if(fonctionnaire.getPrenom()!= null && fonctionnaire.getPrenom().length()>0 && !Objects.equals(fonct.getPrenom(),fonctionnaire.getPrenom()))
    fonct.setPrenom(fonctionnaire.getPrenom());
    if(fonctionnaire.getSexe()!= null && fonctionnaire.getSexe().length()>0 && !Objects.equals(fonct.getSexe(),fonctionnaire.getSexe()))
    fonct.setSexe(fonctionnaire.getSexe());
    if(fonctionnaire.getPrenom()!= null && fonctionnaire.getPrenom().length()>0 && !Objects.equals(fonct.getPrenom(),fonctionnaire.getPrenom()))
    fonct.setDateNaissance(fonctionnaire.getDateNaissance());
    if(fonctionnaire.getLieuNaissance()!= null && fonctionnaire.getLieuNaissance().length()>0 && !Objects.equals(fonct.getLieuNaissance(),fonctionnaire.getLieuNaissance()))
    fonct.setLieuNaissance(fonctionnaire.getLieuNaissance());
    if(fonctionnaire.getAdresse()!= null && fonctionnaire.getAdresse().length()>0 && !Objects.equals(fonct.getAdresse(),fonctionnaire.getAdresse()))
    fonct.setAdresse(fonctionnaire.getAdresse());
    if(fonctionnaire.getNumeroTel()!= null && fonctionnaire.getNumeroTel().length()>0 && !Objects.equals(fonct.getNumeroTel(),fonctionnaire.getNumeroTel()))
    fonct.setNumeroTel(fonctionnaire.getNumeroTel());
    if(fonctionnaire.getEmail()!= null && fonctionnaire.getEmail().length()>0 && !Objects.equals(fonct.getEmail(),fonctionnaire.getEmail()))
    fonct.setEmail(fonctionnaire.getEmail());
    if(fonctionnaire.getInfoFamiliales()!= null)
    fonct.setInfoFamiliales(fonctionnaire.getInfoFamiliales());
    if(fonctionnaire.getInfoAdministratives()!= null )
    fonct.setInfoAdministratives(fonctionnaire.getInfoAdministratives());
    if(fonctionnaire.getInfoPrevoyanceSociale()!= null )
    fonct.setInfoPrevoyanceSociale(fonctionnaire.getInfoPrevoyanceSociale());
    if(fonctionnaire.getOrganismesSociales()!= null )
    fonct.setOrganismesSociales(fonctionnaire.getOrganismesSociales());
    if(fonctionnaire.getInfoRetraite()!= null )
    fonct.setInfoRetraite(fonctionnaire.getInfoRetraite());
    if(fonctionnaire.getInfoAssurance()!= null)
    fonct.setInfoAssurance(fonctionnaire.getInfoAssurance());
    if(fonctionnaire.getNotations()!= null )
    fonct.setNotations(fonctionnaire.getNotations());
    if(fonctionnaire.getDiplomes()!= null )
    fonct.setDiplomes(fonctionnaire.getDiplomes());
    if(fonctionnaire.getMouvements()!= null )
    fonct.setMouvements(fonctionnaire.getMouvements());
    if(fonctionnaire.getAffectations()!= null )
    fonct.setAffectations(fonctionnaire.getAffectations());
    if(fonctionnaire.getSanctions()!= null )
    fonct.setSanctions(fonctionnaire.getSanctions());
    if(fonctionnaire.getDocumentsPiecesJointes()!= null )
    fonct.setDocumentsPiecesJointes(fonctionnaire.getDocumentsPiecesJointes());
}

    public void deleteFonct(String cin) {
        boolean exists=fonctionnaireRepository.existsById(cin);
        if (!exists) {
            throw new IllegalStateException("NO INFOPERSO WITH THIS CIN : " + cin + "IS FOUND");
        }
        else fonctionnaireRepository.deleteById(cin);

    }
}
