package tm.model.database;

import tm.model.tournament.Tournament;

public class TournamentRepository extends Repository<Tournament> {
    private int idCounter = objectList.size() + 1;

    public TournamentRepository(String fileName) {
        super(fileName);
    }

    public TournamentRepository(){

    }

    /**
     * Adds an object to database
     * Sets ID of the object
     * @param item Object to be added
     */
    public void add(Tournament item)
    {
        super.add(item);
        item.setId(idCounter);
        idCounter++;
    }

    /**
     * Removes an object from database based on given ID
     * @param ID ID of object to be removed
     */
    public void remove(int ID)
    {
        Tournament item = get(ID);
        super.remove(item);
    }

    /**
     * Gets object from database based on given ID
     * @param ID ID of desired object
     * @return desired object, null if no such an object exists
     */
    public Tournament get(int ID)
    {
        for(Tournament i : objectList)
        {
            if(i.getId() == ID)
                return i;
        }
        return null;
    }
}
