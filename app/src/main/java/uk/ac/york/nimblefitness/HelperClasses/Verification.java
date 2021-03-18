package uk.ac.york.nimblefitness.HelperClasses;

public class Verification {

    private String email;
    private String password;
    private String confirmPassword;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public Verification(String email, String password) {
        this.password = password;
        this.email = email;
    }
    public Verification(String email,String password, String confirmPassword) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String validateEmail() {

        if (email.isEmpty()) {//checks to see if an email address has been entered
            return ("Email is Required");

        }else if (!email.matches(EMAIL_PATTERN)) {//checks to see if the email address entered follows the correct pattern
            return("Invalid email address");
        }
        else {//removes any error messages that appeared if the email was incorrect previously
            return("Valid");
        }
    }

    public String validatePassword() {

        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 6 characters
                "$";

        if (password.isEmpty()) {//checks to see if a password has been entered
            return ("Password is Required");

        } else  if (!password.matches(passwordVal)) {//checks to see if the password entered follows the correct pattern
            return ("Invalid Password must be more than 6 characters long with at least 1 lower case letter and at least 1 upper case letter and a number");

        }else
            return("Valid");
    }

    public String validateConfirmPassword(){

        if (confirmPassword.isEmpty()) {//checks to see if a confirm password has been entered
            return ("Password is Required");
        }else if (!confirmPassword.equals(password)) {//checks to see if a confirm password and password are the same
            return ("Confirm Password must be the same as Password");

        } else {//removes any error messages that appeared if the password was incorrect previously
           return("Valid");
        }
    }
}
