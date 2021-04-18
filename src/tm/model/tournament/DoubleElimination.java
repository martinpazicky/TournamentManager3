package tm.model.tournament;

import tm.model.Bracket;
import tm.model.Match;
import tm.model.Participant;
import tm.model.Utility;

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
    private Set<Bracket> toDraw = new HashSet<>();
    private final int looserSize;

    public DoubleElimination(String name, List<Participant> participants) {
        super(name, participants);
        int levels = Utility.log2(participants.size());
        System.out.println("levels = " + levels);
        int size = 2 * levels - 2;
        looserSize = size;
        System.out.println("size = " + size);
        looserBrackets = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            looserBrackets[i] = new ArrayList<>();
        }
        createBrackets(size,looserBrackets);
        setNextBrackets(size,looserBrackets);
        mapLooserBrackets();
        finalBracket = new Bracket(new Match(), 0,0);
        finalBracket2 = new Bracket(new Match(), 1,0);
        brackets[brackets.length - 1].get(brackets[brackets.length - 1].size() - 1).setNextBracket(finalBracket);
        looserBrackets[looserBrackets.length - 1].get(looserBrackets[looserBrackets.length - 1].size() - 1)
                .setNextBracket(finalBracket);
        finalBracket.setNextBracket(finalBracket2);
        initializeListeners();
    }

    private void mapLooserBrackets(){
        //HARDCODE
        int levels = looserBrackets.length;
        for (int i = 0; i < brackets.length; i++) {
            int multiplier = 0;
            for (int j = 0; j < brackets[i].size(); j++) {
                if (i == 0){
                    int partSize = brackets[i].size();
                    int toLvl = i;
                    if(j == 4)
                        multiplier++;
                    int toIndex = looserBrackets[i].size() / 2 + j;
//                    int toIndex = looserBrackets[i].size() / 2 + j + 4  + multiplier * 4;
//                    bracketsToLooserBrackets.put(brackets[i].get(j), looserBrackets[i].get(
//                            looserBrackets[i].size() / 2 + (j - (j % 2))
//                    ));
                    System.out.println("from " + i + " " + j + " to " + toLvl + " " + toIndex);
                }
            }
        }
//
//                if (i == 0)
//                    bracketsToLooserBrackets.put(brackets[i].get(j), looserBrackets[i].get(
//                            looserBrackets[i].size()/2 + (j - (j % 2))
//                    ));
//
//                if (i == 1)
//                    bracketsToLooserBrackets.put(brackets[i].get(j), looserBrackets[i].get(
//                            looserBrackets[i].size()/2 + j)
//                    );
//
//                if (i == 2)
//                    bracketsToLooserBrackets.put(brackets[i].get(j), looserBrackets[i].get(
//                            j - (j % 2)
//                    ));
//
//            }
//        }
//

        if(brackets[0].size() == 8) {
            bracketsToLooserBrackets.put(brackets[0].get(0), looserBrackets[0].get(21));
            bracketsToLooserBrackets.put(brackets[0].get(1), looserBrackets[0].get(21));
            bracketsToLooserBrackets.put(brackets[0].get(2), looserBrackets[0].get(23));
            bracketsToLooserBrackets.put(brackets[0].get(3), looserBrackets[0].get(23));
            bracketsToLooserBrackets.put(brackets[0].get(4), looserBrackets[0].get(29));
            bracketsToLooserBrackets.put(brackets[0].get(5), looserBrackets[0].get(29));
            bracketsToLooserBrackets.put(brackets[0].get(6), looserBrackets[0].get(31));
            bracketsToLooserBrackets.put(brackets[0].get(7), looserBrackets[0].get(31));

            bracketsToLooserBrackets.put(brackets[1].get(0), looserBrackets[1].get(10));
            bracketsToLooserBrackets.put(brackets[1].get(1), looserBrackets[1].get(11));
            bracketsToLooserBrackets.put(brackets[1].get(2), looserBrackets[1].get(14));
            bracketsToLooserBrackets.put(brackets[1].get(3), looserBrackets[1].get(15));

//        bracketsToLooserBrackets.put(brackets[2].get(0),looserBrackets[2].get(4));
//        bracketsToLooserBrackets.put(brackets[2].get(1),looserBrackets[2].get(6));
            bracketsToLooserBrackets.put(brackets[2].get(0), looserBrackets[3].get(2));
            bracketsToLooserBrackets.put(brackets[2].get(1), looserBrackets[3].get(3));

//        bracketsToLooserBrackets.put(brackets[3].get(0),looserBrackets[3].get(0));
            bracketsToLooserBrackets.put(brackets[3].get(0), looserBrackets[5].get(0));

            toDraw.add(looserBrackets[1].get(10).getNextBracket());
            toDraw.add(looserBrackets[1].get(14).getNextBracket());
            toDraw.add(looserBrackets[3].get(2).getNextBracket());
        }


        if(brackets[0].size() == 4) {
            bracketsToLooserBrackets.put(brackets[0].get(0), looserBrackets[0].get(5));
            bracketsToLooserBrackets.put(brackets[0].get(1), looserBrackets[0].get(5));
            bracketsToLooserBrackets.put(brackets[0].get(2), looserBrackets[0].get(7));
            bracketsToLooserBrackets.put(brackets[0].get(3), looserBrackets[0].get(7));

            bracketsToLooserBrackets.put(brackets[1].get(0), looserBrackets[1].get(2));
            bracketsToLooserBrackets.put(brackets[1].get(1), looserBrackets[1].get(3));

            bracketsToLooserBrackets.put(brackets[2].get(0), looserBrackets[3].get(0));

            toDraw.add(looserBrackets[1].get(2).getNextBracket());
        }

        if(brackets[0].size() == 2) {
            bracketsToLooserBrackets.put(brackets[0].get(0), looserBrackets[0].get(0));
            bracketsToLooserBrackets.put(brackets[0].get(1), looserBrackets[0].get(0));

            bracketsToLooserBrackets.put(brackets[1].get(0), looserBrackets[1].get(0));
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
                    }
                    if (newValue == null){
                        finalBracket2.getMatch().setParticipant1(null);
                    }
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

    public Set<Bracket> getToDraw() {
        return toDraw;
    }

    public Bracket getFinalBracket() {
        return finalBracket;
    }

    public Bracket getFinalBracket2() {
        return finalBracket2;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        initializeListeners();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }
}
