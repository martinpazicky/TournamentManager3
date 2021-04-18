package tm.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import tm.controller.bracketFX.BracketFX;
import tm.controller.swissBracketFX.SwissBracketFX;
import tm.controller.tableFX.CellFX;
import tm.model.Participant;
import tm.model.ParticipantRecord;
import tm.model.SwissBracket;
import tm.model.tournament.SwissSystem;

import java.net.URL;
import java.util.*;

public class SwissSystemController implements Initializable {

    @FXML
    private AnchorPane rootAP;

    public Integer roundCounter = 1;

    @FXML
    private TableView<ParticipantRecord> participantsTable;
    @FXML
    private TableColumn<ParticipantRecord,String> nameCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> pointsCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> matchesPlayedCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> numOfWinsCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> numOfLossesCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> numOfDrawsCol;
    @FXML
    private TableColumn<ParticipantRecord, Integer> gRankCol;

    private SwissSystem swissSystem;
    private List<SwissBracket>[] brackets;
    private List<Participant> participants;

    private double yLayout = 0;

    private Map<Participant, ParticipantRecord> participantsToRecords = new HashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int amount = 5;
        participants = Main.createParticipants(amount);

        swissSystem = new SwissSystem("turnaj2", participants);
        brackets = swissSystem.getBrackets();

        AnchorPane anchorPane = new AnchorPane();

        for(int i = 0; i < brackets[0].size(); i++){

            AnchorPane pane = new AnchorPane();

            Label label = new Label(" " + "Round" + " " + roundCounter);
            label.setWrapText(true);
            label.setFont(new Font(20.0));
            label.setLayoutY(40);
            label.setLayoutX(20);

            anchorPane.getChildren().add(label);

            SwissBracket actualBracket;

            actualBracket =  brackets[0].get(i);
            SwissBracketFX swissBracketFX = new SwissBracketFX(actualBracket);
            swissBracketFX.getSetResultBtn().setOnAction(event -> {
                swissBracketFX.setResult();
                addPoints(swissBracketFX.getSwissBracket());
            });
            pane.getChildren().add(swissBracketFX);

            anchorPane.getChildren().add(pane);

            yLayout = yLayout + 80;
            pane.setLayoutY(yLayout);
        }

        yLayout = yLayout + 80;

