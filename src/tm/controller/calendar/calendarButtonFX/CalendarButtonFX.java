package tm.controller.calendar.calendarButtonFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import tm.controller.ScreenController;
import tm.controller.TournamentDetailController;
import tm.model.database.TournamentTypeMeta;
import tm.model.tournament.*;


public class CalendarButtonFX extends AnchorPane {
    @FXML
    private Button tournamentBtn;
    @FXML
    private AnchorPane rootPane;

    private Tournament tournament;

    private String getColor() {
        if (tournament instanceof FreeForAll) {
            return TournamentTypeMeta.getRoundRobinColor();
        } else if (tournament instanceof DoubleElimination) {
            return TournamentTypeMeta.getDoubleEliminationColor();
        } else if (tournament instanceof SingleElimination) {
            return TournamentTypeMeta.getSingleEliminationColor();
        } else if (tournament instanceof SwissSystem) {
            return TournamentTypeMeta.getSwissSystemColor();
        }
        return TournamentTypeMeta.getRoundRobinColor();
    }

    public CalendarButtonFX(Tournament tournament) {
        this.tournament = tournament;
        String name = tournament.getName();
        CalendarButtonUtils.loadFXML(this);
        if (name.length() > 20){
            name = name.substring(0,17) + "...";
        }
        tournamentBtn.setText(name);

        tournamentBtn.setStyle("-fx-background-color: " + getColor());
        tournamentBtn.setOnAction(
                e ->{
                    TournamentDetailController.tournament = this.tournament;
                    ScreenController.activate("tournamentDetail");
                }
        );
    }

}
