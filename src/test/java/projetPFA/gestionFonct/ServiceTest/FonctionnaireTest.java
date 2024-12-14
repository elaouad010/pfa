package projetPFA.gestionFonct.ServiceTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import projetPFA.gestionFonct.Fonctionnaire;
import projetPFA.gestionFonct.Repositories.FonctionnaireRepository;
import projetPFA.gestionFonct.Services.FonctionnaireService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@SpringBootTest
class FonctionnaireTest {

    @Mock

    private FonctionnaireRepository fonctionnaireRepository;

    @InjectMocks
    private FonctionnaireService fonctionnaireService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAjouterFonctionnaire() {
        Fonctionnaire fonctionnaire = new Fonctionnaire();
        fonctionnaireService.ajouterFonctionnaire(fonctionnaire);

        ArgumentCaptor<Fonctionnaire> fonctionnaireCaptor = ArgumentCaptor.forClass(Fonctionnaire.class);
        verify(fonctionnaireRepository).save(fonctionnaireCaptor.capture());

        Fonctionnaire capturedFonctionnaire = fonctionnaireCaptor.getValue();
        assertThat(capturedFonctionnaire).isEqualTo(fonctionnaire);
    }

    @Test
    void testGetAllFonct() {
        List<Fonctionnaire> fonctionnaires = new ArrayList<>();
        fonctionnaires.add(new Fonctionnaire());
        fonctionnaires.add(new Fonctionnaire());

        when(fonctionnaireRepository.findAll()).thenReturn(fonctionnaires);

        List<Fonctionnaire> result = fonctionnaireService.getAllFonct();

        assertThat(result).hasSize(2);
        verify(fonctionnaireRepository, times(1)).findAll();
    }

    @Test
    void testUpdateFonct() {
        String cin = "CIN123";
        Fonctionnaire existingFonctionnaire = new Fonctionnaire();
        existingFonctionnaire.setNom("OldName");

        Fonctionnaire updatedFonctionnaire = new Fonctionnaire();
        updatedFonctionnaire.setNom("NewName");

        when(fonctionnaireRepository.findById(cin)).thenReturn(Optional.of(existingFonctionnaire));
        when(fonctionnaireRepository.save(any(Fonctionnaire.class))).thenReturn(existingFonctionnaire);

        Fonctionnaire result = fonctionnaireService.updateFonct(cin, updatedFonctionnaire);

        assertThat(result.getNom()).isEqualTo("NewName");
        verify(fonctionnaireRepository).save(existingFonctionnaire);
    }

    @Test
    void testDeleteFonct() {
        String cin = "CIN123";
        when(fonctionnaireRepository.existsById(cin)).thenReturn(true);

        fonctionnaireService.deleteFonct(cin);

        verify(fonctionnaireRepository, times(1)).deleteById(cin);
    }

    @Test
    void testDeleteFonctThrowsException() {
        String cin = "CIN123";
        when(fonctionnaireRepository.existsById(cin)).thenReturn(false);

        Exception exception = assertThrows(IllegalStateException.class, () -> fonctionnaireService.deleteFonct(cin));

        assertThat(exception.getMessage()).isEqualTo("NO INFOPERSO WITH THIS CIN : CIN123IS FOUND");
        verify(fonctionnaireRepository, never()).deleteById(cin);
    }

    @Test
    void testGetFonctById() {
        String cin = "CIN123";
        Fonctionnaire fonctionnaire = new Fonctionnaire();
        when(fonctionnaireRepository.findById(cin)).thenReturn(Optional.of(fonctionnaire));

        Fonctionnaire result = fonctionnaireService.getFonctById(cin);

        assertThat(result).isEqualTo(fonctionnaire);
        verify(fonctionnaireRepository).findById(cin);
    }

    @Test
    void testGetFonctByIdThrowsException() {
        String cin = "CIN123";
        when(fonctionnaireRepository.findById(cin)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> fonctionnaireService.getFonctById(cin));

        assertThat(exception.getMessage()).isEqualTo("NO INFOPERSO WITH THIS CIN : CIN123IS FOUND");
        verify(fonctionnaireRepository).findById(cin);
    }
}
