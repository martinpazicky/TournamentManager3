package tm.model;

public class ParticipantRecord {
    private Participant participant;
    private Integer points = 0;
    private Integer matchesPlayed = 0;
    private Integer numOfWins = 0;
    private Integer numOfLosses = 0;
    private Integer numOfDraws = 0;
    private Integer gRank = 0;

    public ParticipantRecord(Participant participant) {
        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(Integer matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public Integer getNumOfWins() {
        return numOfWins;
    }

    public void setNumOfWins(Integer numOfWins) {
        this.numOfWins = numOfWins;
    }

    public Integer getNumOfLosses() {
        return numOfLosses;
    }

    public void setNumOfLosses(Integer numOfLosses) {
        this.numOfLosses = numOfLosses;
    }

    public Integer getNumOfDraws() {
        return numOfDraws;
    }

    public void setNumOfDraws(Integer numOfDraws) {
        this.numOfDraws = numOfDraws;
    }

    public Integer getgRank() {
        return gRank;
    }

    public void setgRank(Integer gRank) {
        this.gRank = gRank;
    }
}
