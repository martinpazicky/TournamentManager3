package tm.model.tournament;

import tm.model.Participant;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Tournament implements Serializable {
    private int id;
    private String name;
    protected List<Participant> participants;
    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getColor(){
        if (this instanceof FreeForAll) {
            return "#4281f5";
        }
        else if (this instanceof DoubleElimination) {
            return "#870c1c";
        }
        return "#34a32e";
    }
    public String getHoverColor(){
        if (this instanceof FreeForAll) {
            return "#4281f5";
        }
        else if (this instanceof DoubleElimination) {
            return "#de213a";
        }
        return "#34a32e";
    }
}
