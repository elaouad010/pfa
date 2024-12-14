package projetPFA.gestionFonct.Models;

import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import projetPFA.gestionFonct.Formations.BesoinFormation;
import projetPFA.gestionFonct.Formations.FormServ.FormService;
import projetPFA.gestionFonct.Formations.FormRepo.FormRepository;
import projetPFA.gestionFonct.Formations.FormRepo.VoteRepo;
import projetPFA.gestionFonct.Repositories.FonctionnaireRepository;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.Formations.Vote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)  // Use this annotation to initialize mocks properly
class BesoinFormationTest {

    @Mock
    private FormRepository formRepository;

    @Mock
    private FonctionnaireRepository fonctionnaireRepository;

    @Mock
    private VoteRepo voteRepository;

    @InjectMocks
    private FormService formService;

    @Test
    public void testSaveBesoinFormation() {
        BesoinFormation besoinFormation = new BesoinFormation();
        besoinFormation.setDomaine("Technology");
        besoinFormation.setTotalVotes(10);
        besoinFormation.setStructurep("Tech Inc.");

        Mockito.when(formRepository.save(besoinFormation)).thenReturn(besoinFormation);

        BesoinFormation savedBesoinFormation = formService.saveOrUpdate(besoinFormation);

        assertNotNull(savedBesoinFormation);
        assertEquals("Technology", savedBesoinFormation.getDomaine());
        assertEquals(10, savedBesoinFormation.getTotalVotes());
        Mockito.verify(formRepository, Mockito.times(1)).save(besoinFormation);
    }
/*
    @Test
    public void testGetFormationByIdNotFound() {
        Mockito.when(formRepository.findById(999L)).thenReturn(Optional.empty());

        BesoinFormation foundFormation = formService.getFormationById(999L);

        assertNull(foundFormation, "Formation should be null when not found.");
    }
*/
    @Test
    public void testVoteForFormation() {
        BesoinFormation formation = new BesoinFormation();
        formation.setId(1L);
        formation.setDomaine("Mathematics");
        formation.setTotalVotes(0);
        formation.setVotes(new ArrayList<>());

        String fonctionnaireCin = "ABC123";

        Mockito.when(formRepository.findById(1L)).thenReturn(Optional.of(formation));
        Mockito.when(fonctionnaireRepository.findById(fonctionnaireCin)).thenReturn(Optional.of(new Fonctionnaire()));

        BesoinFormation updatedFormation = formService.voteForFormation(fonctionnaireCin, 1L, true);

        assertNotNull(updatedFormation);
        assertEquals(1, updatedFormation.getTotalVotes());
        Mockito.verify(formRepository, Mockito.times(1)).save(updatedFormation);
    }

    @Test
    public void testVoteForFormationAlreadyVoted() {
        BesoinFormation formation = new BesoinFormation();
        formation.setId(1L);
        formation.setDomaine("Technology");
        formation.setTotalVotes(1);
        formation.setVotes(new ArrayList<>());
        Vote existingVote = new Vote();
        existingVote.setFonctionnaireCin("ABC123");
        formation.getVotes().add(existingVote);

        String fonctionnaireCin = "ABC123";

        Mockito.when(formRepository.findById(1L)).thenReturn(Optional.of(formation));
        Mockito.when(fonctionnaireRepository.findById(fonctionnaireCin)).thenReturn(Optional.of(new Fonctionnaire()));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            formService.voteForFormation(fonctionnaireCin, 1L, true);
        });

        assertEquals("Ce fonctionnaire a déjà voté pour cette formation.", exception.getMessage());
    }

    @Test
    public void testHasUserVoted() {
        BesoinFormation formation = new BesoinFormation();
        formation.setId(1L);
        formation.setVotes(new ArrayList<>());
        Vote existingVote = new Vote();
        existingVote.setFonctionnaireCin("ABC123");
        formation.getVotes().add(existingVote);

        Mockito.when(formRepository.findById(1L)).thenReturn(Optional.of(formation));

        boolean hasVoted = formService.hasUserVoted("ABC123", 1L);

        assertTrue(hasVoted);
    }
/*
    @Test
    public void testVoteForFormationInvalidFonctionnaire() {
        BesoinFormation formation = new BesoinFormation();
        formation.setId(1L);
        formation.setDomaine("Science");
        formation.setTotalVotes(0);

        Mockito.when(formRepository.findById(1L)).thenReturn(Optional.of(formation));

        String fonctionnaireCin = "XYZ999";
        Mockito.when(fonctionnaireRepository.findById(fonctionnaireCin)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            formService.voteForFormation(fonctionnaireCin, 1L, true);
        });

        assertEquals("NO fonctionnaire WITH THIS CIN : XYZ999 IS FOUND", exception.getMessage());
    }*/
@Test
public void testVoteForFormationNoVotesYet() {
    BesoinFormation formation = new BesoinFormation();
    formation.setId(1L);
    formation.setDomaine("Engineering");
    formation.setTotalVotes(0);
    formation.setVotes(new ArrayList<>());

    String fonctionnaireCin = "LMN789";

    Mockito.when(formRepository.findById(1L)).thenReturn(Optional.of(formation));
    Mockito.when(fonctionnaireRepository.findById(fonctionnaireCin)).thenReturn(Optional.of(new Fonctionnaire()));

    BesoinFormation updatedFormation = formService.voteForFormation(fonctionnaireCin, 1L, true);

    assertNotNull(updatedFormation);
    assertEquals(1, updatedFormation.getTotalVotes());  // Check if the vote count is updated correctly
    Mockito.verify(formRepository, Mockito.times(1)).save(updatedFormation);
}



}
