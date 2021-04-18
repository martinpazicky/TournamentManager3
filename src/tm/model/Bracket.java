package tm.model;

import com.sun.xml.internal.ws.wsdl.writer.document.Part;
import javafx.fxml.FXML;

import java.io.Serializable;

public class Bracket implements Serializable {
    private Match match;
    private int level;
    private int index;
    protected Bracket nextBracket;

    public Bracket(Match match, int level, int index) {
        this.match = match;
        this.level = level;
        this.index = index;
    }

    public Bracket(Bracket bracket) {
        this.match = bracket.match;
        this.level = bracket.level;
        this.index = bracket.index;
    }

    public Bracket(int level, int index) {
        this.level = level;
        this.index = index;
    }

    public void setWinner(Participant winner){
        if (match.getWinner().getValue() == winner) {
            // winner was set to the same value as before -> no changes shall be made
            return;
        }
//        if (match.hasBothParticipants() || level == 0) {
            if (match.getWinner().getValue() == null) {
                // winner was not set before -> set winner and put him in next bracket
                setNewWinner(winner);
            }else {
                // winner was set before -> change winner and update brackets
                changeWinner(winner);
            }
//        }
//        else {
//            System.out.println("neni mozne");
//        }
    }

    private void setNewWinner(Participant winner){
        match.setWinner(winner);

        if (nextBracket == null) {
            System.out.println("Víťaz turnaja je " + winner.getName());
            return;
        }

        if (nextBracket.getMatch().getParticipant1().getValue() == null)
            nextBracket.getMatch().setParticipant1(winner);
        else
            nextBracket.getMatch().setParticipant2(winner);
    }

    private void changeWinner(Participant winner){
        unsetWinner();
        setWinner(winner);
        if(nextBracket != null)
            nextBracket.unsetWinner();
    }

    public void unsetWinner(){
        Bracket iterator = nextBracket;
        Participant toRemove = match.getWinner().getValue();
        match.setWinner(null);
        while(iterator != null){
            Participant currWinner = iterator.getMatch().getWinner().getValue();
            iterator.getMatch().setWinner(null);
            iterator.getMatch().removeParticipant(toRemove);
            toRemove = currWinner;
            iterator = iterator.getNextBracket();
        }
    }


    public boolean hasWinner(){
        return match.getWinner().getValue() != null;
    }

    public Bracket getNextBracket() {
        return nextBracket;
    }

    public void setNextBracket(Bracket nextBracket) {
        this.nextBracket = nextBracket;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
