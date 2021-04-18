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
        participant1Lbl.setText(swissBracket.getMatch().getParticipant1().getValue().getName());
        participant2Lbl.setText(swissBracket.getMatch().getParticipant2().getValue().getName());

    }

    public void setResult(){
        swissBracket.getMatch().setParticipant1Score(Integer.valueOf(participant1Score.getText()));
        swissBracket.getMatch().setParticipant2Score(Integer.valueOf(participant2Score.getText()));
//        swissSystemController.addPoints(swissBracket);
    }

    public Button getSetResultBtn() {
        return setResultBtn;
    }

    public SwissBracket getSwissBracket() {
        return swissBracket;
    }

    public void setSwissBracket(SwissBracket swissBracket) {
        this.swissBracket = swissBracket;
    }
}
