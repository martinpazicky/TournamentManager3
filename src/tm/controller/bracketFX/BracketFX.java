package tm.controller.bracketFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import tm.controller.BracketDetailController;
import tm.controller.ScreenController;
import tm.model.Bracket;
import tm.model.Participant;

/**
 * FX Component representing bracket
 * author: @martin
 */
public class BracketFX extends AnchorPane {

    @FXML
    private Label participant1Lbl;
    @FXML
    private Label participant2Lbl;
    @FXML
    private AnchorPane rootPane;
    private Bracket bracket;

    public static final double WIDTH = 150;
    public static final double HEIGHT = 80;


    @FXML
    private void initialize() {
        this.bracket.getMatch().getParticipant1().addListener(
                (observable, oldValue, newValue) ->
                {
                    if(newValue != null) {
                        this.participant1Lbl.setText(newValue.getName());
                    }
                    else{
                        this.participant1Lbl.setText("");
                    }
                }
        );
        this.bracket.getMatch().getParticipant2().addListener(
                (observable, oldValue, newValue) ->
                {
                    if(newValue != null) {
                        this.participant2Lbl.setText(newValue.getName());
                    }
                    else{
                        this.participant2Lbl.setText("");
                    }
                }
        );
        this.bracket.getMatch().getWinner().addListener(
                (observable, oldValue, newValue) -> highlightWinner()
        );
    }

    public BracketFX(Bracket bracket) {
        this.bracket = bracket;
        FXMLUtils.loadFXML(this);
        if (bracket.getMatch().getParticipant1().getValue() != null)
            participant1Lbl.setText(bracket.getMatch().getParticipant1().getValue().getName());
        if (bracket.getMatch().getParticipant2().getValue() != null)
            participant2Lbl.setText(bracket.getMatch().getParticipant2().getValue().getName());
        highlightWinner();
    }

    @FXML
    public void handleEditButton(){
        BracketDetailController.setBracket(bracket);
        ScreenController.activateInNewWindow("bracketDetail",500,500);
    }

    public void highlightWinner(){
        Participant p1 = bracket.getMatch().getParticipant1().getValue();
        Participant p2 = bracket.getMatch().getParticipant2().getValue();
        Participant winner = bracket.getMatch().getWinner().getValue();
        if (winner != null) {
            if (winner.equals(p1)) {
                this.participant1Lbl.setStyle("-fx-text-fill: green; -fx-font-weight: bold");
                this.participant2Lbl.setStyle("-fx-text-fill: black; -fx-font-weight: normal");
            }
            else if (winner.equals(p2)) {
                this.participant2Lbl.setStyle("-fx-text-fill: green; -fx-font-weight: bold");
                this.participant1Lbl.setStyle("-fx-text-fill: black; -fx-font-weight: normal");
            }
        }else {
            this.participant1Lbl.setStyle("-fx-text-fill: black; -fx-font-weight: normal");
            this.participant2Lbl.setStyle("-fx-text-fill: black; -fx-font-weight: normal");

        }
    }

    public Bracket getBracket() {
        return bracket;
    }

    public Label getParticipant1Lbl() {
        return participant1Lbl;
    }

    public void setParticipant1Lbl(Label participant1Lbl) {
        this.participant1Lbl = participant1Lbl;
    }

    public Label getParticipant2Lbl() {
        return participant2Lbl;
    }

    public void setParticipant2Lbl(Label participant2Lbl) {
        this.participant2Lbl = participant2Lbl;
    }

}

