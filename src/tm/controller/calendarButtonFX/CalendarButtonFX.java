package tm.controller.calendarButtonFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import tm.controller.ScreenController;
import tm.controller.TournamentDetailController;
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
        String name = tournament.getName();
        CalendarButtonUtils.loadFXML(this);
        if (name.length() > 20){
            name = name.substring(0,17) + "...";
        }
        tournamentBtn.setText(name);
        tournamentBtn.setStyle("-fx-background-color: " + tournament.getColor());
        tournamentBtn.setOnAction(
                e ->{
                    TournamentDetailController.tournament = this.tournament;
                    ScreenController.activate("tournamentDetail");
                }
        );

//        this.setOnMouseClicked(e -> this.tournament);

//        tournamentBtn.setStyle(":hover{-fx-background-color: " + tournament.getColor() + "}");
    }

}
