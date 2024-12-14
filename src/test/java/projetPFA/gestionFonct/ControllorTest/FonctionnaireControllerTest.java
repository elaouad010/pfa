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
import projetPFA.gestionFonct.Controllers.FonctionnaireController;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.Services.FonctionnaireService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
 class FonctionnaireControllerTest {

    @Mock
    private FonctionnaireService fonctionnaireService;

    @InjectMocks
    private FonctionnaireController fonctionnaireController;

    private Fonctionnaire fonctionnaire;

    @BeforeEach
    void setUp() {
        fonctionnaire = new Fonctionnaire();
        fonctionnaire.setCin("CIN12345");
        fonctionnaire.setNom("John");
        fonctionnaire.setPrenom("Doe");
        fonctionnaire.setSexe("M");
        fonctionnaire.setEmail("john.doe@example.com");
        fonctionnaire.setAdresse("123 Street, City");
        fonctionnaire.setNumeroTel("1234567890");
    }

    @Test
    void testAjouterFonctionnaire() {
        // Arrange
        doNothing().when(fonctionnaireService).ajouterFonctionnaire(fonctionnaire);

        // Act
        fonctionnaireController.ajouterFonctionnaire(fonctionnaire);

        // Assert
        verify(fonctionnaireService, times(1)).ajouterFonctionnaire(fonctionnaire);
    }

    @Test
    void testGetAllFonct() {
        // Arrange
        List<Fonctionnaire> fonctionnaires = Arrays.asList(fonctionnaire);
        when(fonctionnaireService.getAllFonct()).thenReturn(fonctionnaires);

        // Act
        List<Fonctionnaire> result = fonctionnaireController.getAllFonct();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("John", result.get(0).getNom());
    }

    @Test
    void testGetFonctById() {
        // Arrange
        when(fonctionnaireService.getFonctById("CIN12345")).thenReturn(fonctionnaire);

        // Act
        Fonctionnaire result = fonctionnaireController.getFonctById("CIN12345");

        // Assert
        assertNotNull(result);
        assertEquals("CIN12345", result.getCin());
    }

    @Test
    void testUpdateFonct() {
        // Arrange
        Fonctionnaire updatedFonctionnaire = new Fonctionnaire();
        updatedFonctionnaire.setCin("CIN12345");
        updatedFonctionnaire.setNom("Jane");
        updatedFonctionnaire.setPrenom("Doe");
        updatedFonctionnaire.setSexe("F");

        when(fonctionnaireService.updateFonct("CIN12345", updatedFonctionnaire)).thenReturn(updatedFonctionnaire);

        // Act
        ResponseEntity<Fonctionnaire> response = fonctionnaireController.updateFonct("CIN12345", updatedFonctionnaire);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jane", response.getBody().getNom());
    }

    @Test
    void testDeleteFonct() {
        // Arrange
        doNothing().when(fonctionnaireService).deleteFonct("CIN12345");

        // Act
        fonctionnaireController.deleteFonct("CIN12345");

        // Assert
        verify(fonctionnaireService, times(1)).deleteFonct("CIN12345");
    }
}
