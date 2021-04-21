package tm.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tm.model.database.Database;
import tm.model.tournament.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TournamentDetailController implements Initializable {

    @FXML
    private Label tournamentNameLabel;
    @FXML
    private Label tournamentDateLabel;
    @FXML
    private Label tournamentTypeLabel;

    public static Tournament tournament;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tournamentNameLabel.setText(tournament.getName());
        tournamentDateLabel.setText(tournament.getDate().toString());
        tournamentTypeLabel.setText(tournament.getTypeString());
    }

    @FXML
    public void handleEditButton(){
        if(tournament instanceof DoubleElimination) {
            DoubleEliminationController.doubleElimination = (DoubleElimination)tournament;
            ScreenController.activate("doubleElimination");
        }
        else if(tournament instanceof SingleElimination) {
            SingleEliminationController.singleElimination = (SingleElimination)tournament;
            ScreenController.activate("singleElimination");
        }
        else if(tournament instanceof SwissSystem) {
            SwissSystemController.swissSystem = (SwissSystem)tournament;
            ScreenController.activate("swissSystem");
        }
        else if(tournament instanceof FreeForAll) {
            FreeForAllController.freeForAll = (FreeForAll)tournament;
            ScreenController.activate("freeForAll");
        }
    }

}
