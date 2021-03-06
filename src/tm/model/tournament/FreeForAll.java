package tm.model.tournament;

import javafx.beans.property.IntegerProperty;
import tm.model.Match;
import tm.model.Participant;
import tm.model.ParticipantRecord;
import tm.model.TableCell;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FreeForAll extends Tournament implements Serializable {
    private List<List<TableCell>[]> allRounds = new ArrayList<>();
    private int rounds;
    private Map<Participant, ParticipantRecord> participantsToRecords = new HashMap<>();

    public FreeForAll(String name, List<Participant> participants, int rounds) {
        super(name, participants);
        this.typeString = "Round Robin";
        this.rounds = rounds;
        int levels = participants.size();
        for (int i = 0; i < rounds; i++){
            List<TableCell>[] table = new ArrayList[levels];
            for (int j = 0; j < levels; j++) {
                table[j] = new ArrayList<>();
            }
            allRounds.add(table);
            createCells(levels, table);
            initializeListeners(table);
        }
        for (Participant participant : participants){
            ParticipantRecord participantRecord = new ParticipantRecord(participant);
            participantsToRecords.put(participant,participantRecord);
        }
    }

    private void createCells(int levels, List<TableCell>[] table){
        for (int i = 0; i < levels; i++){
            for(int j = 0; j < levels; j++) {
                TableCell tableCell = new TableCell(new Match(participants.get(i), participants.get(j)), i, j);
                table[i].add(tableCell);
            }
        }
    }

    public void initializeListeners(List<TableCell>[] table){
        for (List<TableCell> cellList : table) {
            for (TableCell tableCell : cellList) {
                tableCell.getMatch().getParticipant1ScoreProperty().addListener((observable, oldValue, newValue) -> {
                    proceed(tableCell, table);
                });
                tableCell.getMatch().getParticipant2ScoreProperty().addListener((observable, oldValue, newValue) -> {
                    proceed(tableCell, table);
                });
            }
        }
    }

    public TableCell getPairCell(TableCell tableCell, List<TableCell>[] table){
        return table[tableCell.getColumn()].get(tableCell.getRow());
    }

    public void proceed(TableCell tableCell, List<TableCell>[] table){
        TableCell pairTableCell = getPairCell(tableCell, table);
        IntegerProperty x_score = tableCell.getMatch().getParticipant1ScoreProperty();
        IntegerProperty y_score = tableCell.getMatch().getParticipant2ScoreProperty();
        if( x_score.getValue() != null){
            int score1 = tableCell.getMatch().getParticipant1ScoreProperty().get();
            pairTableCell.getMatch().setParticipant2Score(score1);
        }
        else {
            int score1 = 0;
            pairTableCell.getMatch().setParticipant2Score(score1);
        }
        if( y_score.getValue() != null){
            int score2 = tableCell.getMatch().getParticipant2ScoreProperty().get();
            pairTableCell.getMatch().setParticipant1Score(score2);
        }
        else {
            int score2 = 0;
            pairTableCell.getMatch().setParticipant1Score(score2);
        }
    }

    public List<List<TableCell>[]> getAllRounds() {
        return allRounds;
    }

    public void setAllRounds(List<List<TableCell>[]> allRounds) {
        this.allRounds = allRounds;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        for (int i = 0; i < rounds; i++){
            List<TableCell>[] table = allRounds.get(i);
            initializeListeners(table);
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    public Map<Participant, ParticipantRecord> getParticipantsToRecords() {
        return participantsToRecords;
    }

    public void setParticipantsToRecords(Map<Participant, ParticipantRecord> participantsToRecords) {
        this.participantsToRecords = participantsToRecords;
    }
}
