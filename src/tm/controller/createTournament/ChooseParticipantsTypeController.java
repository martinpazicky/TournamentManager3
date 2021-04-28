package tm.controller.createTournament;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tm.controller.ScreenController;
import tm.controller.utility.Utils;
import tm.controller.utility.XMLParser;
import tm.model.Participant;
import tm.view.AlertBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChooseParticipantsTypeController {

    @FXML
    private TextField amountTextField;

    public static List<Participant> participants = new ArrayList<>();


    public void generateParticipants(){
        if (amountTextField.getText().equals(""))
            AlertBox.displayError("Chyba", "Zadajte počet účastníkov pre vygenerovanie.");
        else if (Utils.isInteger(amountTextField.getText())){
            int amount = Integer.parseInt(amountTextField.getText());
            for (int i = 1; i <= amount; i++ ){
                String name = "player" + i;
                Participant p = new Participant(name);
                participants.add(p);
            }
            goToInfo();
        }
        else {
            AlertBox.displayError("Chyba", "Počet účastníkov musí byť zadaný ako číslo.");
        }
    }


    public void createParticipants(){
        ScreenController.activate("createParticipants",ScreenController.newWindow);
    }


    public void importParticipants(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "(*.xml)","*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File chosenFile = fileChooser.showOpenDialog(ScreenController.stage);
        if (chosenFile != null && chosenFile.exists()){
            participants = XMLParser.parseParticipants(chosenFile);
            goToInfo();
        }
    }

    public void goToInfo(){
        CreateTournamentInfo.participants = participants;
        ScreenController.activate("createTournamentInfo",ScreenController.newWindow);

    }



}
