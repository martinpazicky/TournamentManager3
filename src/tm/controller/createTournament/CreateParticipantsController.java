package tm.controller.createTournament;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tm.controller.ScreenController;
import tm.model.Participant;
import tm.view.AlertBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateParticipantsController implements Initializable {

    private List<Participant> participants = new ArrayList<>();

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField sureNameTextField;
    @FXML
    private TextField nicknameTextField;

    @FXML
    private TableView<Participant> participantsTable;
    @FXML
    private TableColumn<Participant,String> nickNameCol;
    @FXML
    private TableColumn<Participant,String> firstNameCol;
    @FXML
    private TableColumn<Participant,String> lastNameCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    //Add buttom clicked
    public void addButtonClicked() {
        String firstName = nameTextField.getText();
        String lastName = sureNameTextField.getText();
        String nickName = nicknameTextField.getText();

        if( firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty() || nickName == null || nickName.trim().isEmpty())
        {
            AlertBox.displayError("CHUBA", "Vyplnte prosím všetky polia.");
        }
        else
        {
            Participant participant = new Participant(nickName, firstName, lastName);
            participants.add(participant);
            participantsTable.getItems().add(participant);
            nameTextField.clear();
            sureNameTextField.clear();
            nicknameTextField.clear();
        }
    }

    //Delete buttom clicked
    public void deleteButtonClicked() {
        Participant selected;
        selected = participantsTable.getSelectionModel().getSelectedItem();

        if (selected != null){
            ObservableList<Participant> selectedParticipants, allParticipants;
            allParticipants = participantsTable.getItems();
            selectedParticipants = participantsTable.getSelectionModel().getSelectedItems();

            for(Participant participant : selectedParticipants){
                participants.remove(participant);
            }

            selectedParticipants.forEach(allParticipants::remove);
        }
        else
        {
            AlertBox.displayError("CHUBA", "Pred kliknutim na zmazat, oznacte riadok, ktory si prajete zmazat.");
        }
    }

    public void setTable(){
        participantsTable.getStylesheets().add("tm/resources/css/table.css");
        participantsTable.setPlaceholder(new Label("Pridajte nových účastníkov :)"));

        participantsTable.getItems().clear();
        nickNameCol.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    }

    public void nextScreen(){
        CreateTournamentInfo.participants = participants;
        ScreenController.activate("createTournamentInfo",ScreenController.newWindow);
    }
}
