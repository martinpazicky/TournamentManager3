package tm.model.database;

import tm.model.MyLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Part of Repository design pattern
 * @param <T> Type of object that is stored in database
 * @author martinpazicky
 */

public abstract class Repository<T> {

    protected List<T> objectList = new ArrayList<>();
    protected String fileName;

    public Repository(String fileName){
        this.fileName = fileName;
    }

    public Repository(){}

    /**
     * Adds object to database
     * @param o object to be added
     */
    public void add(T o)
    {
        objectList.add(o);
    }

    /**
     * Removes object from database
     * @param o object to be removed
     */
    public void remove(T o)
    {
        objectList.remove(o);
    }

    /**
     * Getter for {@link Repository#objectList}
     * @return {@link Repository#objectList}
     */
    public List<T> getItems()
    {
        return this.objectList;
    }

    public void print() {
        for (T o : objectList) {
            System.out.println(o);
        }
    }

    /**
     * Saves database to {@link Repository#fileName} file
     */
    public void save() {
        serialize();
    }


    /**
     * Loads database from {@link Repository#fileName} file
     */
    public void load() {
        deserialize();
    }

    private void serialize() {
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(objectList);
            out.close();
            file.close();
        } catch (FileNotFoundException e) {
            MyLogger.LOG.log(Level.SEVERE,"Neexistujúci súbor s dátami");
        } catch (NotSerializableException e) {
            MyLogger.LOG.log(Level.SEVERE,"Objekty nie sú serializovatelné");
        } catch (IOException e) {
            MyLogger.LOG.log(Level.SEVERE,"Problém pri serializácií");
        }
    }

    private void deserialize() {
        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            objectList = (ArrayList<T>) in.readObject();
            in.close();
            file.close();
        } catch (FileNotFoundException e) {
            MyLogger.LOG.log(Level.SEVERE,"Neexistujúci súbor s dátami");
        } catch (IOException e) {
            MyLogger.LOG.log(Level.SEVERE,"Prázdny súbor s dátami");
        } catch (ClassNotFoundException e) {
            MyLogger.LOG.log(Level.SEVERE,"Class not found");
        }
    }
}

