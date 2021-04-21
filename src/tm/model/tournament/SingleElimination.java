package tm.model.tournament;

import tm.model.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SingleElimination extends Tournament implements Serializable {

    protected List<Bracket>[] brackets;
    final protected int maxLevel;

    public SingleElimination(String name, List<Participant> participants) {
        super(name, participants);
        this.typeString = "Single Elimination";
        int levels = Utility.log2(participants.size());
        maxLevel = levels - 1;
        brackets = new ArrayList[levels];
        for (int i = 0; i < levels; i++) {
            brackets[i] = new ArrayList<>();
        }
        createBrackets(levels,brackets);
        setNextBrackets(levels,brackets);
        fillFirstLevelBrackets();
    }

    /**
     * Fills list of brackets with empty brackets upon
       tournament instantiation
     * @param levels amount of levels (horizontal)
     */
    protected void createBrackets(int levels, List<Bracket>[] brackets){
        for (int i = 0; i < levels; i++){
            for(int j = 0; j < Math.pow(2,(levels - i - 1)); j++) {
                Bracket bracket = new Bracket(new Match(),i,brackets[i].size());
                brackets[i].add(bracket);
            }
        }
    }

    protected void setNextBrackets(int levels, List<Bracket>[] brackets){
        for (int i = 0; i < levels; i++){
            for(int j = 0; j < Math.pow(2,(levels - i - 1)); j++) {
                brackets[i].get(j).setNextBracket(getNextBracket(brackets[i].get(j),brackets));
            }
        }
    }

    /**
     * Fills first level brackets with participant
       upon instantiation
     */
    private void fillFirstLevelBrackets(){
        for (int i = 0; i < brackets[0].size(); i++){
            Participant p = participants.get(i);
            brackets[0].get(i).getMatch().setParticipant1(p);
        }
        for (int i = brackets[0].size(); i < participants.size(); i++){
            Participant p = participants.get(i);
            brackets[0].get(i - brackets[0].size()).getMatch().setParticipant2(p);
        }
        proceedFirstLevelBrackets();
    }

    /**
     * winner in first level brackets which only
       have one participant is set automatically
     */
    private void proceedFirstLevelBrackets(){
        for (int i = 0; i < brackets[0].size(); i++) {
            if (!brackets[0].get(i).getMatch().hasBothParticipants())
                brackets[0].get(i).setWinner(brackets[0].get(i).getMatch().getParticipant1().getValue());
        }
    }


    public Bracket getNextBracket(Bracket bracket, List<Bracket>[] brackets){
//        if(bracket.getLevel() == maxLevel)
        try {
            return brackets[bracket.getLevel() + 1].get(bracket.getIndex() / 2);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public List<Bracket> getPreviousBrackets(Bracket bracket){
        List<Bracket> prev = new ArrayList<>();
        if (bracket.getLevel() == 0)
            return prev;
        Bracket pr1 = brackets[bracket.getLevel() - 1].get(bracket.getIndex() * 2);
        Bracket pr2 = brackets[bracket.getLevel() - 1].get(bracket.getIndex() * 2 + 1);
        prev.add(pr1);
        prev.add(pr2);
        return prev;
    }

    public Bracket getPreviousBracket(Bracket bracket, Participant participant){
        List<Bracket> previous = getPreviousBrackets(bracket);
        for (Bracket br : previous){
            if (br.getMatch().getParticipant1().getValue().equals(participant) ||
                    br.getMatch().getParticipant2().getValue().equals(participant))
                return br;
        }
        return null;
    }

    public List<Bracket>[] getBrackets() {
        return brackets;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
