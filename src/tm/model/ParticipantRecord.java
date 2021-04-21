package tm.model;

import java.io.Serializable;

public class ParticipantRecord implements Serializable {
    private Participant participant;
    private int points = 0;
    private int matchesPlayed = 0;
    private int numOfWins = 0;
    private int numOfLosses = 0;
    private int numOfDraws = 0;
    private int rank = 0;

    public ParticipantRecord(Participant participant) {
        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getNumOfWins() {
        return numOfWins;
    }

    public void setNumOfWins(int numOfWins) {
        this.numOfWins = numOfWins;
    }

    public int getNumOfLosses() {
        return numOfLosses;
    }

    public void setNumOfLosses(int numOfLosses) {
        this.numOfLosses = numOfLosses;
    }

    public int getNumOfDraws() {
        return numOfDraws;
    }

    public void setNumOfDraws(int numOfDraws) {
        this.numOfDraws = numOfDraws;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int gRank) {
        this.rank = gRank;
    }
}
