package com.csgame.api.csgameapi.persistence;

import java.io.IOException;
import com.csgame.api.csgameapi.model.Event;

/**
 * Defines the interface for Event object persistence
 * 
 * @author Jack McLaughlin
 */
public interface EventDAO {
    /**
     * Retrieves all {@linkplain Event events}
     * 
     * @return An array of {@link Event events} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Event[] getEvents() throws IOException;

    /**
     * Finds all {@linkplain Event events} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Event events} whose nemes contains the given
     *         text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Event[] findEvents(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Event event} with the given id
     * 
     * @param eventID The id of the {@link Event event} to get
     * 
     * @return a {@link Event event} object with the matching id
     *         <br>
     *         null if no {@link Event event} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Event getEvent(int eventID) throws IOException;

    /**
     * Creates and saves a {@linkplain Event event}
     * 
     * @param event {@linkplain Event event} object to be created and saved
     *              <br>
     *              The id of the event object is ignored and a new uniqe id is
     *              assigned
     *
     * @return new {@link Event event} if successful, false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    Event createEvent(Event event) throws IOException;

    /**
     * Updates and saves a {@linkplain Event event}
     * 
     * @param {@link Event event} object to be updated and saved
     * 
     * @return updated {@link Event event} if successful, null if
     *         {@link Event event} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Event updateEvent(Event event) throws IOException;

    /**
     * Deletes a {@linkplain Event event} with the given id
     * 
     * @param eventID The id of the {@link Event event}
     * 
     * @return true if the {@link Event event} was deleted
     *         <br>
     *         false if event with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteEvent(int eventID) throws IOException;
}
