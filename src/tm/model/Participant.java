package tm.model;

import java.io.Serializable;

public class Participant implements Serializable {
    final private int id;
    private String name;
    static private int idCounter = 1;

    public Participant(String name) {
        this.name = name;
        this.id = idCounter++;
    }

    public String toString(){
        return getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
