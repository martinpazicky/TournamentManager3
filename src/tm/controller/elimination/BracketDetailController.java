package tm.controller.elimination;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tm.controller.ScreenController;
import tm.controller.utility.Utils;
import tm.model.Bracket;
import tm.view.AlertBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BracketDetailController implements Initializable {

    @FXML
    private Button SetWinnerP1Button;
    @FXML
    private Button SetWinnerP2Button;
    @FXML
    private Label winnerLabel;
    @FXML
    private TextField p1ScoreTF;
    @FXML
    private TextField p2ScoreTF;

    private static Bracket bracket;
    private static boolean editable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        p1ScoreTF.setEditable(editable);
        p2ScoreTF.setEditable(editable);
        if (bracket.getMatch().getParticipant1().getValue() != null)
            SetWinnerP1Button.setText(bracket.getMatch().getParticipant1().getValue().getNickName());
        if (bracket.getMatch().getParticipant2().getValue() != null)
            SetWinnerP2Button.setText(bracket.getMatch().getParticipant2().getValue().getNickName());
        ScreenController.newWindow.setOnCloseRequest(e -> handleSaveButton());
        try {
            if (bracket.getMatch().getParticipant1ScoreProperty().getValue() != -1)
                p1ScoreTF.setText(bracket.getMatch().getParticipant1ScoreProperty().getValue().toString());
            if (bracket.getMatch().getParticipant2ScoreProperty().getValue() != -1)
                p2ScoreTF.setText(bracket.getMatch().getParticipant2ScoreProperty().getValue().toString());
        }catch (NullPointerException ignored){}
        if (bracket.getMatch().getWinner().getValue() != null){
            winnerLabel.setText(bracket.getMatch().getWinner().getValue().getNickName());
        }
        ScreenController.newWindow.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ENTER == event.getCode()) {
                handleSaveButton();
            }
        });
    }

    @FXML
    public void handleSetWinnerP1Button(){
        if (editable){
            if (bracket.getMatch().getParticipant1().getValue() != null){
                bracket.setWinner(bracket.getMatch().getParticipant1().getValue());
                winnerLabel.setText(bracket.getMatch().getParticipant1().getValue().getNickName());
            }
            else AlertBox.displayError("Chyba","Hráč neexistuje");
        }
        else AlertBox.displayError("Chyba","Turnaj už nie je možné upravovať");
    }

    @FXML
    public void handleSetWinnerP2Button(){
        if (editable) {
            if (bracket.getMatch().getParticipant2().getValue() != null) {
                bracket.setWinner(bracket.getMatch().getParticipant2().getValue());
                winnerLabel.setText(bracket.getMatch().getParticipant2().getValue().getNickName());
            }
            else AlertBox.displayError("Chyba","Hráč neexistuje");
        }
        else AlertBox.displayError("Chyba","Turnaj už nie je možné upravovať");
    }

    @FXML
    public void handleResetButton(){
        if (editable) {
            winnerLabel.setText("-");
            bracket.unsetWinner();
        }
        else AlertBox.displayError("Chyba","Turnaj už nie je možné upravovať");
    }

    @FXML
    public void handleSaveButton(){
        if (Utils.isInteger(p1ScoreTF.getText()) && Utils.isInteger(p2ScoreTF.getText())){
            bracket.getMatch().setParticipant1Score(Integer.parseInt(p1ScoreTF.getText()));
            bracket.getMatch().setParticipant2Score(Integer.parseInt(p2ScoreTF.getText()));
        }
        ScreenController.newWindow.close();
    }

    public static Bracket getBracket() {
        return bracket;
    }

    public static void setBracket(Bracket bracket) {
        BracketDetailController.bracket = bracket;
    }

    public static boolean isEditable() {
        return editable;
    }

    public static void setEditable(boolean editable) {
        BracketDetailController.editable = editable;
    }
}
