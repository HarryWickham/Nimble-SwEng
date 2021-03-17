package uk.ac.york.nimblefitness.HelperClasses;

import   org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;


class VerificationTest {
    String email = "hmt519@york.ac.uk", password = "Hello123", confirmPassword = "Hello123";
    Verification user = new Verification(email,password,confirmPassword);

    @Test
    void validateEmail_CorrectEmailSample_ReturnsTrue() {
        System.out.println(user.validateEmail());
        assertEquals("Valid",user.validateEmail());
    }

    @Test
    void validatePassword_CorrectFormat_ReturnsTrue() {
        assertEquals("Valid",user.validatePassword());
    }

    @Test
    void validateConfirmPassword_CorrectFormat_ReturnsTrue() {
        assertEquals("Valid",user.validateConfirmPassword());
    }
}