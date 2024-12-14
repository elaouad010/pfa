package projetPFA.gestionFonct.Models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import projetPFA.gestionFonct.Formations.BesoinFormation;
import projetPFA.gestionFonct.Formations.Vote;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class VoteTest {

    @Test
    public void testVoteConstructorAndGettersSetters() {
        // Creating a Vote object using the constructor
        Vote vote = new Vote();

        // Setting values using setters
        vote.setId(1L);
        vote.setFonctionnaireCin("ABC123");
        vote.setVote(true);

        // Testing the getter methods
        assertEquals(1L, vote.getId());
        assertEquals("ABC123", vote.getFonctionnaireCin());
        assertTrue(vote.isVote());

        // Modifying values using setters
        vote.setId(2L);
        vote.setFonctionnaireCin("XYZ789");
        vote.setVote(false);

        // Testing the updated values using getters
        assertEquals(2L, vote.getId());
        assertEquals("XYZ789", vote.getFonctionnaireCin());
        assertFalse(vote.isVote());
    }

    @Test
    public void testVoteWithConstructor() {
        // Creating a Vote object using the all-args constructor
        Vote vote = new Vote(1L, "ABC123", new BesoinFormation(), true);

        // Verifying the constructor values using getters
        assertEquals(1L, vote.getId());
        assertEquals("ABC123", vote.getFonctionnaireCin());
        assertTrue(vote.isVote());
    }
}
