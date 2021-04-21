package tm.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tm.model.Bracket;
import tm.model.Match;
import tm.model.Participant;
import tm.model.database.Database;
import tm.model.tournament.*;
import tm.calendar.FullCalendarView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static List<Participant> createParticipants(int amount){
        List<Participant> participants = new ArrayList<>();
        for (int i = 1; i <= amount; i++ ){
            String name = "jozo" + i;
            Participant p = new Participant(name);
            participants.add(p);
        }
        return participants;
    }

    private void initializeScreenController(Stage primaryStage, Scene scene){
        ScreenController.stage = primaryStage;
        ScreenController.main = scene;
        ScreenController.addScreen("home", "/tm/view/home.fxml");
        ScreenController.addScreen("singleElimination", "/tm/view/singleElimination.fxml");
        ScreenController.addScreen("doubleElimination", "/tm/view/doubleElimination.fxml");
        ScreenController.addScreen("freeForAll", "/tm/view/freeForAll.fxml");
        ScreenController.addScreen("swissSystem", "/tm/view/swissSystem.fxml");
        ScreenController.addScreen("bracketDetail", "/tm/view/bracketDetail.fxml");
        ScreenController.addScreen("calendar", "/tm/calendar/fullCalendar.fxml");
        ScreenController.addScreen("tournamentDetail", "/tm/view/tournamentDetail.fxml");
        ScreenController.addScreen("createTournamentType", "/tm/view/createTournamentType.fxml");
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/tm/view/home.fxml"));
        Scene scene = new Scene(root, 1400, 900);
        initializeScreenController(primaryStage,scene);
        primaryStage.setTitle("TM");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinWidth(1400);
        primaryStage.setMinHeight(900);
    }

    public static void main(String[] args) {

//        List<Participant> participants = createParticipants(8);
//        FreeForAll freeForAll = new FreeForAll("Tabulka", participants);
//        List<Bracket>[] brackets2 = freeForAll.getBrackets();
//        Bracket r0c1 = brackets2[0].get(1);
//        Bracket r0c2 = brackets2[0].get(2);
//        Bracket r1c2 = brackets2[1].get(2);
//        freeForAll.setWinner(r0c1, 1);
//        freeForAll.setWinner(r0c2, 1);
//        freeForAll.setWinner(r1c2, 1);
//        Participant winner = freeForAll.countWinner();
//        singleEliminationTest();


        testElimination();
//        testTable();
//        testSwiss();

//
//        Database.loadAll();
        List<Tournament> t = Database.tournaments.getItems();
        launch(args);
        Database.saveAll();

    }

    private static void testTable(){
        List<Participant> participants = Main.createParticipants(8);
        FreeForAll freeForAll = new FreeForAll("Fajnovy turnaj",participants, 2);

        LocalDate localDate = LocalDate.now();
        freeForAll.setDate(localDate);


        Database.tournaments.add(freeForAll);
    }

    private static void testSwiss(){
        List<Participant> participants = Main.createParticipants(8);
        SwissSystem swissSystem = new SwissSystem("Sak vis ako",participants);

        LocalDate localDate = LocalDate.now();
        swissSystem.setDate(localDate);


        Database.tournaments.add(swissSystem);
    }

    private static void testElimination(){
        List<Participant> participants = Main.createParticipants(8);
        SingleElimination de = new SingleElimination("turnaj", participants);
//        DoubleElimination de = new DoubleElimination("Fakt velky turnaj ",participants);
        de.getBrackets()[0].get(0).setWinner(de.getBrackets()[0].get(0).getMatch().getParticipant1().getValue());
        de.getBrackets()[0].get(1).setWinner(de.getBrackets()[0].get(1).getMatch().getParticipant1().getValue());
        de.getBrackets()[0].get(2).setWinner(de.getBrackets()[0].get(2).getMatch().getParticipant1().getValue());
        de.getBrackets()[0].get(3).setWinner(de.getBrackets()[0].get(3).getMatch().getParticipant1().getValue());
//        de.getBrackets()[0].get(4).setWinner(de.getBrackets()[0].get(4).getMatch().getParticipant1().getValue());
//        de.getBrackets()[0].get(5).setWinner(de.getBrackets()[0].get(5).getMatch().getParticipant1().getValue());
//        de.getBrackets()[0].get(6).setWinner(de.getBrackets()[0].get(6).getMatch().getParticipant1().getValue());
//        de.getBrackets()[0].get(7).setWinner(de.getBrackets()[0].get(7).getMatch().getParticipant1().getValue());

//        de.getBrackets()[1].get(0).setWinner(de.getBrackets()[1].get(0).getMatch().getParticipant1().getValue());
//        de.getBrackets()[1].get(1).setWinner(de.getBrackets()[1].get(1).getMatch().getParticipant1().getValue());
//        de.getBrackets()[1].get(2).setWinner(de.getBrackets()[1].get(2).getMatch().getParticipant1().getValue());
//        de.getBrackets()[1].get(3).setWinner(de.getBrackets()[1].get(3).getMatch().getParticipant1().getValue());

        LocalDate localDate = LocalDate.now();
        de.setDate(localDate);


        Database.tournaments.add(de);

    }
}
