package projetPFA.gestionFonct.Formations.FormRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetPFA.gestionFonct.Formations.BesoinFormation;
import projetPFA.gestionFonct.Formations.Vote;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<BesoinFormation,Long> {



}
