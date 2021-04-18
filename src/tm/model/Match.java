package tm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Match implements Serializable {
    private transient ObjectProperty<Participant> participant1;
    private transient ObjectProperty<Participant> participant2;
    private transient IntegerProperty participant1Score = new SimpleIntegerProperty(-1);
    private transient IntegerProperty participant2Score = new SimpleIntegerProperty(-1);
    private transient ObjectProperty<Participant> winner = new SimpleObjectProperty<>();

    public Match() {
        this.participant1 = new SimpleObjectProperty<>();
        this.participant2 = new SimpleObjectProperty<>();
    }

    public Match(Participant participant1, Participant participant2) {
        this.participant1 = new SimpleObjectProperty<>(participant1);
        this.participant2 = new SimpleObjectProperty<>(participant2);
    }

    public ObjectProperty<Participant> getWinner() {
        return winner;
    }

    public Participant getLooser() {
        if(winner.getValue() == participant1.getValue())
            return participant2.getValue();
        if(winner.getValue() == participant2.getValue())
            return participant1.getValue();
        return null;
    }

    public boolean hasBothParticipants(){
        return participant1.getValue() != null && participant2.getValue() != null;
    }

    public boolean hasAnyParticipant(){
        return participant1.getValue() != null || participant2.getValue() != null;
    }

    public boolean containsParticipant(Participant participant){
        return participant1.getValue() == participant || participant2.getValue() == participant;
    }

    public void removeParticipant(Participant participant){
        if (!containsParticipant(participant))
            return;
        if (participant1.getValue() == participant)
            participant1.set(null);
        else if (participant2.getValue() == participant)
            participant2.set(null);

    }

    public void addParticipant(Participant participant){
        if (containsParticipant(participant))
            return;
        if (participant1.getValue() == null)
            participant1.set(participant);
        else if (participant2.getValue() == null)
            participant2.set(participant);

    }

    public boolean hasWinner(){
        return getWinner().getValue() != null;
    }
    public void setWinner(Participant winner) {
        this.winner.set(winner);
    }

    public ObjectProperty<Participant> getParticipant1() {
        return participant1;
    }

    public void setParticipant1(Participant participant1) {
        this.participant1.set(participant1);
    }

    public ObjectProperty<Participant> getParticipant2() {
        return participant2;
    }

    public void setParticipant2(Participant participant2) {
        this.participant2.set(participant2);
    }


    public IntegerProperty getParticipant1ScoreProperty() {
        return participant1Score;
    }

    public void setParticipant1Score(Integer participant1Score) {
        this.participant1Score.set(participant1Score);
    }

    public IntegerProperty getParticipant2ScoreProperty() {
        return participant2Score;
    }

    public void setParticipant2Score(Integer participant2Score) {
        this.participant2Score.set(participant2Score);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(participant1.getValue());
        s.writeObject(participant2.getValue());
        s.writeObject(winner.getValue());
        s.writeInt(participant1Score.getValue());
        s.writeInt(participant2Score.getValue());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.participant1Score = new SimpleIntegerProperty();
        this.participant2Score = new SimpleIntegerProperty();
        this.participant1 = new SimpleObjectProperty<>();
        this.participant2 = new SimpleObjectProperty<>();
        this.winner = new SimpleObjectProperty<>();
        participant1.set((Participant)s.readObject());
        participant2.set((Participant)s.readObject());
        winner.set((Participant)s.readObject());
        participant1Score.set(s.readInt());
        participant2Score.set(s.readInt());
    }

}
