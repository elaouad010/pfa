package projetPFA.gestionFonct.ServiceTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.Formations.BesoinFormation;
import projetPFA.gestionFonct.Formations.FormRepo.FormRepository;
import projetPFA.gestionFonct.Formations.FormRepo.VoteRepo;
import projetPFA.gestionFonct.Formations.FormServ.FormService;
import projetPFA.gestionFonct.Formations.Vote;
import projetPFA.gestionFonct.Repositories.FonctionnaireRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@SpringBootTest
class FormationTest {

    @Mock
    private FonctionnaireRepository fonctionnaireRepository;

    @Mock
    private FormRepository formRepository;

    @Mock
    private VoteRepo voteRepository;

    @InjectMocks
    private FormService formService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFormationById() {
        Long formationId = 1L;
        BesoinFormation formation = new BesoinFormation();
        when(formRepository.findById(formationId)).thenReturn(Optional.of(formation));

        BesoinFormation result = formService.getFormationById(formationId);

        assertThat(result).isEqualTo(formation);
        verify(formRepository).findById(formationId);
    }

    @Test
    void testGetFormationByIdThrowsException() {
        Long formationId = 1L;
        when(formRepository.findById(formationId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> formService.getFormationById(formationId));

        assertThat(exception.getMessage()).isEqualTo("Formation not found with id: " + formationId);
    }

    @Test
    void testSaveOrUpdate() {
        BesoinFormation formation = new BesoinFormation();
        when(formRepository.save(formation)).thenReturn(formation);

        BesoinFormation result = formService.saveOrUpdate(formation);

        assertThat(result).isEqualTo(formation);
        verify(formRepository).save(formation);
    }

    @Test
    void testVoteForFormation() {
        Long formationId = 1L;
        BesoinFormation formation = new BesoinFormation();
        when(formRepository.findById(formationId)).thenReturn(Optional.of(formation));
        when(formRepository.save(formation)).thenReturn(formation);

        BesoinFormation result = formService.voteForFormation(formationId);

        assertThat(result).isEqualTo(formation);
        verify(formRepository).save(formation);
    }

    @Test
    void testGetAllFormationVotes() {
        BesoinFormation formation1 = new BesoinFormation();
        formation1.setDomaine("IT");
        formation1.setTotalVotes(5);

        BesoinFormation formation2 = new BesoinFormation();
        formation2.setDomaine("IT");
        formation2.setTotalVotes(3);

        List<BesoinFormation> formations = List.of(formation1, formation2);
        when(formRepository.findAll()).thenReturn(formations);

        List<BesoinFormation> result = formService.getAllFormationVotes();

        assertThat(result).hasSize(2);
        verify(formRepository).findAll();
    }

    @Test
    void testDeleteFormation() {
        Long formationId = 1L;

        formService.deleteFormation(formationId);

        verify(formRepository).deleteById(formationId);
    }

    @Test
    void testUpdateFormation() {
        Long formationId = 1L;
        BesoinFormation existingFormation = new BesoinFormation();
        BesoinFormation updatedDetails = new BesoinFormation();
        updatedDetails.setDomaine("New Domaine");

        when(formRepository.findById(formationId)).thenReturn(Optional.of(existingFormation));
        when(formRepository.save(existingFormation)).thenReturn(existingFormation);

        BesoinFormation result = formService.updateFormation(formationId, updatedDetails);

        assertThat(result.getDomaine()).isEqualTo("New Domaine");
        verify(formRepository).save(existingFormation);
    }

    @Test
    void testVoter() {
        String cin = "CIN123";
        Long formationId = 1L;
        Fonctionnaire fonctionnaire = new Fonctionnaire();
        BesoinFormation formation = new BesoinFormation();

        when(fonctionnaireRepository.findById(cin)).thenReturn(Optional.of(fonctionnaire));
        when(formRepository.findById(formationId)).thenReturn(Optional.of(formation));

        ResponseEntity<?> response = formService.voter(cin, formationId, true);

        assertThat(response.getBody()).isEqualTo("Vote enregistré avec succès");
        verify(formRepository).save(formation);
    }

    @Test
    void testVoteForFormationWithValidation() {
        String cin = "CIN123";
        Long formationId = 1L;
        Fonctionnaire fonctionnaire = new Fonctionnaire();
        BesoinFormation formation = new BesoinFormation();
        formation.setVotes(new ArrayList<>());

        when(fonctionnaireRepository.findById(cin)).thenReturn(Optional.of(fonctionnaire));
        when(formRepository.findById(formationId)).thenReturn(Optional.of(formation));
        when(formRepository.save(formation)).thenReturn(formation);

        BesoinFormation result = formService.voteForFormation(cin, formationId, true);

        assertThat(result.getVotes()).hasSize(1);
        verify(formRepository).save(formation);
    }

    @Test
    void testHasUserVoted() {
        String cin = "CIN123";
        Long formationId = 1L;
        BesoinFormation formation = new BesoinFormation();
        Vote vote = new Vote();
        vote.setFonctionnaireCin(cin);
        formation.setVotes(List.of(vote));

        when(formRepository.findById(formationId)).thenReturn(Optional.of(formation));

        boolean result = formService.hasUserVoted(cin, formationId);

        assertThat(result).isTrue();
    }

    @Test
    void testGetVotesByCin() {
        String cin = "CIN123";
        List<Vote> votes = List.of(new Vote(), new Vote());

        when(voteRepository.findByFonctionnaireCin(cin)).thenReturn(votes);

        List<Vote> result = formService.getVotesByCin(cin);

        assertThat(result).hasSize(2);
        verify(voteRepository).findByFonctionnaireCin(cin);
    }
    @Test
    void testGetFormationByInvalidId() {
        // Test behavior when an invalid ID is passed
        Long invalidFormationId = -1L;
        when(formRepository.findById(invalidFormationId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> formService.getFormationById(invalidFormationId));

        assertThat(exception.getMessage()).isEqualTo("Formation not found with id: " + invalidFormationId);
    }

    @Test
    void testDeleteNonExistingFormation() {
        // Test deletion of a formation that doesn't exist
        Long nonExistingFormationId = 99L;
        doThrow(new RuntimeException("Formation not found with id: " + nonExistingFormationId))
                .when(formRepository).deleteById(nonExistingFormationId);

        Exception exception = assertThrows(RuntimeException.class, () -> formService.deleteFormation(nonExistingFormationId));

        assertThat(exception.getMessage()).isEqualTo("Formation not found with id: " + nonExistingFormationId);
    }

    @Test
    void testUpdateNonExistingFormation() {
        // Test updating a formation that doesn't exist
        Long nonExistingFormationId = 99L;
        BesoinFormation updatedDetails = new BesoinFormation();
        updatedDetails.setDomaine("New Domain");

        when(formRepository.findById(nonExistingFormationId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> formService.updateFormation(nonExistingFormationId, updatedDetails));

        assertThat(exception.getMessage()).isEqualTo("Formation not found with id: " + nonExistingFormationId);
    }


    @Test
    void testGetAllFormationsEmptyList() {
        // Test behavior when no formations exist
        when(formRepository.findAll()).thenReturn(new ArrayList<>());

        List<BesoinFormation> result = formService.getAllFormationVotes();

        assertThat(result).isEmpty();
        verify(formRepository).findAll();
    }
 @Test
    void testUpdateFormationVotesRetain() {
        // Test updating a formation and retaining its votes
        Long formationId = 1L;
        BesoinFormation existingFormation = new BesoinFormation();
        BesoinFormation updatedDetails = new BesoinFormation();
        Vote vote = new Vote();
        existingFormation.setVotes(List.of(vote));
        updatedDetails.setDomaine("Updated Domain");

        when(formRepository.findById(formationId)).thenReturn(Optional.of(existingFormation));
        when(formRepository.save(existingFormation)).thenReturn(existingFormation);

        BesoinFormation result = formService.updateFormation(formationId, updatedDetails);

        assertThat(result.getVotes()).hasSize(1);
        assertThat(result.getDomaine()).isEqualTo("Updated Domain");
        verify(formRepository).save(existingFormation);
    }


    @Test
    void testVoteForFormationInvalidRepositorySave() {
        // Test case where saving the formation fails
        String cin = "CIN123";
        Long formationId = 1L;
        Fonctionnaire fonctionnaire = new Fonctionnaire();
        BesoinFormation formation = new BesoinFormation();
        formation.setVotes(new ArrayList<>());

        when(fonctionnaireRepository.findById(cin)).thenReturn(Optional.of(fonctionnaire));
        when(formRepository.findById(formationId)).thenReturn(Optional.of(formation));
        doThrow(new RuntimeException("Repository save error")).when(formRepository).save(formation);

        Exception exception = assertThrows(RuntimeException.class, () -> formService.voteForFormation(cin, formationId, true));

        assertThat(exception.getMessage()).isEqualTo("Repository save error");
    }

    @Test
    void testGetVotesByCinNoVotes() {
        // Test case where a user has not voted for any formation
        String cin = "CIN123";

        when(voteRepository.findByFonctionnaireCin(cin)).thenReturn(new ArrayList<>());

        List<Vote> result = formService.getVotesByCin(cin);

        assertThat(result).isEmpty();
        verify(voteRepository).findByFonctionnaireCin(cin);
    }

    @Test
    void testGetVotesByInvalidCin() {
        // Test behavior when an invalid CIN is provided
        String invalidCin = "INVALIDCIN";

        when(voteRepository.findByFonctionnaireCin(invalidCin)).thenReturn(new ArrayList<>());

        List<Vote> result = formService.getVotesByCin(invalidCin);

        assertThat(result).isEmpty();
        verify(voteRepository).findByFonctionnaireCin(invalidCin);
    }

    @Test
    void testUpdateFormationPartialUpdate() {
        // Test partial updates to a formation
        Long formationId = 1L;
        BesoinFormation existingFormation = new BesoinFormation();
        existingFormation.setDomaine("Initial Domain");
        existingFormation.setTotalVotes(10);

        BesoinFormation updatedDetails = new BesoinFormation();
        updatedDetails.setDomaine("Updated Domain"); // Only update the domain

        when(formRepository.findById(formationId)).thenReturn(Optional.of(existingFormation));
        when(formRepository.save(existingFormation)).thenReturn(existingFormation);

        BesoinFormation result = formService.updateFormation(formationId, updatedDetails);

        assertThat(result.getDomaine()).isEqualTo("Updated Domain");
        assertThat(result.getTotalVotes()).isEqualTo(10); // Ensure other fields remain unchanged
        verify(formRepository).save(existingFormation);
    }

    @Test
    void testDeleteFormationWithoutVotes() {
        // Test deleting a formation with no associated votes
        Long formationId = 1L;
        BesoinFormation formation = new BesoinFormation();
        formation.setVotes(new ArrayList<>());

        when(formRepository.findById(formationId)).thenReturn(Optional.of(formation));

        formService.deleteFormation(formationId);

        verify(formRepository).deleteById(formationId);
    }

    @Test
    void testGetAllFormationsWithVotes() {
        // Test retrieving all formations along with their votes
        BesoinFormation formation1 = new BesoinFormation();
        formation1.setDomaine("IT");
        formation1.setVotes(List.of(new Vote()));

        BesoinFormation formation2 = new BesoinFormation();
        formation2.setDomaine("Finance");
        formation2.setVotes(new ArrayList<>());

        List<BesoinFormation> formations = List.of(formation1, formation2);
        when(formRepository.findAll()).thenReturn(formations);

        List<BesoinFormation> result = formService.getAllFormationVotes();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getVotes()).hasSize(1);
        assertThat(result.get(1).getVotes()).isEmpty();
        verify(formRepository).findAll();
    }


}

