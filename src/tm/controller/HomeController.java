package tm.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import tm.calendar.FullCalendarView;

import java.awt.*;
import java.net.URL;
import java.time.YearMonth;

public class HomeController {

    @FXML
    public void handleSingleEliminationButton(){
        ScreenController.activate("singleElimination");
    }

    @FXML
    public void handleDoubleEliminationButton(){
        ScreenController.activate("doubleElimination");
    }

    @FXML
    public void handleFreeForAllButton(){
        ScreenController.activate("freeForAll");
    }

    @FXML
    public void handleSwissSystemButton(){
        ScreenController.activate("swissSystem");
    }

    @FXML
    public void handleCreateButton(){
        ScreenController.activateInNewWindow("createTournamentType", 733, 450);
    }

    @FXML
    public void handleCalendarButton(){
        ScreenController.main.setRoot((new FullCalendarView(YearMonth.now()).getView()));;
    }

}
