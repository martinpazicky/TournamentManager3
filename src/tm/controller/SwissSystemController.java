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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tm.controller.bracketFX.BracketFX;
import tm.controller.swissBracketFX.SwissBracketFX;
import tm.controller.tableFX.CellFX;
import tm.model.Match;
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

    public static SwissSystem swissSystem;
    private List<SwissBracket>[] brackets;
    private List<Participant> participants;

    private double yLayout = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootAP.getStylesheets().add("tm/resources/css/swissSystem.css");
        participantsTable.getStylesheets().add("tm/resources/css/table.css");
        participants = swissSystem.getParticipants();
        brackets = swissSystem.getBrackets();

        AnchorPane anchorPane = new AnchorPane();

        for(int i = 0; i < brackets[0].size(); i++){

            AnchorPane pane = new AnchorPane();

            Label label = new Label(" " + "Round" + " " + roundCounter);
            label.setTextFill(Color.web("#6bfc03"));
            label.setFont(new Font(30));
            label.setWrapText(true);
            label.setFont(new Font(20.0));
            label.setLayoutY(40);
            label.setLayoutX(20);

            anchorPane.getChildren().add(label);

            SwissBracket actualBracket;

            actualBracket =  brackets[0].get(i);
            SwissBracketFX swissBracketFX = new SwissBracketFX(actualBracket);
            //if cell have score
            if (swissBracketFX.getSwissBracket().getMatch().getParticipant1ScoreProperty().getValue() >= 0){
                String score1 = String.valueOf(swissBracketFX.getSwissBracket().getMatch().getParticipant1ScoreProperty().getValue());
                String score2 = String.valueOf(swissBracketFX.getSwissBracket().getMatch().getParticipant2ScoreProperty().getValue());
                swissBracketFX.getParticipant1Score().setText((score1));
                swissBracketFX.getParticipant2Score().setText((score2));
            }


            swissBracketFX.getSetResultBtn().setOnAction(event -> {
//                ParticipantRecord participant1Record = swissSystem.getParticipantsToRecords().get(swissBracketFX.getSwissBracket().getMatch().getParticipant1().getValue());
//                participant1Record.set
                swissBracketFX.setResult();
                addPoints();
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
        label.setTextFill(Color.web("#6bfc03"));
        label.setFont(new Font(30));
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
                addPoints();
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

        participantsTable.getItems().clear();
        participantsTable.setItems(getParticipantsRecords());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("participant"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
        matchesPlayedCol.setCellValueFactory(new PropertyValueFactory<>("matchesPlayed"));
        numOfWinsCol.setCellValueFactory(new PropertyValueFactory<>("numOfWins"));
        numOfLossesCol.setCellValueFactory(new PropertyValueFactory<>("numOfLosses"));
        numOfDrawsCol.setCellValueFactory(new PropertyValueFactory<>("numOfDraws"));
        gRankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
        pointsCol.setSortType(TableColumn.SortType.DESCENDING);
        participantsTable.getSortOrder().add(pointsCol);
    }

    public ObservableList<ParticipantRecord> getParticipantsRecords() {
        ObservableList<Participant> allPlayers = FXCollections.observableArrayList();
        allPlayers.addAll(swissSystem.getParticipants());
        ObservableList<ParticipantRecord> participantRecords = FXCollections.observableArrayList();
        for (Map.Entry<Participant, ParticipantRecord> entry : swissSystem.getParticipantsToRecords().entrySet()) {
            participantRecords.add(entry.getValue());
        }
        return participantRecords;
    }

    public void addPoints(){
        clearTable();

        for (List<SwissBracket> oneRound : brackets){
            for (SwissBracket swissBracket : oneRound){

                //if brackets score is set
                if (swissBracket.getMatch().getParticipant1ScoreProperty().getValue() >= 0 && swissBracket.getMatch().getParticipant2ScoreProperty().getValue() >= 0){
                    ParticipantRecord participant1Record = swissSystem.getParticipantsToRecords().get(swissBracket.getMatch().getParticipant1().getValue());
                    ParticipantRecord participant2Record = swissSystem.getParticipantsToRecords().get(swissBracket.getMatch().getParticipant2().getValue());

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
                }
            }
                }
        participantsTable.getItems().clear();
        participantsTable.setItems(getParticipantsRecords());
        pointsCol.setSortType(TableColumn.SortType.DESCENDING);
        participantsTable.getSortOrder().add(pointsCol);
    }


    public void clearTable(){
        participantsTable.getItems().clear();
        List<ParticipantRecord> records = getParticipantsRecords();
        for (ParticipantRecord participantRecord : records){
            participantRecord.setPoints(0);
            participantRecord.setMatchesPlayed(0);
            participantRecord.setNumOfDraws(0);
            participantRecord.setNumOfLosses(0);
            participantRecord.setNumOfWins(0);
            participantRecord.setRank(0);
        }
        participantsTable.setItems(getParticipantsRecords());
    }

    public void nextRound(){

        if (roundCounter + 1 == swissSystem.getMaxRounds()){
            return;
        }

        Map<Participant, Participant> allPairs = new HashMap<>();

        List<Participant> pairs = new ArrayList<>();
        List<SwissBracket> actualRound;
        boolean alreadyPaired;

        int minGRankDiff;
        int participant1GRank;
        int participant2GRank;
        Participant bestMatch = null;

        //find best match for all participants
        while (true) {
            Random randomizer = new Random();
            Participant participant1 = participants.get(randomizer.nextInt(participants.size()));
            bestMatch = null;
            minGRankDiff = 1000000;
            ParticipantRecord participant1Record = swissSystem.getParticipantsToRecords().get(participant1);
            participant1GRank = participant1Record.getRank();

            //if participant1 already had pair in this round
            if (pairs.contains(participant1)){
                continue;
            }
            for (Participant participant2 : participants) {
                alreadyPaired = false;
                ParticipantRecord participant2Record = swissSystem.getParticipantsToRecords().get(participant2);
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
            if (bestMatch != null){
                pairs.add(participant1);
                pairs.add(bestMatch);
                if (pairs.size() == participants.size()){
                    break;
                }
            }
            else {
                pairs.clear();
            }
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
