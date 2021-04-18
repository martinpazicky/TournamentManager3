package tm.model.database;

import tm.model.MyLogger;

import java.util.logging.Level;

public class Database {
    public static TournamentRepository tournaments = new TournamentRepository("tournaments.ser");

    public static void loadAll(){
        tournaments.load();
        MyLogger.LOG.log(Level.INFO, "Loading all data from .ser files");
    }

    public static void saveAll(){
        tournaments.save();
        MyLogger.LOG.log(Level.INFO, "Saving all data to .ser files");
    }
}
