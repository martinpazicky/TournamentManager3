package tm.model.tournament;

import javafx.beans.property.SimpleObjectProperty;
import tm.model.Bracket;
import tm.model.Match;
import tm.model.Participant;
import tm.model.utility.Utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class DoubleElimination extends SingleElimination{

    private List<Bracket>[] looserBrackets;
    private Bracket finalBracket;
    private Bracket finalBracket2;
    private Participant finalFromWinners;
    private Participant finalFromLosers;
    private Map<Bracket,Bracket> bracketsToLooserBrackets = new HashMap<>();
    private final int looserSize;

    public DoubleElimination(String name, List<Participant> participants) {
        super(name, participants);
        this.typeString = "Double Elimination";
        int levels = Utility.log2(participants.size());
        int size = 2 * levels - 2;
        looserSize = size;
        looserBrackets = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            looserBrackets[i] = new ArrayList<>();
        }
        createLoserBrackets(brackets);
        setNextLoserBrackets();
        mapLoserBrackets();
        finalBracket = new Bracket(new Match(), 0,0);
        finalBracket2 = new Bracket(new Match(), 1,0);
        brackets[brackets.length - 1].get(brackets[brackets.length - 1].size() - 1).setNextBracket(finalBracket);
        looserBrackets[looserBrackets.length - 1].get(looserBrackets[looserBrackets.length - 1].size() - 1)
                .setNextBracket(finalBracket);
        finalBracket.setNextBracket(finalBracket2);
        initializeListeners();
    }

    private void createLoserBrackets(List<Bracket>[] brackets){
        int offset = 0;
        for (int i = 0; i < brackets.length; i++){
            Bracket Lbracket;
            for(int j = 0; j < brackets[i].size(); j++) {
                if(i == 0) {
                    if(j % 2 == 0) {
                        Lbracket = new Bracket(new Match(), i, j);
                        looserBrackets[i].add(Lbracket);
                    }
                }
                else if(i == 1){
                    Lbracket = new Bracket(new Match(), i, j);
                    looserBrackets[i].add(Lbracket);
                } else {
                    Lbracket = new Bracket(new Match(), i + offset + 1, j);
                    looserBrackets[i + offset + 1].add(Lbracket);
                    Lbracket = new Bracket(new Match(), i + offset, j);
                    looserBrackets[i + offset].add(Lbracket);
                }
            }
            if (i > 1)
                offset++;
        }
    }

    private void mapLoserBrackets() {
        int offset = 0;
        for (int i = 0; i < brackets.length; i++) {
            for (int j = 0; j < brackets[i].size(); j++) {
                if(i == 0)
                    bracketsToLooserBrackets.put(brackets[i].get(j),looserBrackets[i].get(j/2));
                else if(i == 1)
                    bracketsToLooserBrackets.put(brackets[i].get(j),looserBrackets[i].get(j));
                else {
                    bracketsToLooserBrackets.put(brackets[i].get(j),looserBrackets[i + offset + 1].get(j));
                }
            }
            if (i > 1)
                offset++;
        }
    }

    private void setNextLoserBrackets(){
        for (int i = 0; i < looserBrackets.length - 1; i++) {
            for (int j = 0; j < looserBrackets[i].size(); j++) {
                Bracket bracket = looserBrackets[i].get(j);
                if(i % 2 == 0){
                    bracket.setNextBracket(looserBrackets[i+1].get(j));
                }else {
                    bracket.setNextBracket(looserBrackets[i+1].get(j/2));
                }
            }
        }
    }

    private void initializeListeners(){
        for (List<Bracket> bracketList : brackets) {
            for (Bracket bracket : bracketList) {
                bracket.getMatch().getWinner().addListener((observable, oldValue, newValue) -> {
                   if(newValue == null){
                       Bracket looserBracket = bracketsToLooserBrackets.get(bracket);
                        looserBracket.unsetWinner();
                        Participant toRemove;
                        if (bracket.getMatch().getParticipant1().getValue() == oldValue)
                            toRemove = bracket.getMatch().getParticipant2().getValue();
                        else
                            toRemove = bracket.getMatch().getParticipant1().getValue();
                        looserBracket.getMatch().removeParticipant(toRemove);
                   }else {
                       setLoserBracket(bracket);
                   }
                });
            }
        }
        brackets[brackets.length - 1].get(brackets[brackets.length - 1].size() - 1).getMatch().getWinner().addListener(
                (observable, oldValue, newValue) -> {
                    finalFromWinners = newValue;
                }
        );
        looserBrackets[looserBrackets.length - 1].get(looserBrackets[looserBrackets.length - 1].size() - 1).
                getMatch().getWinner().addListener(
                (observable, oldValue, newValue) -> {
                    finalFromLosers = newValue;
                }
        );
        finalBracket.getMatch().getWinner().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == finalFromLosers){
                        finalBracket.setNextBracket(finalBracket2);
                        finalBracket2.getMatch().addParticipant(finalFromWinners);
                    }
                    if (newValue == finalFromWinners){
                        finalBracket.setNextBracket(null);
                        finalBracket.setWinner(finalFromWinners);
                        tournamentWinner.set(finalFromWinners);
                        System.out.println("winner is " + newValue.getNickName());
                    }
                    if (newValue == null){
                        tournamentWinner.set(null);
                        finalBracket2.getMatch().setParticipant1(null);
                    }
                }
        );
        finalBracket2.getMatch().getWinner().addListener(
                (observable, oldValue, newValue) -> {
                    tournamentWinner.set(newValue);
                    if(newValue != null)
                        System.out.println("winner is " + newValue.getNickName());
                }
        );
    }

    private void setLoserBracket(Bracket bracket){
        Bracket looserBracket = bracketsToLooserBrackets.get(bracket);
        Participant looser = bracket.getMatch().getLooser();
        looserBracket.getMatch().addParticipant(looser);
    }

    public List<Bracket>[] getLooserBrackets() {
        return looserBrackets;
    }

    public Map<Bracket, Bracket> getBracketsToLooserBrackets() {
        return bracketsToLooserBrackets;
    }

    public Bracket getFinalBracket() {
        return finalBracket;
    }

    public Bracket getFinalBracket2() {
        return finalBracket2;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.tournamentWinner = new SimpleObjectProperty<>();
        this.tournamentWinner.set((Participant)s.readObject());
        initializeListeners();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(tournamentWinner.getValue());
    }
}
