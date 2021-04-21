package tm.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tm.model.Bracket;

import java.net.URL;
import java.util.ResourceBundle;

public class BracketDetailController implements Initializable {

    @FXML
    private Label p1Label;
    @FXML
    private Label p2Label;

    private static Bracket bracket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (bracket.getMatch().getParticipant1().getValue() != null)
            p1Label.setText(bracket.getMatch().getParticipant1().getValue().getNickName());
        if (bracket.getMatch().getParticipant2().getValue() != null)
            p2Label.setText(bracket.getMatch().getParticipant2().getValue().getNickName());
    }

    @FXML
    public void handleSetWinnerP1Button(){
        bracket.setWinner(bracket.getMatch().getParticipant1().getValue());
    }

    @FXML
    public void handleSetWinnerP2Button(){
        bracket.setWinner(bracket.getMatch().getParticipant2().getValue());
    }

    @FXML
    public void handleResetButton(){
        bracket.unsetWinner();
    }
    public static Bracket getBracket() {
        return bracket;
    }

    public static void setBracket(Bracket bracket) {
        BracketDetailController.bracket = bracket;
    }

}