        rootAP.getChildren().addAll(anchorPane);
        roundCounter =+ 1;
        setTable();
    }


    public void printNextRound(){

        AnchorPane anchorPane = new AnchorPane();

        Label label = new Label(" " + "Round" + " " + (roundCounter + 1));
        label.setWrapText(true);
        label.setFont(new Font(20.0));
        label.setLayoutY(yLayout + 40);
        label.setLayoutX(20);

        anchorPane.getChildren().add(label);

        for(int i = 0; i < brackets[0].size(); i++){

            AnchorPane pane = new AnchorPane();

            SwissBracket actualBracket;

            actualBracket =  brackets[roundCounter].get(i);
            SwissBracketFX swissBracketFX = new SwissBracketFX(actualBracket);
            swissBracketFX.getSetResultBtn().setOnAction(event -> {
                swissBracketFX.setResult();
                addPoints(swissBracketFX.getSwissBracket());
            });
            pane.getChildren().add(swissBracketFX);

            anchorPane.getChildren().add(pane);

            yLayout = yLayout + 80;
            pane.setLayoutY(yLayout);
        }

        yLayout = yLayout + 80;

        rootAP.getChildren().addAll(anchorPane);
        roundCounter = roundCounter + 1;
    }


    public void setTable() {
        for (Participant participant : participants){
            ParticipantRecord participantRecord = new ParticipantRecord(participant);
            participantsToRecords.put(participant,participantRecord);
        }

        participantsTable.getItems().clear();
        participantsTable.setItems(getParticipantsRecords());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("participant"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
        matchesPlayedCol.setCellValueFactory(new PropertyValueFactory<>("matchesPlayed"));
        numOfWinsCol.setCellValueFactory(new PropertyValueFactory<>("numOfWins"));
        numOfLossesCol.setCellValueFactory(new PropertyValueFactory<>("numOfLosses"));
        numOfDrawsCol.setCellValueFactory(new PropertyValueFactory<>("numOfDraws"));
        gRankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
    }

    public ObservableList<ParticipantRecord> getParticipantsRecords() {
        ObservableList<Participant> allPlayers = FXCollections.observableArrayList();
        allPlayers.addAll(swissSystem.getParticipants());
        ObservableList<ParticipantRecord> participantRecords = FXCollections.observableArrayList();
        for (Map.Entry<Participant, ParticipantRecord> entry : participantsToRecords.entrySet()) {
            participantRecords.add(entry.getValue());
        }
        return participantRecords;
    }

    public void addPoints(SwissBracket swissBracket){
        ParticipantRecord participant1Record = participantsToRecords.get(swissBracket.getMatch().getParticipant1().getValue());
        ParticipantRecord participant2Record = participantsToRecords.get(swissBracket.getMatch().getParticipant2().getValue());

        participant1Record.setMatchesPlayed(participant1Record.getMatchesPlayed() + 1);
        participant2Record.setMatchesPlayed(participant2Record.getMatchesPlayed() + 1);

        if (swissBracket.getMatch().getParticipant1ScoreProperty().getValue() == swissBracket.getMatch().getParticipant2ScoreProperty().getValue()){
            participant1Record.setPoints(participant1Record.getPoints() + 1);
            participant2Record.setPoints(participant2Record.getPoints() + 1);
            participant1Record.setNumOfDraws(participant1Record.getNumOfDraws() + 1);
            participant2Record.setNumOfDraws(participant2Record.getNumOfDraws() + 1);
        }
        else if (swissBracket.getMatch().getParticipant1ScoreProperty().getValue() > swissBracket.getMatch().getParticipant2ScoreProperty().getValue()){
            participant1Record.setPoints(participant1Record.getPoints() + 3);
            participant1Record.setNumOfWins(participant1Record.getNumOfWins() + 1);
            participant2Record.setNumOfLosses(participant2Record.getNumOfLosses() + 1);
            participant1Record.setRank(participant1Record.getRank() + 3);
            participant2Record.setRank(participant2Record.getRank() - 3);
        }
        else if (swissBracket.getMatch().getParticipant1ScoreProperty().getValue() < swissBracket.getMatch().getParticipant2ScoreProperty().getValue()){
            participant2Record.setPoints(participant2Record.getPoints() + 3);
            participant2Record.setNumOfWins(participant2Record.getNumOfWins() + 1);
            participant1Record.setNumOfLosses(participant1Record.getNumOfLosses() + 1);
            participant2Record.setRank(participant2Record.getRank() + 3);
            participant1Record.setRank(participant1Record.getRank() - 3);
        }
        participantsTable.getItems().clear();
        participantsTable.setItems(getParticipantsRecords());
        pointsCol.setSortType(TableColumn.SortType.DESCENDING);
        participantsTable.getSortOrder().add(pointsCol);
    }


    public void nextRound(){

        if (roundCounter + 1 == swissSystem.getMaxRounds()){
            return;
        }

        List<Participant> pairs = new ArrayList<>();
        List<SwissBracket> actualRound;
        boolean alreadyPaired;

        int minGRankDiff;
        int participant1GRank;
        int participant2GRank;
        Participant bestMatch = null;

        //find best match for all participants
        for (Participant participant1 : participants){
            minGRankDiff = 1000000;
            ParticipantRecord participant1Record = participantsToRecords.get(participant1);
            participant1GRank = participant1Record.getRank();

            //if participant1 already had pair in this round
            if (pairs.contains(participant1)){
                continue;
            }
            for (Participant participant2 : participants) {
                alreadyPaired = false;
                ParticipantRecord participant2Record = participantsToRecords.get(participant2);
                participant2GRank = participant2Record.getRank();

                //if participant2 already had pair in this round
                if (pairs.contains(participant2)) {
                    continue;
                }
                if (participant1 == participant2){
                    continue;
                }

                //check if participants already played together in previous rounds
                int temp = 0;
                while (temp < roundCounter) {
                    actualRound = brackets[temp];
                    for (SwissBracket swissBracket : actualRound) {
                        if (((swissBracket.getMatch().getParticipant1().getValue() == participant1) && (swissBracket.getMatch().getParticipant2().getValue() == participant2)) || ((swissBracket.getMatch().getParticipant1().getValue() == participant2) && (swissBracket.getMatch().getParticipant2().getValue() == participant1))) {
                            alreadyPaired = true;
                        }
                    }
                    temp++;
                }
                if (alreadyPaired) {
                    continue;
                }
                if (Math.abs(participant1GRank - participant2GRank) < minGRankDiff) {
                    minGRankDiff = Math.abs(participant1GRank - participant2GRank);
                    bestMatch = participant2;
                }
            }
            pairs.add(participant1);
            pairs.add(bestMatch);
        }

        int j = 0;
        for (int i = 0; i < brackets[0].size(); i++){
            SwissBracket actualSwissBracket = brackets[roundCounter].get(i);
            actualSwissBracket.getMatch().setParticipant1(pairs.get(j));
            actualSwissBracket.getMatch().setParticipant2(pairs.get(j+1));
            j = j + 2;
        }
        printNextRound();
        }
}
