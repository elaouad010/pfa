package projetPFA.gestionFonct.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetPFA.gestionFonct.Fonctionnaire;

@Repository
public interface FonctionnaireRepository extends JpaRepository<Fonctionnaire,String > {
}
