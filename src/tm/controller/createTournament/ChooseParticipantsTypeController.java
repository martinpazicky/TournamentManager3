package tm.controller.createTournament;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tm.controller.utility.Utils;
import tm.model.Participant;

import java.util.ArrayList;
import java.util.List;

public class ChooseParticipantsTypeController {

    @FXML
    private TextField amountTextField;


    public void generateParticipants(){
        if (amountTextField.getText().equals(""))
            System.out.println("Braskou daj pocet ucastnikov");
        if (Utils.isInteger(amountTextField.getText())){
            int amount = Integer.parseInt(amountTextField.getText());
            List<Participant> participants = new ArrayList<>();
            for (int i = 1; i <= amount; i++ ){
                String name = "jozo" + i;
                Participant p = new Participant(name);
                participants.add(p);
            }
        }
        else {
            System.out.println("Davaj cislo braskou");
        }

    }


    public void createParticipants(){
        if (amountTextField.getText().equals(""))
            System.out.println("Braskou daj pocet ucastnikov");
        if (Utils.isInteger(amountTextField.getText())){
            int amount = Integer.parseInt(amountTextField.getText());

        }
        else {
            System.out.println("Davaj cislo braskou");
        }

    }


    public void importParticipants(){
        //TODO IMPORT FXML
    }



}
