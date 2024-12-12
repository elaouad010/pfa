package projetPFA.gestionFonct.Formations.FormRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFA.gestionFonct.Formations.BesoinFormation;
import projetPFA.gestionFonct.Formations.Vote;

import java.util.List;

public interface  VoteRepo extends JpaRepository<Vote,Long> {
    List<Vote> findByFonctionnaireCin(String fonctionnaireCin);
    boolean existsByFonctionnaireCinAndFormation_Id(String fonctionnaireCin, Long formationId);
}
