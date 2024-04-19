package projetPFA.gestionFonct.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFA.gestionFonct.Repositories.Testrepo;
import projetPFA.gestionFonct.test;

@Service
public class Testservice {
    private Testrepo testrepo;

    @Autowired
    public Testservice(Testrepo testrepo){
        this.testrepo=testrepo;

    }
    public void ajoutertest(test test) {

        testrepo.save(test);
    }
}
