package tm.controller.calendarButtonFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import tm.controller.swissBracketFX.SwissBracketUtils;
import tm.model.SwissBracket;
import tm.model.tournament.Tournament;


public class CalendarButtonFX extends AnchorPane {
    @FXML
    private Button tournamentBtn;
    @FXML
    private AnchorPane rootPane;

    private Tournament tournament;

    public CalendarButtonFX(Tournament tournament) {
        this.tournament = tournament;
        CalendarButtonUtils.loadFXML(this);
        tournamentBtn.setText(tournament.getName());
        tournamentBtn.setStyle("-fx-background-color: " + tournament.getColor());
//        tournamentBtn.setStyle(":hover{-fx-background-color: " + tournament.getColor() + "}");
    }

}
