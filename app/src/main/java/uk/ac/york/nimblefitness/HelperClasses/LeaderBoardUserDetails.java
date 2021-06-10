package uk.ac.york.nimblefitness.HelperClasses;

/**
 * Creating an object that contains all the information that is required to display all users on
 * the leader board this is used to save and retrieve from the firebase realtime database
 */

public class LeaderBoardUserDetails implements Comparable<LeaderBoardUserDetails> {
    String name;
    int score;
    String uuid;
    boolean user;

    public LeaderBoardUserDetails() {
    }

    public LeaderBoardUserDetails(String name, int score, String uuid) {
        this.name = name;
        this.score = score;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    @Override
    public int compareTo(LeaderBoardUserDetails o) {
        return o.score - this.score;
    }
}
