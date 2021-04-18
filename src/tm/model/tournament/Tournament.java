package tm.model.tournament;

import tm.model.Participant;

import java.io.Serializable;
import java.util.List;

public class Tournament implements Serializable {
    private int id;
    private String name;
    protected List<Participant> participants;

    public Tournament(String name, List<Participant> participants) {
        this.name = name;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
