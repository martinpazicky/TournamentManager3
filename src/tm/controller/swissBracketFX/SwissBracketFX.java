package tm.controller.swissBracketFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tm.model.SwissBracket;

public class SwissBracketFX extends AnchorPane {
    @FXML
    private TextField participant1Score;
    @FXML
    private TextField participant2Score;
    @FXML
    private Label participant1Lbl;
    @FXML
    private Label participant2Lbl;
    @FXML
    private Button setResultBtn;

    @FXML
    private AnchorPane rootPane;


    private SwissBracket swissBracket;

    public SwissBracketFX(SwissBracket swissBracket) {
        this.swissBracket = swissBracket;
        SwissBracketUtils.loadFXML(this);
        participant1Lbl.setText(swissBracket.getMatch().getParticipant1().getValue().getNickName());
        participant2Lbl.setText(swissBracket.getMatch().getParticipant2().getValue().getNickName());

    }

    public void setResult(){
        String score1 = participant1Score.getText();
        String score2 = participant2Score.getText();
        if(score1 != null && !score1.trim().isEmpty() && score2 != null && !score2.trim().isEmpty() && isNumeric(score1) && isNumeric(score2))
        {

            swissBracket.getMatch().setParticipant1Score(Integer.valueOf(participant1Score.getText()));
            swissBracket.getMatch().setParticipant2Score(Integer.valueOf(participant2Score.getText()));
        }
    }

    public Button getSetResultBtn() {
        return setResultBtn;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public SwissBracket getSwissBracket() {
        return swissBracket;
    }

    public void setSwissBracket(SwissBracket swissBracket) {
        this.swissBracket = swissBracket;
    }

    public TextField getParticipant1Score() {
        return participant1Score;
    }

    public void setParticipant1Score(TextField participant1Score) {
        this.participant1Score = participant1Score;
    }

    public TextField getParticipant2Score() {
        return participant2Score;
    }

    public void setParticipant2Score(TextField participant2Score) {
        this.participant2Score = participant2Score;
    }
}
