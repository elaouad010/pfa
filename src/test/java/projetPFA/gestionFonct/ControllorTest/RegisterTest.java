package projetPFA.gestionFonct.ControllorTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import projetPFA.gestionFonct.Login.Auth.RegisterRequest;
import projetPFA.gestionFonct.Login.User.Role;
@SpringBootTest
class RegisterTest {

    @Test
    void testConstructorAndGetters() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest("12345", "John", "Doe", "john.doe@example.com", "password123", Role.USER);

        // When & Then
        assertEquals("12345", registerRequest.getCin());
        assertEquals("John", registerRequest.getFirstname());
        assertEquals("Doe", registerRequest.getLastname());
        assertEquals("john.doe@example.com", registerRequest.getEmail());
        assertEquals("password123", registerRequest.getPassword());
        assertEquals(Role.USER, registerRequest.getRole());
    }

    @Test
    void testBuilder() {
        // Given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .cin("67890")
                .firstname("Jane")
                .lastname("Smith")
                .email("jane.smith@example.com")
                .password("securePassword")
                .role(Role.ADMIN)
                .build();

        // When & Then
        assertEquals("67890", registerRequest.getCin());
        assertEquals("Jane", registerRequest.getFirstname());
        assertEquals("Smith", registerRequest.getLastname());
        assertEquals("jane.smith@example.com", registerRequest.getEmail());
        assertEquals("securePassword", registerRequest.getPassword());
        assertEquals(Role.ADMIN, registerRequest.getRole());
    }

    @Test
    void testNoArgsConstructor() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest();

        // When & Then
        assertNull(registerRequest.getCin());
        assertNull(registerRequest.getFirstname());
        assertNull(registerRequest.getLastname());
        assertNull(registerRequest.getEmail());
        assertNull(registerRequest.getPassword());
        assertNull(registerRequest.getRole());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest("54321", "Alice", "Johnson", "alice.johnson@example" +
                ".com", "password321", Role.ADMIN);

        // When & Then
        assertEquals("54321", registerRequest.getCin());
        assertEquals("Alice", registerRequest.getFirstname());
        assertEquals("Johnson", registerRequest.getLastname());
        assertEquals("alice.johnson@example.com", registerRequest.getEmail());
        assertEquals("password321", registerRequest.getPassword());
        assertEquals(Role.ADMIN, registerRequest.getRole());
    }
}
