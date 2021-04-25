package tm.controller.createTournament;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tm.controller.ScreenController;
import tm.controller.SwissSystemController;
import tm.controller.TournamentDetailController;
import tm.controller.utility.Utils;
import tm.model.Participant;
import tm.model.database.Database;
import tm.model.database.TournamentTypeMeta;
import tm.model.tournament.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateTournamentInfo {
    public static String tournamentType;
    public static List<Participant> participants;
    public static int numOfAllRounds = 1;

    @FXML
    private TextField numOfRounds;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField sportTextField;




    public void checkInfo(){
        String rounds = numOfRounds.getText();
        String name = nameTextField.getText();
        if( name == null || name.trim().isEmpty())
        {
            //TODO AlERT BOX
        }
        else
        {
            if(rounds.equals("")){
                finish(name);
            }
            else if (Utils.isInteger(rounds)) {
                numOfAllRounds = Integer.parseInt(rounds);
                finish(name);
            }
            else {
//                root.controller.AlertBox.display("CHYBA","Cena musí byť zadaná ako číslo.");
//                TODO ALERT BOX
            }
        }
    }


    public void finish(String name){
        LocalDate localDate = LocalDate.now();
        if (tournamentType.equals(TournamentTypeMeta.getSwissSystemName())){
            SwissSystem swissSystem = new SwissSystem(name, participants);
            swissSystem.setDate(localDate);
            swissSystem.setSportType(sportTextField.getText());
            Database.tournaments.add(swissSystem);
            TournamentDetailController.tournament = swissSystem;
            ScreenController.activate("tournamentDetail");
        }
        else if (tournamentType.equals(TournamentTypeMeta.getRoundRobinName())){
            FreeForAll freeForAll = new FreeForAll(name, participants, numOfAllRounds);
            freeForAll.setDate(localDate);
            freeForAll.setSportType(sportTextField.getText());
            Database.tournaments.add(freeForAll);
            TournamentDetailController.tournament = freeForAll;
            ScreenController.activate("tournamentDetail");
        }
        else if (tournamentType.equals(TournamentTypeMeta.getDoubleEliminationName())){
            DoubleElimination doubleElimination = new DoubleElimination(name, participants);
            doubleElimination.setDate(localDate);
            doubleElimination.setSportType(sportTextField.getText());
            Database.tournaments.add(doubleElimination);
            TournamentDetailController.tournament = doubleElimination;
            ScreenController.activate("tournamentDetail");
        }
        else if (tournamentType.equals(TournamentTypeMeta.getSingleEliminationName())){
            SingleElimination singleElimination = new SingleElimination(name, participants);
            singleElimination.setDate(localDate);
            singleElimination.setSportType(sportTextField.getText());
            Database.tournaments.add(singleElimination);
            TournamentDetailController.tournament = singleElimination;
            ScreenController.activate("tournamentDetail");
        }
        Stage stage = (Stage) numOfRounds.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
