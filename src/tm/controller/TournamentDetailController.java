package tm.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tm.controller.calendar.FullCalendarView;
import tm.controller.elimination.DoubleEliminationController;
import tm.controller.elimination.SingleEliminationController;
import tm.model.Participant;
import tm.model.tournament.*;
import java.net.URL;
import java.time.YearMonth;
import java.util.ResourceBundle;

public class TournamentDetailController implements Initializable {

    @FXML
    private Label tournamentNameLabel;
    @FXML
    private Label tournamentDateLabel;
    @FXML
    private Label tournamentSportLabel;
    @FXML
    private Label tournamentTypeLabel;
    @FXML
    private Label tournamentStateLabel;
    @FXML
    private Label tournamentWinnerLabel;
    @FXML
    private TableView<Participant> participantsTable;
    @FXML
    private TableColumn<Participant,String> participantNickCol;
    @FXML
    private TableColumn<Participant,String> participantFirstNameCol;
    @FXML
    private TableColumn<Participant,String> participantLastNameCol;
    @FXML
    private TableColumn<Participant,Integer> participantAgeCol;

    private enum tournamentStates {
        FINISHED("Ukončený"),
        EDITABLE("Upraviteľný");
        private String c;
        tournamentStates(String c){
            this.c = c;
        }
        public String getCaution(){
            return c;
        }
    }
    public static Tournament tournament;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tournamentNameLabel.setText(tournament.getName());
        tournamentDateLabel.setText(tournament.getDate().toString());
        tournamentTypeLabel.setText(tournament.getTypeString());
        tournamentSportLabel.setText(tournament.getSportType());
        if (tournament.isFinished())
            tournamentStateLabel.setText(tournamentStates.FINISHED.getCaution());
        else
            tournamentStateLabel.setText(tournamentStates.EDITABLE.getCaution());
        if (tournament.getTournamentWinner() != null)
            tournamentWinnerLabel.setText(tournament.getTournamentWinner().getNickName());
        setTableView();
    }

    @FXML
    public void handleEditButton(){
        if(tournament instanceof DoubleElimination) {
            DoubleEliminationController.doubleElimination = (DoubleElimination)tournament;
            ScreenController.activate("doubleElimination");
        }
        else if(tournament instanceof SingleElimination) {
            SingleEliminationController.singleElimination = (SingleElimination)tournament;
            ScreenController.activate("singleElimination");
        }
        else if(tournament instanceof SwissSystem) {
            SwissSystemController.swissSystem = (SwissSystem)tournament;
            ScreenController.activate("swissSystem");
        }
        else if(tournament instanceof FreeForAll) {
            FreeForAllController.freeForAll = (FreeForAll)tournament;
            ScreenController.activate("freeForAll");
        }
    }

    @FXML
    public void handleBackButton(){
        ScreenController.activate((new FullCalendarView(YearMonth.now()).getView()));
    }

    private void setTableView(){
        participantNickCol.prefWidthProperty().bind(participantsTable.widthProperty().multiply(0.3));
        participantFirstNameCol.prefWidthProperty().bind(participantsTable.widthProperty().multiply(0.3));
        participantLastNameCol.prefWidthProperty().bind(participantsTable.widthProperty().multiply(0.3));
        participantAgeCol.prefWidthProperty().bind(participantsTable.widthProperty().multiply(0.1));
        participantNickCol.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        participantFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        participantLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        participantAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        ObservableList<Participant> toShow = FXCollections.observableArrayList(tournament.getParticipants());
        participantsTable.getItems().clear();
        participantsTable.getItems().addAll(toShow);
    }

}
