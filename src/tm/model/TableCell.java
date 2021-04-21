package tm.model;

import java.io.Serializable;

public class TableCell implements Serializable {
    private Match match;
    private int row;
    private int column;

    public TableCell(Match match, int row, int column) {
        this.match = match;
        this.row = row;
        this.column = column;
    }

    public void setWinner(Participant winner){
        if (match.hasBothParticipants())
            match.setWinner(winner);
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
