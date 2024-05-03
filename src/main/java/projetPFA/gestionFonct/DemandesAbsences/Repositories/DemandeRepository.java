package projetPFA.gestionFonct.DemandesAbsences.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetPFA.gestionFonct.DemandesAbsences.Demande_absence;
import projetPFA.gestionFonct.DemandesAbsences.statusdemande;
import projetPFA.gestionFonct.Fonctionnaire;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande_absence,Long > {
    List<Demande_absence> findByStatus(statusdemande status);

}