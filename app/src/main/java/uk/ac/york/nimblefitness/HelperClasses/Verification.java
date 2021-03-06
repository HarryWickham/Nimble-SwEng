package uk.ac.york.nimblefitness.HelperClasses;

public class Verification {

    String EMAIL_PATTERN = "((([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(\\" +
            ".[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)|(\"(" +
            "([\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F]|[\\x21\\x23-\\x5B\\x5D-\\x7E])|" +
            "(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F]))*\"))@(([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(\\" +
            ".[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)|(\\[(" +
            "([\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F]|[\\x21-\\x5A\\x5E-\\x7E])|" +
            "(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F]))*])))";
    private String email;
    private String password;
    private String confirmPassword;

    public Verification(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public Verification(String email, String password, String confirmPassword) {
        this.password = password;
        this.email = email;
        this.confirmPassword = confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validateEmail() {

        if (email.isEmpty()) {//checks to see if an email address has been entered
            return ("Email is Required");

        } else if (!email.matches(EMAIL_PATTERN)) {//checks to see if the email address entered
            // follows the correct pattern
            return ("Invalid email address");
        } else {//removes any error messages that appeared if the email was incorrect previously
            return ("Valid");
        }
    }

    public String validatePassword() {

        String passwordVal = "^" + "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 6 characters
                "$";

        if (password.isEmpty()) {//checks to see if a password has been entered
            return ("Password is Required");

        } else if (!password.matches(passwordVal)) {//checks to see if the password entered
            // follows the correct pattern
            return ("Invalid Password must be more than 6 characters long with at least 1 lower " +
                    "case letter and at least 1 upper case letter and a number");

        } else return ("Valid");
    }

    public String validateConfirmPassword() {

        if (confirmPassword.isEmpty()) {//checks to see if a confirm password has been entered
            return ("Password is Required");
        } else if (!confirmPassword.equals(password)) {//checks to see if a confirm password and
            // password are the same
            return ("Confirm Password must be the same as Password");

        } else {//removes any error messages that appeared if the password was incorrect previously
            return ("Valid");
        }
    }
}
