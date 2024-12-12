package projetPFA.gestionFonct.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetPFA.gestionFonct.Demande_absence;
import projetPFA.gestionFonct.statusdemande;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande_absence,Long > {
    List<Demande_absence> findByStatus(statusdemande status);
    List<Demande_absence> findByToncin(String toncin);
    List<Demande_absence> findByToncinAndStatus(String toncin, statusdemande status);


}