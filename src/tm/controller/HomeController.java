package tm.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import tm.controller.calendar.FullCalendarView;
import java.time.YearMonth;
import java.util.Locale;
import java.util.ResourceBundle;

public class HomeController {
    private ResourceBundle bundle;
    private Locale locale;

    @FXML
    private Button saveAndEndButton;
    @FXML
    private Button calendarButton;
    @FXML
    private Button createButton;

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

    @FXML
    public void handleSKButton(){
        loadLang("sk");
    }

    @FXML
    public void handleENButton(){
        loadLang("en");
    }

    private void loadLang(String lang){
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("lang", locale);
        createButton.setText(bundle.getString("create"));
        calendarButton.setText(bundle.getString("calendar"));
        saveAndEndButton.setText(bundle.getString("saveAndEnd"));
    }
}
