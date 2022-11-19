package com.csgame.api.csgameapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.csgame.api.csgameapi.model.Hero;

/**
 * Implements the functionality for JSON file-based peristance for csgame
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author SWEN Faculty
 */
@Component
public class HeroFileDAO implements HeroDAO {
    private static final Logger LOG = Logger.getLogger(HeroFileDAO.class.getName());
    Map<Integer,Hero> csgame;   // Provides a local cache of the hero objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Hero
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new hero
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Hero File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public HeroFileDAO(@Value("${csgame.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the csgame from the file
    }

    /**
     * Generates the next id for a new {@linkplain Hero hero}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Hero csgame} from the tree map
     * 
     * @return  The array of {@link Hero csgame}, may be empty
     */
    private Hero[] getcsgameArray() {
        return getcsgameArray(null);
    }

    /**
     * Generates an array of {@linkplain Hero csgame} from the tree map for any
     * {@linkplain Hero csgame} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Hero csgame}
     * in the tree map
     * 
     * @return  The array of {@link Hero csgame}, may be empty
     */
    private Hero[] getcsgameArray(String containsText) { // if containsText == null, no filter
        ArrayList<Hero> heroArrayList = new ArrayList<>();

        for (Hero hero : csgame.values()) {
            if (containsText == null || hero.getName().contains(containsText)) {
                heroArrayList.add(hero);
            }
        }

        Hero[] heroArray = new Hero[heroArrayList.size()];
        heroArrayList.toArray(heroArray);
        return heroArray;
    }

    /**
     * Saves the {@linkplain Hero csgame} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Hero csgame} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Hero[] heroArray = getcsgameArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),heroArray);
        return true;
    }

    /**
     * Loads {@linkplain Hero csgame} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        csgame = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of csgame
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Hero[] heroArray = objectMapper.readValue(new File(filename),Hero[].class);

        // Add each hero to the tree map and keep track of the greatest id
        for (Hero hero : heroArray) {
            csgame.put(hero.getId(),hero);
            if (hero.getId() > nextId)
                nextId = hero.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Hero[] getcsgame() {
        synchronized(csgame) {
            return getcsgameArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Hero[] findcsgame(String containsText) {
        synchronized(csgame) {
            return getcsgameArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Hero getHero(int id) {
        synchronized(csgame) {
            if (csgame.containsKey(id))
                return csgame.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Hero createHero(Hero hero) throws IOException {
        synchronized(csgame) {
            // We create a new hero object because the id field is immutable
            // and we need to assign the next unique id
            Hero newHero = new Hero(nextId(),hero.getName());
            csgame.put(newHero.getId(),newHero);
            save(); // may throw an IOException
            return newHero;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Hero updateHero(Hero hero) throws IOException {
        synchronized(csgame) {
            if (csgame.containsKey(hero.getId()) == false)
                return null;  // hero does not exist

            csgame.put(hero.getId(),hero);
            save(); // may throw an IOException
            return hero;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteHero(int id) throws IOException {
        synchronized(csgame) {
            if (csgame.containsKey(id)) {
                csgame.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
