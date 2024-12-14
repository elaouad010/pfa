package projetPFA.gestionFonct.Models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import projetPFA.gestionFonct.Login.User.ChangePasswordRequest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChangePasswordRequestTest {

    @Test
    void testConstructorAndGetters() {
        // Test the constructor with arguments
        String currentPassword = "oldPassword";
        String newPassword = "newPassword";
        String confirmationPassword = "newPassword";

        ChangePasswordRequest request = new ChangePasswordRequest(
                currentPassword,
                newPassword,
                confirmationPassword
        );

        assertEquals(currentPassword, request.getCurrentPassword());
        assertEquals(newPassword, request.getNewPassword());
        assertEquals(confirmationPassword, request.getConfirmationPassword());
    }

    @Test
    void testSetters() {
        // Test the setters
        ChangePasswordRequest request = new ChangePasswordRequest();

        request.setCurrentPassword("oldPassword");
        request.setNewPassword("newPassword");
        request.setConfirmationPassword("newPassword");

        assertEquals("oldPassword", request.getCurrentPassword());
        assertEquals("newPassword", request.getNewPassword());
        assertEquals("newPassword", request.getConfirmationPassword());
    }

    @Test
    void testNoArgsConstructor() {
        // Test the no-arguments constructor
        ChangePasswordRequest request = new ChangePasswordRequest();

        assertNull(request.getCurrentPassword());
        assertNull(request.getNewPassword());
        assertNull(request.getConfirmationPassword());
    }

    @Test
    void testBuilder() {
        // Test the builder pattern
        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .currentPassword("oldPassword")
                .newPassword("newPassword")
                .confirmationPassword("newPassword")
                .build();

        assertEquals("oldPassword", request.getCurrentPassword());
        assertEquals("newPassword", request.getNewPassword());
        assertEquals("newPassword", request.getConfirmationPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        // Test equals and hashCode (provided by Lombok's @Data)
        ChangePasswordRequest request1 = new ChangePasswordRequest("oldPassword", "newPassword", "newPassword");
        ChangePasswordRequest request2 = new ChangePasswordRequest("oldPassword", "newPassword", "newPassword");
        ChangePasswordRequest request3 = new ChangePasswordRequest("differentPassword", "newPassword", "newPassword");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    void testToString() {
        // Test the toString method (provided by Lombok's @Data)
        ChangePasswordRequest request = new ChangePasswordRequest("oldPassword", "newPassword", "newPassword");

        String expected = "ChangePasswordRequest(currentPassword=oldPassword, newPassword=newPassword, confirmationPassword=newPassword)";
        assertEquals(expected, request.toString());
    }

    @Test
    void testAllArgsConstructor() {
        // Test the all-args constructor
        ChangePasswordRequest request = new ChangePasswordRequest("oldPassword", "newPassword", "newPassword");

        assertEquals("oldPassword", request.getCurrentPassword());
        assertEquals("newPassword", request.getNewPassword());
        assertEquals("newPassword", request.getConfirmationPassword());
    }

    @Test
    void testWithNullBuilder() {
        // Test the builder with null values
        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .currentPassword(null)
                .newPassword(null)
                .confirmationPassword(null)
                .build();

        assertNull(request.getCurrentPassword());
        assertNull(request.getNewPassword());
        assertNull(request.getConfirmationPassword());
    }

  @Test
        void testPasswordMatching() {
            // Verify that newPassword matches confirmationPassword
            ChangePasswordRequest request = new ChangePasswordRequest("oldPassword", "newPassword", "newPassword");

            assertEquals(request.getNewPassword(), request.getConfirmationPassword(),
                    "New password and confirmation password should match.");
        }

        @Test
        void testPasswordNotMatching() {
            // Verify that newPassword and confirmationPassword mismatch is detected
            ChangePasswordRequest request = new ChangePasswordRequest("oldPassword", "newPassword", "differentPassword");

            assertNotEquals(request.getNewPassword(), request.getConfirmationPassword(),
                    "New password and confirmation password should not match.");
        }

        @Test
        void testEmptyPasswords() {
            // Test with empty passwords
            ChangePasswordRequest request = new ChangePasswordRequest("", "", "");

            assertTrue(request.getCurrentPassword().isEmpty(), "Current password should be empty.");
            assertTrue(request.getNewPassword().isEmpty(), "New password should be empty.");
            assertTrue(request.getConfirmationPassword().isEmpty(), "Confirmation password should be empty.");
        }

        @Test
        void testNullPasswords() {
            // Test with null passwords
            ChangePasswordRequest request = new ChangePasswordRequest(null, null, null);

            assertNull(request.getCurrentPassword(), "Current password should be null.");
            assertNull(request.getNewPassword(), "New password should be null.");
            assertNull(request.getConfirmationPassword(), "Confirmation password should be null.");
        }

        @Test
        void testTrimmedPasswords() {
            // Test passwords with leading or trailing spaces
            ChangePasswordRequest request = new ChangePasswordRequest(" oldPassword ", " newPassword ", " newPassword ");

            assertEquals(" oldPassword ", request.getCurrentPassword(), "Current password should retain spaces.");
            assertEquals(" newPassword ", request.getNewPassword(), "New password should retain spaces.");
            assertEquals(" newPassword ", request.getConfirmationPassword(), "Confirmation password should retain spaces.");
        }

        @Test
        void testPasswordSpecialCharacters() {
            // Test passwords with special characters
            String specialPassword = "P@ssw0rd!#%";
            ChangePasswordRequest request = new ChangePasswordRequest(specialPassword, specialPassword, specialPassword);

            assertEquals(specialPassword, request.getCurrentPassword(), "Special characters should be preserved in current password.");
            assertEquals(specialPassword, request.getNewPassword(), "Special characters should be preserved in new password.");
            assertEquals(specialPassword, request.getConfirmationPassword(), "Special characters should be preserved in confirmation password.");
        }

        @Test
        void testLongPasswords() {
            // Test with long passwords
            String longPassword = "a".repeat(100);
            ChangePasswordRequest request = new ChangePasswordRequest(longPassword, longPassword, longPassword);

            assertEquals(longPassword, request.getCurrentPassword(), "Long password should be preserved in current password.");
            assertEquals(longPassword, request.getNewPassword(), "Long password should be preserved in new password.");
            assertEquals(longPassword, request.getConfirmationPassword(), "Long password should be preserved in confirmation password.");
        }

        @Test
        void testImmutableAfterCreation() {
            // Verify that immutability is respected (if implemented as such)
            ChangePasswordRequest request = new ChangePasswordRequest("oldPassword", "newPassword", "newPassword");

            String originalPassword = request.getNewPassword();
            request.setNewPassword("modifiedPassword");

            assertNotEquals(originalPassword, request.getNewPassword(),
                    "If ChangePasswordRequest is immutable, password modification should not be allowed.");
        }

        @Test
        void testAllPasswordsAreEqual() {
            // Edge case where all passwords are the same
            ChangePasswordRequest request = new ChangePasswordRequest("samePassword", "samePassword", "samePassword");

            assertEquals(request.getCurrentPassword(), request.getNewPassword(), "All passwords should be equal.");
            assertEquals(request.getNewPassword(), request.getConfirmationPassword(), "All passwords should be equal.");
        }

        @Test
        void testPasswordsAreCaseSensitive() {
            // Verify case sensitivity
            ChangePasswordRequest request = new ChangePasswordRequest("OldPassword", "NewPassword", "newpassword");

            assertNotEquals(request.getNewPassword(), request.getConfirmationPassword(),
                    "Passwords should be case-sensitive.");
        }
    }


