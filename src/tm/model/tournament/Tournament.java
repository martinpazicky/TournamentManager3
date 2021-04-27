package tm.model.tournament;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import tm.model.Participant;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Tournament implements Serializable {
    private int id;
    private String name;
    protected List<Participant> participants;
    private LocalDate date;
    boolean isFinished;
    protected transient ObjectProperty<Participant> tournamentWinner = new SimpleObjectProperty<>();
    protected String typeString;
    private String sportType;

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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getTypeString() {
        return typeString;
    }

    public Participant getTournamentWinner() {
        return tournamentWinner.get();
    }

    public ObjectProperty<Participant> tournamentWinnerProperty() {
        return tournamentWinner;
    }

    public void setTournamentWinner(Participant tournamentWinner) {
        this.tournamentWinner.set(tournamentWinner);
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(tournamentWinner.getValue());
    }



    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.tournamentWinner = new SimpleObjectProperty<>();
        this.tournamentWinner.set((Participant)s.readObject());
    }
}
