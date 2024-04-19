package projetPFA.gestionFonct.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projetPFA.gestionFonct.Services.Testservice;
import projetPFA.gestionFonct.test;

@RestController
@RequestMapping(path="api/test")
public class testcontroller {
    private Testservice testservice;
    @Autowired
    public testcontroller(Testservice testservice){
        this.testservice=testservice;

        }

    @PostMapping
    public void ajoutertset(@RequestBody test test) {
        testservice.ajoutertest(test);
    }
}
