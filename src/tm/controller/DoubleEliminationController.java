package tm.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import tm.controller.bracketFX.BracketFX;
import tm.controller.utility.EliminationsUtility;
import tm.model.Bracket;
import tm.model.database.Database;
import tm.model.tournament.DoubleElimination;

import java.net.URL;
import java.util.*;

public class DoubleEliminationController implements Initializable {
    @FXML
    private AnchorPane rootAP;

    private Map<Bracket,BracketFX> brToBrFX = new HashMap<>();

    double Y = 100;
    double maxX = 0;

    private DoubleElimination doubleElimination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        doubleElimination = (DoubleElimination) Database.tournaments.get(1);
        List<Bracket>[] brackets = doubleElimination.getBrackets();
        List<Bracket>[] looserBrackets = doubleElimination.getLooserBrackets();
        double dx = BracketFX.WIDTH + 150;
        double dy = BracketFX.HEIGHT + 20;
        double lineXBreakPoint = (dx - BracketFX.WIDTH) * (0.65) ;
        renderBrackets(brackets,dx,dy);
        renderLoserBrackets(looserBrackets,dx,dy);
        rootAP.setPrefHeight(10000);
        rootAP.setPrefWidth(5000);
        renderFinalBrackets();
        EliminationsUtility.renderLines(brackets,brToBrFX,rootAP);
        EliminationsUtility.renderLines(looserBrackets,brToBrFX,rootAP);
        List<Bracket>[] finalBrackets = new ArrayList[1];
        finalBrackets[0] = new ArrayList<>();
        finalBrackets[0].add(doubleElimination.getFinalBracket());
        finalBrackets[0].add(doubleElimination.getFinalBracket2());
        EliminationsUtility.renderLines(finalBrackets,brToBrFX,rootAP);
    }


    private void renderFinalBrackets(){
        BracketFX brFX = new BracketFX(doubleElimination.getFinalBracket());
        BracketFX brFX2 = new BracketFX(doubleElimination.getFinalBracket2());
        rootAP.getChildren().add(brFX);
        rootAP.getChildren().add(brFX2);
        brToBrFX.put(doubleElimination.getFinalBracket(),brFX);
        brToBrFX.put(doubleElimination.getFinalBracket2(),brFX2);
        brFX.setLayoutX(maxX+250+200);
        brFX.setLayoutY(Y/2);
        brFX2.setLayoutX(maxX+250+200+200);
        brFX2.setLayoutY(Y/2);
    }

    private void renderLoserBrackets(List<Bracket>[] brackets, double dx, double dy){
        double x = 20;
        double maxY = 0;
        double maxX = 0;
        double y;
        for(int i = 0; i < brackets.length; i++){
            y = Y;
            y += Math.pow(2,i) * (dy/2);
            for (Bracket bracket : brackets[i]) {
                if (doubleElimination.getBracketsToLooserBrackets().containsValue(bracket) ||
                        doubleElimination.getToDraw().contains(bracket)){
                    BracketFX brFX = new BracketFX(bracket);
                    brToBrFX.put(bracket,brFX);
                    rootAP.getChildren().add(brFX);
                    brFX.setLayoutX(x);
                    brFX.setLayoutY(y);
                }
                y += dy * Math.pow(2,i)/2;
            }
            if (i == 0)
                maxY = y;
            maxX = x;
            x += dx;
        }
        this.maxX = maxX;
        Y = maxY + dy;
        rootAP.setPrefHeight(maxY + dy);
        rootAP.setPrefWidth(maxX + dx);
    }

    private void renderBrackets(List<Bracket>[] brackets, double dx, double dy){
        double x = 20;
        double maxY = 0;
        double maxX = 0;
        double y;
        for(int i = 0; i < brackets.length; i++){
            y = Y;
            y += Math.pow(2,i) * (dy/2);
            for (Bracket bracket : brackets[i]) {
                BracketFX brFX = new BracketFX(bracket);
                brToBrFX.put(bracket,brFX);
                rootAP.getChildren().add(brFX);
                brFX.setLayoutX(x);
                brFX.setLayoutY(y);
                y += dy * Math.pow(2,i);
            }
            if (i == 0)
                maxY = y;
            maxX = x;
            x += dx;
        }
        Y = maxY + dy;
        rootAP.setPrefHeight(maxY + dy);
        rootAP.setPrefWidth(maxX + dx);
    }

}
