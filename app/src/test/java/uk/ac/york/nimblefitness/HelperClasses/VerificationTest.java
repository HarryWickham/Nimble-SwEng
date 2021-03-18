package uk.ac.york.nimblefitness.HelperClasses;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;


class VerificationTest {

    Verification allValid = new Verification("yahoo@email.com","Password123","Password123");
    Verification invalidEmail = new Verification("emailemailcomxc","Password123","Password123");
    Verification differentPasswords = new Verification("email@yahoo.com", "Password123", "Password324");

    //User enters valid details
    @Test
    void validateEmailCorrectEmailSampleReturnsTrue() {
        assertEquals("Valid", allValid.validateEmail());
    }
    @Test
    void validatePasswordCorrectFormatReturnsTrue() {
        assertEquals("Valid",allValid.validatePassword());
    }
    @Test
    void validateConfirmPasswordCorrectFormatReturnsTrue() {
        assertEquals("Valid",allValid.validateConfirmPassword());
    }

    //User enters invalid email
    @Test
    void validateEmailWrongEmailSampleReturnsTrue() {
        assertEquals("Invalid email address", invalidEmail.validateEmail());
    }
    @Test
    void validatePasswordWrongEmailSampleReturnsTrue() {
        assertEquals("Valid",invalidEmail.validatePassword());
    }
    @Test
    void validateConfirmPasswordWrongEmailSampleReturnsTrue() {
        assertEquals("Valid",invalidEmail.validateConfirmPassword());
    }

    //User enters non-matching passwords
    @Test
    void validateEmailWrongPasswordsReturnsTrue() {
        assertEquals("Valid", differentPasswords.validateEmail());
    }
    @Test
    void validatePasswordWrongPasswordsReturnsTrue() {
        assertEquals("Valid", differentPasswords.validatePassword());
    }

    @Test
    void validateConfirmPasswordWrongFormatReturnsTrue() {
        assertEquals("Confirm Password must be the same as Password", differentPasswords.validateConfirmPassword()); //Expecting the passwords to not match
    }


}