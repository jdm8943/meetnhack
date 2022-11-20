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

import com.csgame.api.csgameapi.model.Event;

/**
 * Implements the functionality for JSON file-based peristence for Event
 * events
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 * @author Jack McLaughlin
 */
@Component
public class EventFileDAO implements EventDAO {
    private static final Logger LOG = Logger.getLogger(EventFileDAO.class.getName());
    public Map<Integer, Event> events; // Provides a local cache of the event objects
    // so that we don't need to read from the file
    // each time
    private ObjectMapper objectMapper; // Provides conversion between Event
                                       // objects and JSON text format written
                                       // to the file
    private static int nextId; // The next Id to assign to a new event
    private String filename; // Filename to read from and write to

    /**
     * Creates a Event File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public EventFileDAO(@Value("${events.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the events from the file
    }

    /**
     * Generates the next id for a new {@linkplain Event event}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        nextId += 1;
        return id;
    }

    /**
     * Generates an array of {@linkplain Event events} from the tree map
     * 
     * @return The array of {@link Event events}, may be empty
     */
    private Event[] getEventsArray() {
        return getEventsArray(null);
    }

    /**
     * Generates an array of {@linkplain Event events} from the tree map for any
     * {@linkplain Event events} that contains the text specified by
     * containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Event
     * events}
     * in the tree map
     * 
     * @return The array of {@link Event events}, may be empty
     */
    private Event[] getEventsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Event> eventArrayList = new ArrayList<>();
        for (Event event : events.values()) {
            if (containsText == null
                    || event.getEventName().toLowerCase().contains(containsText.toLowerCase())
                    || event.getOrgID().toLowerCase().contains(containsText.toLowerCase())) {
                eventArrayList.add(event);
            }
        }

        Event[] eventArray = new Event[eventArrayList.size()];
        eventArrayList.toArray(eventArray);
        return eventArray;
    }

    /**
     * Saves the {@linkplain Event events} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link Event events} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Event[] eventArray = getEventsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), eventArray);
        return true;
    }

    /**
     * Loads {@linkplain Event events} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        events = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of events
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Event[] eventArray = objectMapper.readValue(new File(filename), Event[].class);

        // Add each event to the tree map and keep track of the greatest id
        for (Event event : eventArray) {
            if (event.getEventID() > nextId) {
                nextId = event.getEventID();
                events.put(event.getEventID(), event);
            }
        }
        nextId += 1;
        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Event[] getEvents() {
        synchronized (events) {
            return getEventsArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Event[] findEvents(String containsText) {
        synchronized (events) {
            return getEventsArray(containsText);
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Event getEvent(int eventID) {
        synchronized (events) {
            if (events.containsKey(eventID))
                return events.get(eventID);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Event createEvent(Event event) throws IOException {
        synchronized (events) {
            // We create a new event object because the id field is immutable
            // and we need to assign the next unique id
            Event newEvent = new Event(nextId(), event.getOrgID(),
                    event.getEventName(), event.getDescription(), event.getPoints(),
                    event.getAttendees(), event.getDate());
            events.put(newEvent.getEventID(), newEvent);
            save(); // may throw an IOException
            return newEvent;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Event updateEvent(Event event) throws IOException {
        synchronized (events) {
            if (events.containsKey(event.getEventID()) == true) {
                Event newProd = new Event(event.getEventID(), event.getOrgID(), event.getEventName(),
                        event.getDescription(), event.getPoints(), event.getAttendees(),
                        event.getDate());
                events.put(event.getEventID(), newProd);
                save(); // may throw an IOException
                return event;
            }

            return null; // event does not exist

        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteEvent(int eventID) throws IOException {
        synchronized (events) {
            if (events.containsKey(eventID)) {
                events.remove(eventID);
                return save();
            } else
                return false;
        }
    }
}
