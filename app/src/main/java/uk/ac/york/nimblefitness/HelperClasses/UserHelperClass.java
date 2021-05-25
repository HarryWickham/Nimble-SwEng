package uk.ac.york.nimblefitness.HelperClasses;

public class UserHelperClass {

    String firstName, lastName, gender, exerciseType, exerciseDuration, age, membershipPlan, weeklyGoal;

    public UserHelperClass() {
    }

    public UserHelperClass(String membershipPlan) {
        this.membershipPlan = membershipPlan;
    }

    public UserHelperClass(String firstName, String lastName, String gender, String exerciseType, String exerciseDuration, String age, String membershipPlan, String weeklyGoal) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.exerciseType = exerciseType;
        this.exerciseDuration = exerciseDuration;
        this.age = age;
        this.membershipPlan = membershipPlan;
        this.weeklyGoal = weeklyGoal;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMembershipPlan() {
        return membershipPlan;
    }

    public void setMembershipPlan(String membershipPlan) {
        this.membershipPlan = membershipPlan;
    }

    public String getWeeklyGoal() {
        return weeklyGoal;
    }

    public void setWeeklyGoal(String weeklyGoal) {
        this.weeklyGoal = weeklyGoal;
    }
}
