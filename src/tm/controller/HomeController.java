package tm.controller;

import javafx.fxml.FXML;
import tm.controller.calendar.FullCalendarView;
import tm.model.database.Database;

import java.time.YearMonth;

public class HomeController {

    @FXML
    public void handleCreateButton(){
        ScreenController.activateInNewWindow("createTournamentType", 733, 450);
    }

    @FXML
    public void handleCalendarButton(){
        ScreenController.activate((new FullCalendarView(YearMonth.now()).getView()));
    }

    @FXML
    public void handleSaveAndEndButton(){
        ScreenController.stage.close();
    }

}
