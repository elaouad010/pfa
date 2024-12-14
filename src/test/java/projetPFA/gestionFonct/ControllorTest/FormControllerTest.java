package projetPFA.gestionFonct.ControllorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import projetPFA.gestionFonct.Formations.BesoinFormation;
import projetPFA.gestionFonct.Formations.FormControll.FormController;
import projetPFA.gestionFonct.Formations.FormServ.FormService;
import projetPFA.gestionFonct.Formations.Vote;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
 class FormControllerTest {

    @Mock
    private FormService formService;

    @InjectMocks
    private FormController formController;

    private BesoinFormation formation;
    private Vote vote;

    @BeforeEach
    void setUp() {
        // Initialisation d'une formation pour les tests
        formation = new BesoinFormation();
        formation.setId(1L);
        formation.setDomaine("IT");
        formation.setThemeprop("Java");
        formation.setTotalVotes(0);
        formation.setTotalVotesAcc(0);
        formation.setStructurep("Structure A");

        // Initialisation d'un vote pour les tests
        vote = new Vote();
        vote.setFonctionnaireCin("CIN12345");
        vote.setFormation(formation);
        vote.setVote(true);
    }

    @Test
    void testAddFormation() {
        when(formService.saveOrUpdate(any(BesoinFormation.class))).thenReturn(formation);

        BesoinFormation createdFormation = formController.addFormation(formation);

        assertNotNull(createdFormation);
        assertEquals("IT", createdFormation.getDomaine());
    }

    @Test
    void testUpdateFormation() {
        BesoinFormation updatedFormation = new BesoinFormation();
        updatedFormation.setId(1L);
        updatedFormation.setDomaine("IT");
        updatedFormation.setThemeprop("Spring");

        when(formService.updateFormation(eq(1L), any(BesoinFormation.class))).thenReturn(updatedFormation);

        BesoinFormation result = formController.updateFormation(1L, updatedFormation);

        assertNotNull(result);
        assertEquals("Spring", result.getThemeprop());
    }

    @Test
    void testGetFormationById() {
        when(formService.getFormationById(1L)).thenReturn(formation);

        BesoinFormation result = formController.getFormationById(1L);

        assertNotNull(result);
        assertEquals("IT", result.getDomaine());
    }

    @Test
    void testGetAllFormationVotes() {
        List<BesoinFormation> formations = new ArrayList<>();
        formations.add(formation);

        when(formService.getAllFormationVotes()).thenReturn(formations);

        List<BesoinFormation> result = formController.getAllFormationVotes();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("IT", result.get(0).getDomaine());
    }

    @Test
    void testDeleteFormation() {
        doNothing().when(formService).deleteFormation(1L);

        formController.deleteFormation(1L);

        verify(formService, times(1)).deleteFormation(1L);
    }

    @Test
    void testCheckIfUserVoted() {
        when(formService.hasUserVoted("CIN12345", 1L)).thenReturn(true);

        ResponseEntity<?> response = formController.checkIfUserVoted("CIN12345", 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testGetUserVotes() {
        List<Vote> votes = new ArrayList<>();
        votes.add(vote);

        when(formService.getVotesByCin("CIN12345")).thenReturn(votes);

        ResponseEntity<List<Vote>> response = formController.getUserVotes("CIN12345");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }
}
