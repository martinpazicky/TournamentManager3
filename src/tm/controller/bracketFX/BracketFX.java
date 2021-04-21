package tm.controller.bracketFX;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import tm.controller.BracketDetailController;
import tm.controller.ScreenController;
import tm.model.Bracket;
import tm.model.Participant;

import java.util.ArrayList;
import java.util.List;

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
    private Button editButton;
    @FXML
    private AnchorPane rootPane;
    private Bracket bracket;
    private List<Line> lines = new ArrayList<>();

    public static final double WIDTH = 175;
    public static final double HEIGHT = 80;


    @FXML
    private void initialize() {
        this.bracket.getMatch().getParticipant1().addListener(
                (observable, oldValue, newValue) ->
                {
                    if(newValue != null) {
                        this.participant1Lbl.setText(newValue.getNickName());
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
                        this.participant2Lbl.setText(newValue.getNickName());
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
            participant1Lbl.setText(bracket.getMatch().getParticipant1().getValue().getNickName());
        if (bracket.getMatch().getParticipant2().getValue() != null)
            participant2Lbl.setText(bracket.getMatch().getParticipant2().getValue().getNickName());
        highlightWinner();
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

    public List<Line> getLines() {
        return lines;
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public Bracket getBracket() {
        return bracket;
    }

    public Label getParticipant1Lbl() {
        return participant1Lbl;
    }

    public Label getParticipant2Lbl() {
        return participant2Lbl;
    }

    public Button getEditButton() {
        return editButton;
    }
}

