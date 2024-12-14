package projetPFA.gestionFonct.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import projetPFA.gestionFonct.info.embadddedinfo.infoPrev.InfoPrevoyanceSociale;
import projetPFA.gestionFonct.info.embadddedinfo.infoPrev.OrganismePrevoyanceSociale;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class InfoPrevoyanceSocialeTest {

    private InfoPrevoyanceSociale infoPrevoyanceSociale;

    @BeforeEach
    void setUp() {
        // Initialize InfoPrevoyanceSociale before each test
        infoPrevoyanceSociale = new InfoPrevoyanceSociale();
    }

    @Test
    void testDefaultConstructor() {
        // Assert that the object is not null
        assertNotNull(infoPrevoyanceSociale);

        // Assert the default values (null for non-primitive fields)
        assertNull(infoPrevoyanceSociale.getNumAffiliationCNOPS());
        assertNull(infoPrevoyanceSociale.getNumImmatriculationCNOPS());
        assertNull(infoPrevoyanceSociale.getDateAffiliationCNOPS());
        assertNull(infoPrevoyanceSociale.getOrganismePrevoyanceSociale());
    }

    @Test
    void testParameterizedConstructor() {
        // Create a new instance using the all-args constructor
        OrganismePrevoyanceSociale organisme = OrganismePrevoyanceSociale.MutuelleGeneral;
        String numAffiliation = "12345";
        String numImmatriculation = "67890";
        Date dateAffiliation = new Date();

        infoPrevoyanceSociale = new InfoPrevoyanceSociale(1L, organisme, numAffiliation, numImmatriculation, dateAffiliation);

        // Assert that the object was correctly initialized
        assertEquals(1L, infoPrevoyanceSociale.getId());
        assertEquals(organisme, infoPrevoyanceSociale.getOrganismePrevoyanceSociale());
        assertEquals(numAffiliation, infoPrevoyanceSociale.getNumAffiliationCNOPS());
        assertEquals(numImmatriculation, infoPrevoyanceSociale.getNumImmatriculationCNOPS());
        assertEquals(dateAffiliation, infoPrevoyanceSociale.getDateAffiliationCNOPS());
    }

    @Test
    void testSettersAndGetters() {
        // Test setting and getting values using setter and getter methods
        OrganismePrevoyanceSociale organisme = OrganismePrevoyanceSociale.OMFAM;
        String numAffiliation = "123456";
        String numImmatriculation = "7891011";
        Date dateAffiliation = new Date();

        infoPrevoyanceSociale.setOrganismePrevoyanceSociale(organisme);
        infoPrevoyanceSociale.setNumAffiliationCNOPS(numAffiliation);
        infoPrevoyanceSociale.setNumImmatriculationCNOPS(numImmatriculation);
        infoPrevoyanceSociale.setDateAffiliationCNOPS(dateAffiliation);

        // Assert that the values have been correctly set
        assertEquals(organisme, infoPrevoyanceSociale.getOrganismePrevoyanceSociale());
        assertEquals(numAffiliation, infoPrevoyanceSociale.getNumAffiliationCNOPS());
        assertEquals(numImmatriculation, infoPrevoyanceSociale.getNumImmatriculationCNOPS());
        assertEquals(dateAffiliation, infoPrevoyanceSociale.getDateAffiliationCNOPS());
    }

    @Test
    void testSetNullValues() {
        // Test setting null values
        infoPrevoyanceSociale.setOrganismePrevoyanceSociale(null);
        infoPrevoyanceSociale.setNumAffiliationCNOPS(null);
        infoPrevoyanceSociale.setNumImmatriculationCNOPS(null);
        infoPrevoyanceSociale.setDateAffiliationCNOPS(null);

        // Assert that null values are correctly set
        assertNull(infoPrevoyanceSociale.getOrganismePrevoyanceSociale());
        assertNull(infoPrevoyanceSociale.getNumAffiliationCNOPS());
        assertNull(infoPrevoyanceSociale.getNumImmatriculationCNOPS());
        assertNull(infoPrevoyanceSociale.getDateAffiliationCNOPS());
    }

    @Test
    void testEnumValues() {
        // Assert that the enum values are valid and accessible
        assertEquals(OrganismePrevoyanceSociale.MutuelleGeneral, OrganismePrevoyanceSociale.valueOf("MutuelleGeneral"));
        assertEquals(OrganismePrevoyanceSociale.OMFAM, OrganismePrevoyanceSociale.valueOf("OMFAM"));
        assertEquals(OrganismePrevoyanceSociale.MGAP, OrganismePrevoyanceSociale.valueOf("MGAP"));
    }

    @Test
    void testToStringMethod() {
        // Test the toString method output for the InfoPrevoyanceSociale object
        infoPrevoyanceSociale.setOrganismePrevoyanceSociale(OrganismePrevoyanceSociale.MGAP);
        infoPrevoyanceSociale.setNumAffiliationCNOPS("112233");
        infoPrevoyanceSociale.setNumImmatriculationCNOPS("445566");
        infoPrevoyanceSociale.setDateAffiliationCNOPS(new Date());

        String expectedString = "InfoPrevoyanceSociale(id=null, organismePrevoyanceSociale=MGAP, numAffiliationCNOPS=112233, numImmatriculationCNOPS=445566, dateAffiliationCNOPS=" + infoPrevoyanceSociale.getDateAffiliationCNOPS() + ")";
        assertEquals(expectedString, infoPrevoyanceSociale.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        // Test the equals and hashCode methods
        InfoPrevoyanceSociale anotherInfo = new InfoPrevoyanceSociale();
        anotherInfo.setOrganismePrevoyanceSociale(OrganismePrevoyanceSociale.MutuelleGeneral);
        anotherInfo.setNumAffiliationCNOPS("12345");

        // Assert that objects with the same properties are equal
        infoPrevoyanceSociale.setOrganismePrevoyanceSociale(OrganismePrevoyanceSociale.MutuelleGeneral);
        infoPrevoyanceSociale.setNumAffiliationCNOPS("12345");

        assertEquals(infoPrevoyanceSociale, anotherInfo);
        assertEquals(infoPrevoyanceSociale.hashCode(), anotherInfo.hashCode());
    }





        @Test
        void testIdAssignment() {
            // Test if ID is assigned correctly when setting the value manually
            infoPrevoyanceSociale.setId(10L);
            assertEquals(10L, infoPrevoyanceSociale.getId());
        }

        @Test
        void testDateAffiliationCNOPSFormat() {
            // Test if the date format is correctly handled by the setter
            Date dateAffiliation = new Date(2020 - 1900, 10, 25); // Date(2020, 10, 25) is used to simulate a date
            infoPrevoyanceSociale.setDateAffiliationCNOPS(dateAffiliation);
            assertNotNull(infoPrevoyanceSociale.getDateAffiliationCNOPS());
            assertEquals(2020 - 1900, infoPrevoyanceSociale.getDateAffiliationCNOPS().getYear());
            assertEquals(10, infoPrevoyanceSociale.getDateAffiliationCNOPS().getMonth());
            assertEquals(25, infoPrevoyanceSociale.getDateAffiliationCNOPS().getDate());
        }

        @Test
        void testOrganismePrevoyanceSocialeEnumNull() {
            // Test if the enum can handle null values
            infoPrevoyanceSociale.setOrganismePrevoyanceSociale(null);
            assertNull(infoPrevoyanceSociale.getOrganismePrevoyanceSociale());
        }

        @Test
        void testUpdateNumAffiliationCNOPS() {
            // Test updating the numAffiliationCNOPS
            String newNumAffiliation = "987654";
            infoPrevoyanceSociale.setNumAffiliationCNOPS("123456");
            infoPrevoyanceSociale.setNumAffiliationCNOPS(newNumAffiliation);
            assertEquals(newNumAffiliation, infoPrevoyanceSociale.getNumAffiliationCNOPS());
        }

        @Test
        void testUpdateNumImmatriculationCNOPS() {
            // Test updating the numImmatriculationCNOPS
            String newNumImmatriculation = "321654";
            infoPrevoyanceSociale.setNumImmatriculationCNOPS("654321");
            infoPrevoyanceSociale.setNumImmatriculationCNOPS(newNumImmatriculation);
            assertEquals(newNumImmatriculation, infoPrevoyanceSociale.getNumImmatriculationCNOPS());
        }

        @Test
        void testEqualsWithNullFields() {
            // Test equals when some fields are null
            InfoPrevoyanceSociale anotherInfo = new InfoPrevoyanceSociale();
            anotherInfo.setOrganismePrevoyanceSociale(null);
            anotherInfo.setNumAffiliationCNOPS(null);

            infoPrevoyanceSociale.setOrganismePrevoyanceSociale(null);
            infoPrevoyanceSociale.setNumAffiliationCNOPS(null);

            assertEquals(infoPrevoyanceSociale, anotherInfo);
        }

        @Test
        void testInfoPrevoyanceSocialeNotEqualToNull() {
            // Test equals when comparing to null
            assertNotEquals(infoPrevoyanceSociale, null);
        }

        @Test
        void testInfoPrevoyanceSocialeNotEqualToDifferentClass() {
            // Test equals when comparing to an object of a different class
            assertNotEquals(infoPrevoyanceSociale, new Object());
        }

        @Test
        void testValidEnumValue() {
            // Test that a valid enum value is properly accepted
            OrganismePrevoyanceSociale organisme = OrganismePrevoyanceSociale.MGAP;
            infoPrevoyanceSociale.setOrganismePrevoyanceSociale(organisme);
            assertEquals(organisme, infoPrevoyanceSociale.getOrganismePrevoyanceSociale());
        }

        @Test
        void testInvalidEnumValue() {
            // Test if invalid enum values can be handled (shouldn't be possible through the setter, but just in case)
            assertThrows(IllegalArgumentException.class, () -> {
                OrganismePrevoyanceSociale organisme = OrganismePrevoyanceSociale.valueOf("InvalidEnum");
            });
        }

        @Test
        void testSetNumImmatriculationCNOPSToEmpty() {
            // Test setting numImmatriculationCNOPS to an empty string
            infoPrevoyanceSociale.setNumImmatriculationCNOPS("");
            assertEquals("", infoPrevoyanceSociale.getNumImmatriculationCNOPS());
        }

        @Test
        void testSetNumAffiliationCNOPSToEmpty() {
            // Test setting numAffiliationCNOPS to an empty string
            infoPrevoyanceSociale.setNumAffiliationCNOPS("");
            assertEquals("", infoPrevoyanceSociale.getNumAffiliationCNOPS());
        }

        @Test
        void testSetDateAffiliationCNOPSToFutureDate() {
            // Test setting a future date for affiliation
            Date futureDate = new Date(System.currentTimeMillis() + 10000000000L); // A future date
            infoPrevoyanceSociale.setDateAffiliationCNOPS(futureDate);
            assertEquals(futureDate, infoPrevoyanceSociale.getDateAffiliationCNOPS());
        }
    }





