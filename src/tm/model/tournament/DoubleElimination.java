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

    private List<Bracket>[] loserBrackets;
    private Bracket finalBracket;
    private Bracket finalBracket2;
    private Participant finalFromWinners;
    private Participant finalFromLosers;
    private Map<Bracket,Bracket> bracketsToLoserBrackets = new HashMap<>();

    public DoubleElimination(String name, List<Participant> participants) {
        super(name, participants);
        this.typeString = "Double Elimination";
        int levels = Utility.log2(participants.size());
        int size = 2 * levels - 2;
        loserBrackets = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            loserBrackets[i] = new ArrayList<>();
        }
        createLoserBrackets(brackets);
        setNextLoserBrackets();
        mapLoserBrackets();
        finalBracket = new Bracket(new Match(), 0,0);
        finalBracket2 = new Bracket(new Match(), 1,0);
        brackets[brackets.length - 1].get(brackets[brackets.length - 1].size() - 1).setNextBracket(finalBracket);
        loserBrackets[loserBrackets.length - 1].get(loserBrackets[loserBrackets.length - 1].size() - 1)
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
                        loserBrackets[i].add(Lbracket);
                    }
                }
                else if(i == 1){
                    Lbracket = new Bracket(new Match(), i, j);
                    loserBrackets[i].add(Lbracket);
                } else {
                    Lbracket = new Bracket(new Match(), i + offset + 1, j);
                    loserBrackets[i + offset + 1].add(Lbracket);
                    Lbracket = new Bracket(new Match(), i + offset, j);
                    loserBrackets[i + offset].add(Lbracket);
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
                    bracketsToLoserBrackets.put(brackets[i].get(j), loserBrackets[i].get(j/2));
                else if(i == 1)
                    bracketsToLoserBrackets.put(brackets[i].get(j), loserBrackets[i].get(j));
                else {
                    bracketsToLoserBrackets.put(brackets[i].get(j), loserBrackets[i + offset + 1].get(j));
                }
            }
            if (i > 1)
                offset++;
        }
    }

    private void setNextLoserBrackets(){
        for (int i = 0; i < loserBrackets.length - 1; i++) {
            for (int j = 0; j < loserBrackets[i].size(); j++) {
                Bracket bracket = loserBrackets[i].get(j);
                if(i % 2 == 0){
                    bracket.setNextBracket(loserBrackets[i+1].get(j));
                }else {
                    bracket.setNextBracket(loserBrackets[i+1].get(j/2));
                }
            }
        }
    }

    private void initializeListeners(){
        for (List<Bracket> bracketList : brackets) {
            for (Bracket bracket : bracketList) {
                bracket.getMatch().getWinner().addListener((observable, oldValue, newValue) -> {
                   if(newValue == null){
                       Bracket loserBracket = bracketsToLoserBrackets.get(bracket);
                        loserBracket.unsetWinner();
                        Participant toRemove;
                        if (bracket.getMatch().getParticipant1().getValue() == oldValue)
                            toRemove = bracket.getMatch().getParticipant2().getValue();
                        else
                            toRemove = bracket.getMatch().getParticipant1().getValue();
                        loserBracket.getMatch().removeParticipant(toRemove);
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
        loserBrackets[loserBrackets.length - 1].get(loserBrackets[loserBrackets.length - 1].size() - 1).
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
        Bracket loserBracket = bracketsToLoserBrackets.get(bracket);
        Participant loser = bracket.getMatch().getLoser();
        loserBracket.getMatch().addParticipant(loser);
    }

    public List<Bracket>[] getLoserBrackets() {
        return loserBrackets;
    }

    public Map<Bracket, Bracket> getBracketsToLoserBrackets() {
        return bracketsToLoserBrackets;
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
