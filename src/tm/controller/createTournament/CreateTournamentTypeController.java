package tm.controller.createTournament;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tm.controller.ScreenController;
import tm.model.database.TournamentTypeMeta;
import tm.model.tournament.Tournament;

import java.net.URL;
import java.util.ResourceBundle;


public class CreateTournamentTypeController implements Initializable {

    public static Tournament tournament;

    @FXML private javafx.scene.control.Button nextStepButton;

    @FXML
    private Label typeDescription;
    @FXML
    private Label typeName;
    @FXML
    private Label participants;
    @FXML
    private Label suitsFor;
    @FXML
    private Label alsoKnownAs;
    @FXML
    private AnchorPane primaryAP;

    public void swissSystemDetail(){
        primaryAP.setStyle("-fx-background-color: " + TournamentTypeMeta.getSwissSystemSecondaryColor() + ";");
        typeDescription.setText(TournamentTypeMeta.getSwissSystemDescription());
        typeName.setText(TournamentTypeMeta.getSwissSystemName());
        participants.setText(TournamentTypeMeta.getSwissSystemParticipants());
        suitsFor.setText(TournamentTypeMeta.getSwissSystemSuitsFor());
        alsoKnownAs.setText(TournamentTypeMeta.getSwissSystemAlsoKnownAs());
    }
    public void roundRobinDetail(){
        primaryAP.setStyle("-fx-background-color: " + TournamentTypeMeta.getRoundRobinSecondaryColor() + ";");
        typeDescription.setText(TournamentTypeMeta.getRoundRobinDescription());
        typeName.setText(TournamentTypeMeta.getRoundRobinName());
        participants.setText(TournamentTypeMeta.getRoundRobinParticipants());
        suitsFor.setText(TournamentTypeMeta.getRoundRobinSuitsFor());
        alsoKnownAs.setText(TournamentTypeMeta.getRoundRobinAlsoKnownAs());
    }
    public void singleEliminationDetail(){
        primaryAP.setStyle("-fx-background-color: " + TournamentTypeMeta.getSingleEliminationSecondaryColor() + ";");
        typeDescription.setText(TournamentTypeMeta.getSingleEliminationDescription());
        typeName.setText(TournamentTypeMeta.getSingleEliminationName());
        participants.setText(TournamentTypeMeta.getSingleEliminationParticipants());
        suitsFor.setText(TournamentTypeMeta.getSingleEliminationParticipants());
        alsoKnownAs.setText(TournamentTypeMeta.getSingleEliminationAlsoKnownAs());
    }
    public void doubleEliminationDetail(){
        primaryAP.setStyle("-fx-background-color: " + TournamentTypeMeta.getDoubleEliminationSecondaryColor() + ";");
        typeDescription.setText(TournamentTypeMeta.getDoubleEliminationDescription());
        typeName.setText(TournamentTypeMeta.getDoubleEliminationName());
        participants.setText(TournamentTypeMeta.getDoubleEliminationParticipants());
        suitsFor.setText(TournamentTypeMeta.getDoubleEliminationSuitsFor());
        alsoKnownAs.setText(TournamentTypeMeta.getDoubleEliminationAlsoKnownAs());
    }

    @FXML
    private void nextStepButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) nextStepButton.getScene().getWindow();
        // do what you have to do
        stage.close();
        ScreenController.activateInNewWindow("chooseParticipantsType", 733, 450);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeDescription.setWrapText(true);
        alsoKnownAs.setWrapText(true);
        suitsFor.setWrapText(true);
        participants.setWrapText(true);
        swissSystemDetail();
    }
}
