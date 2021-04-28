package tm.controller.calendar;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tm.controller.ScreenController;
import tm.controller.calendar.calendarButtonFX.CalendarButtonFX;
import tm.model.database.Database;
import tm.model.tournament.Tournament;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FullCalendarView {

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;

    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public FullCalendarView(YearMonth yearMonth) {
        currentYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.getStylesheets().add("tm/resources/css/calendar.css");
        calendar.setPrefSize(600, 800);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200,200);
                calendar.add(ap,j,i);
                allCalendarDays.add(ap);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{ new Text("Sunday"), new Text("Monday"), new Text("Tuesday"),
                                        new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
                                        new Text("Saturday") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
//            txt.setFill(Color.BLACK);
            txt.setStyle("-fx-font: 20 arial;");
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
            GridPane.setMargin(ap, new Insets(20,0,20,0));
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text("OPAA");
        calendarTitle.setStyle("-fx-font: 25 arial;");
        Button previousMonth = new Button("<<");

        previousMonth.getStylesheets().add("tm/resources/css/calendar.css");
        previousMonth.getStyleClass().add("month-buttons");

        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");

        nextMonth.getStylesheets().add("tm/resources/css/calendar.css");
        nextMonth.getStyleClass().add("month-buttons");

        nextMonth.setOnAction(e -> nextMonth());
        Button backButton = new Button();
        FontAwesomeIconView fontIconView = new FontAwesomeIconView();
        fontIconView.setGlyphName("ARROW_LEFT");
        fontIconView.setSize("25");
        fontIconView.setStyle("-fx-text-alignment: center;" +
                "-fx-fill:white;");
        backButton.setPrefWidth(60);
        backButton.setPrefHeight(35);
        backButton.setGraphic(fontIconView);
        backButton.setStyle("-fx-background-color:\n" +
                "            linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\n" +
                "            linear-gradient(#020b02, #3a3a3a),\n" +
                "            linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\n" +
                "            linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\n" +
                "            linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);");
        backButton.setOnAction(
                e -> ScreenController.activate("home")
        );
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        HBox.setMargin(calendarTitle, new Insets(0,20,0,20));
        titleBar.setPrefSize( ScreenController.stage.getWidth() - 120, 200);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth);
        HBox united = new HBox(backButton,titleBar);
        united.setMaxHeight(200);
        HBox.setMargin(backButton,new Insets(10,0,0,35));
        // Create the calendar view
        view = new VBox(united, dayLabels, calendar);
        backButton.setLayoutX(30);
        backButton.setLayoutY(30);
        VBox.setMargin(calendar, new Insets(0,30,40,30));
        VBox.setMargin(united, new Insets(10,0,0,0));
        VBox.setMargin(dayLabels, new Insets(0,30,0,30));

    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) {
        Map<LocalDate, AnchorPaneNode> dateToNode = new HashMap<>();


        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {

            ap.getChildren().clear();
            ap.setTempLayoutY(0);

            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            dateToNode.put(calendarDate, ap);
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            txt.setStyle("-fx-font: 20 arial;");
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));


        List<Tournament> tournamentsList = Database.tournaments.getItems();
        for (Tournament tournament : tournamentsList){
            if (dateToNode.get(tournament.getDate()) == null){
                continue;
            }
            AnchorPaneNode actualAP = dateToNode.get(tournament.getDate());
            actualAP.getChildren().removeAll();

            CalendarButtonFX calendarButtonFX = new CalendarButtonFX(tournament);

            calendarButtonFX.setLayoutX(40);
            calendarButtonFX.setLayoutY(actualAP.getTempLayoutY() + 5);
            actualAP.getChildren().add(calendarButtonFX);

            actualAP.setTempLayoutY(actualAP.getTempLayoutY() + 30);

        }
    }


    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
}
