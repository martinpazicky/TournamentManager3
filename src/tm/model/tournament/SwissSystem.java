package tm.model.tournament;

import tm.model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwissSystem extends Tournament implements Serializable {

    private List<SwissBracket>[] brackets;
    final private int maxRounds;
    private Map<Participant, ParticipantRecord> participantsToRecords = new HashMap<>();


    public SwissSystem(String name, List<Participant> participants) {
        super(name, participants);
        this.typeString = "Swiss System";
        this.maxRounds = participants.size();
        brackets = new ArrayList[maxRounds];
        for (int i = 0; i < maxRounds; i++) {
            brackets[i] = new ArrayList<>();
        }
        createBrackets(participants.size(), maxRounds);
        fillFirstLevelBrackets(participants.size());

        for (Participant participant : participants){
            ParticipantRecord participantRecord = new ParticipantRecord(participant);
            participantsToRecords.put(participant,participantRecord);
        }
    }

    private void createBrackets(int size, int maxLevel){
        for (int i = 0; i < maxLevel; i++){
            for(int j = 0; j < size/2; j++) {
                SwissBracket swissBracket = new SwissBracket(new Match(), i, j);
                brackets[i].add(swissBracket);
            }
        }
    }

    private void fillFirstLevelBrackets(int size){
        int half = size/2;
        for(int j = 0; j < size/2; j++) {
            SwissBracket swissBracket = brackets[0].get(j);
            swissBracket.getMatch().setParticipant1(participants.get(j));
            swissBracket.getMatch().setParticipant2(participants.get(half));
            half = half + 1;
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    public List<SwissBracket>[] getBrackets() {
        return brackets;
    }

    public void setBrackets(List<SwissBracket>[] brackets) {
        this.brackets = brackets;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public Map<Participant, ParticipantRecord> getParticipantsToRecords() {
        return participantsToRecords;
    }

    public void setParticipantsToRecords(Map<Participant, ParticipantRecord> participantsToRecords) {
        this.participantsToRecords = participantsToRecords;
    }
}
