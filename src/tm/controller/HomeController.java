package tm.controller;

import javafx.fxml.FXML;
import tm.controller.calendar.FullCalendarView;

import java.time.YearMonth;

public class HomeController {

    @FXML
    public void handleCreateButton(){
        ScreenController.stage.setWidth(1400);
        ScreenController.stage.setHeight(900);
        ScreenController.activateInNewWindow("createTournamentType", 733, 450);
    }

    @FXML
    public void handleCalendarButton(){
        ScreenController.stage.setWidth(1400);
        ScreenController.stage.setHeight(900);
        ScreenController.activate((new FullCalendarView(YearMonth.now()).getView()));

    }

}
