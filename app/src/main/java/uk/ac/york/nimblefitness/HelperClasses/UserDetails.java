package uk.ac.york.nimblefitness.HelperClasses;

public class UserDetails {

    String firstName, lastName, gender, exerciseType, exerciseDuration, membershipPlan;
    int currentMoves, age, weeklyGoal, completedRoutines, lastLogin;
    boolean acceptedTC, onBoarded;

    public UserDetails() {
    }

    public UserDetails(String firstName, String lastName, String gender, String exerciseType, String exerciseDuration, int age, String membershipPlan, int weeklyGoal, int currentMoves, int completedRoutines, int lastLogin, boolean acceptedTC, boolean onBoarded) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.exerciseType = exerciseType;
        this.exerciseDuration = exerciseDuration;
        this.membershipPlan = membershipPlan;
        this.currentMoves = currentMoves;
        this.age = age;
        this.weeklyGoal = weeklyGoal;
        this.completedRoutines = completedRoutines;
        this.lastLogin = lastLogin;
        this.acceptedTC = acceptedTC;
        this.onBoarded = onBoarded;
    }

    /*
        public UserDetails(String firstName, String lastName, String gender, String exerciseType, String exerciseDuration, int age, String membershipPlan, int weeklyGoal, int currentMoves, int completedRoutines, int lastLogin) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.exerciseType = exerciseType;
            this.exerciseDuration = exerciseDuration;
            this.age = age;
            this.membershipPlan = membershipPlan;
            this.weeklyGoal = weeklyGoal;
            this.currentMoves = currentMoves;
            this.completedRoutines = completedRoutines;
            this.lastLogin = lastLogin;
        }

    public UserDetails(String firstName, String lastName, String gender, String exerciseType, String exerciseDuration, int age, String membershipPlan, int weeklyGoal, int currentMoves, int completedRoutines, int lastLogin, boolean acceptedTC) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.exerciseType = exerciseType;
        this.exerciseDuration = exerciseDuration;
        this.age = age;
        this.membershipPlan = membershipPlan;
        this.weeklyGoal = weeklyGoal;
        this.currentMoves = currentMoves;
        this.completedRoutines = completedRoutines;
        this.lastLogin = lastLogin;
        this.acceptedTC = acceptedTC;
    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(String exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMembershipPlan() {
        return membershipPlan;
    }

    public void setMembershipPlan(String membershipPlan) {
        this.membershipPlan = membershipPlan;
    }

    public int getWeeklyGoal() {
        return weeklyGoal;
    }

    public void setWeeklyGoal(int weeklyGoal) {
        this.weeklyGoal = weeklyGoal;
    }

    public int getCurrentMoves() {
        return currentMoves;
    }

    public void setCurrentMoves(int currentMoves) {
        this.currentMoves = currentMoves;
    }

    public int getCompletedRoutines() {
        return completedRoutines;
    }

    public void setCompletedRoutines(int completedRoutines) {
        this.completedRoutines = completedRoutines;
    }

    public int getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(int lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isAcceptedTC() {
        return acceptedTC;
    }

    public void setAcceptedTC(boolean acceptedTC) {
        this.acceptedTC = acceptedTC;
    }

    public boolean isOnBoarded() {
        return onBoarded;
    }

    public void setOnBoarded(boolean onBoarded) {
        this.onBoarded = onBoarded;
    }
}
