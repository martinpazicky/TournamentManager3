package tm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import tm.controller.bracketFX.BracketFX;
import tm.controller.utility.EliminationsUtility;
import tm.model.Bracket;
import tm.model.Participant;
import tm.model.database.Database;
import tm.model.tournament.SingleElimination;
import java.net.URL;
import java.util.*;

public class SingleEliminationController implements Initializable {

    @FXML
    private AnchorPane rootAP;

    private Map<Bracket,BracketFX> brToBrFX = new HashMap<>();

    private ObjectProperty<Participant> highlightedParticipant = new SimpleObjectProperty<>();

    private SingleElimination singleElimination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singleElimination = (SingleElimination) Database.tournaments.get(1);
        List<Bracket>[] brackets = singleElimination.getBrackets();
        double dx = BracketFX.WIDTH + 150;
        double dy = BracketFX.HEIGHT + 20;
        double lineXBreakPoint = (dx - BracketFX.WIDTH) * (0.65) ;
        renderBrackets(brackets,dx,dy);
        EliminationsUtility.renderLines(brackets,brToBrFX,rootAP);
    }

    private void renderBrackets(List<Bracket>[] brackets, double dx, double dy){
        double x = 20;
        double maxY = 0;
        double maxX = 0;
        double y;
        for(int i = 0; i < brackets.length; i++){
            y = 100;
            y += Math.pow(2,i) * (dy/2);
            for (Bracket bracket : brackets[i]) {
                BracketFX brFX = new BracketFX(bracket);
                EliminationsUtility.initializeHighlightListeners(brFX,highlightedParticipant);
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
        rootAP.setPrefHeight(maxY + dy);
        rootAP.setPrefWidth(maxX + dx);
    }


}

