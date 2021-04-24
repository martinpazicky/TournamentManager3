package tm.controller.createTournament;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tm.controller.ScreenController;
import tm.controller.utility.Utils;
import tm.controller.utility.XMLParser;
import tm.model.Participant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChooseParticipantsTypeController {

    @FXML
    private TextField amountTextField;

    public static List<Participant> participants = new ArrayList<>();


    public void generateParticipants(){
        if (amountTextField.getText().equals(""))
            System.out.println("Braskou daj pocet ucastnikov");
        if (Utils.isInteger(amountTextField.getText())){
            int amount = Integer.parseInt(amountTextField.getText());
            for (int i = 1; i <= amount; i++ ){
                String name = "jozo" + i;
                Participant p = new Participant(name);
                participants.add(p);
            }
        }
        else {
            System.out.println("Davaj cislo braskou");
        }
        goToInfo();

    }


    public void createParticipants(){
        ScreenController.activate("createParticipants",ScreenController.newWindow);
    }


    public void importParticipants(){
        File f = new File("src/tm/resources/participants.xml");
        participants = XMLParser.parseParticipants(f);
//        for (Participant p : participants) {
//            System.out.println(p.getNickName());
//            System.out.println(p.getAge());
//            System.out.println(p.getFirstName());
//            System.out.println(p.getLastName());
//        }
        goToInfo();

    }

    public void goToInfo(){
        CreateTournamentInfo.participants = participants;
        ScreenController.activate("createTournamentInfo",ScreenController.newWindow);

    }



}
