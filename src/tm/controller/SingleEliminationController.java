package tm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import static jdk.nashorn.internal.objects.NativeMath.max;

public class SingleEliminationController implements Initializable {

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

    public static SingleElimination singleElimination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Bracket>[] brackets = singleElimination.getBrackets();
        double dx = BracketFX.WIDTH + 150;
        double dy = BracketFX.HEIGHT + 20;
        renderBrackets(brackets,dx,dy);
        EliminationsUtility.renderLines(brackets,brToBrFX,rootAP);
        EliminationsUtility.renderUtilities(singleElimination,brToBrFX,finishTournamentButton,
                backButton,tournamentWinnerLabel,tournamentStateLabel);
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
                brToBrFX.put(bracket,brFX);
                EliminationsUtility.initializeEditButtonAction(singleElimination,bracket,brFX,brToBrFX);
                EliminationsUtility.initializeHighlightListeners(brFX,highlightedParticipant);
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
        rootAP.setPrefHeight(max(maxY + dy,ScreenController.main.getHeight()));
        rootAP.setPrefWidth(max(maxX + dx,ScreenController.main.getWidth()));
    }


}

