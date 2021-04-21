package tm.model;

import java.io.Serializable;

public class SwissBracket implements Serializable {
    private Match match;
    private int level;
    private int index;

    public SwissBracket(Match match, int level, int index) {
        this.match = match;
        this.level = level;
        this.index = index;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
