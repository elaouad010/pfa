package projetPFA.gestionFonct.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetPFA.gestionFonct.test;

@Repository
public interface Testrepo extends JpaRepository<test,Long> {
}
