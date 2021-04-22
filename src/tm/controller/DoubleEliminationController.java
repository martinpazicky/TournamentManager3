package tm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import tm.controller.bracketFX.BracketFX;
import tm.controller.utility.EliminationsUtility;
import tm.model.Bracket;
import tm.model.Participant;
import tm.model.tournament.DoubleElimination;

import java.net.URL;
import java.util.*;
import java.util.List;

public class DoubleEliminationController implements Initializable {
    @FXML
    private AnchorPane rootAP;
    @FXML
    private Button backButton;
    @FXML
    private Button finishTournamentButton;
    @FXML
    private Label tournamentWinnerLabel;
    @FXML
    private Label tournamentStateLabel;

    private Map<Bracket,BracketFX> brToBrFX = new HashMap<>();

    private ObjectProperty<Participant> highlightedParticipant = new SimpleObjectProperty<>();

    double Y = 100;
    double maxX = 0;

    public static DoubleElimination doubleElimination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Bracket>[] brackets = doubleElimination.getBrackets();
        List<Bracket>[] looserBrackets = doubleElimination.getLooserBrackets();
        double dx = BracketFX.WIDTH + 150;
        double dy = BracketFX.HEIGHT + 20;
        renderBrackets(brackets,dx,dy);
        renderLoserBrackets(looserBrackets,dx,dy);
        renderFinalBrackets();
        EliminationsUtility.renderLines(brackets,brToBrFX,rootAP);
        EliminationsUtility.renderLines(looserBrackets,brToBrFX,rootAP);
        EliminationsUtility.renderUtilities(doubleElimination,brToBrFX,finishTournamentButton,
                backButton,tournamentWinnerLabel,tournamentStateLabel);
        rootAP.setPrefHeight(Math.max(Y + dy,ScreenController.main.getHeight()));
        rootAP.setPrefWidth(Math.max(maxX + dx,ScreenController.main.getWidth()));
    }

    private void renderFinalBrackets(){
        BracketFX brFX = new BracketFX(doubleElimination.getFinalBracket());
        BracketFX brFX2 = new BracketFX(doubleElimination.getFinalBracket2());
        rootAP.getChildren().addAll(brFX,brFX2);
        brToBrFX.put(doubleElimination.getFinalBracket(),brFX);
        brToBrFX.put(doubleElimination.getFinalBracket2(),brFX2);
        maxX += 250 + 200;
        brFX.setLayoutX(maxX);
        brFX.setLayoutY(Y/2);
        maxX += 200;
        brFX2.setLayoutX(maxX);
        maxX += 200;
        brFX2.setLayoutY(Y/2);
        EliminationsUtility.initializeHighlightListeners(brFX,highlightedParticipant);
        EliminationsUtility.initializeHighlightListeners(brFX2,highlightedParticipant);
        List<Bracket>[] finalBrackets = new ArrayList[1];
        finalBrackets[0] = new ArrayList<>();
        finalBrackets[0].add(doubleElimination.getFinalBracket());
        finalBrackets[0].add(doubleElimination.getFinalBracket2());
        EliminationsUtility.initializeEditButtonAction(doubleElimination,doubleElimination.getFinalBracket(),brFX,
                brToBrFX);
        EliminationsUtility.initializeEditButtonAction(doubleElimination,doubleElimination.getFinalBracket2(),brFX2,
                brToBrFX);
        EliminationsUtility.renderLines(finalBrackets,brToBrFX,rootAP);
    }

    private void renderLoserBrackets(List<Bracket>[] brackets, double dx, double dy){
        double x = 20;
        double maxY = 0;
        double maxX = 0;
        double y;

        double total = brackets[0].size() * dy;
        for(int i = 0; i < brackets.length; i++){
            y = Y;
            y += (total / brackets[i].size())/2;
            for (Bracket bracket : brackets[i]) {
                BracketFX brFX = new BracketFX(bracket);
                EliminationsUtility.initializeEditButtonAction(doubleElimination,bracket,brFX,brToBrFX);
                EliminationsUtility.initializeHighlightListeners(brFX,highlightedParticipant);
                brToBrFX.put(bracket,brFX);
                rootAP.getChildren().add(brFX);
                brFX.setLayoutX(x);
                brFX.setLayoutY(y);
                y += total / brackets[i].size();
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
        double y;
        for(int i = 0; i < brackets.length; i++){
            y = Y;
            y += Math.pow(2,i) * (dy/2);
            for (Bracket bracket : brackets[i]) {
                BracketFX brFX = new BracketFX(bracket);
                EliminationsUtility.initializeEditButtonAction(doubleElimination,bracket,brFX,brToBrFX);
                EliminationsUtility.initializeHighlightListeners(brFX,highlightedParticipant);
                brToBrFX.put(bracket,brFX);
                rootAP.getChildren().add(brFX);
                brFX.setLayoutX(x);
                brFX.setLayoutY(y);
                y += dy * Math.pow(2,i);
            }
            if (i == 0)
                maxY = y;
            x += dx;
        }
        Y = maxY + dy;
    }



}

