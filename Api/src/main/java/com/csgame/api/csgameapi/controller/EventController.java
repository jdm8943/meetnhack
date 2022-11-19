package com.csgame.api.csgameapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.csgame.api.csgameapi.persistence.EventDAO;
import com.csgame.api.csgameapi.model.Event;

/**
 * Handles the REST API requests for the Event resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Team Jack McLaughlin
 */

@RestController
@RequestMapping("events")
public class EventController {
    private static final Logger LOG = Logger.getLogger(EventController.class.getName());
    private EventDAO EventDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param EventDao The {@link EventDAO Event Data Access Object} to
     *                 perform CRUD operations
     *                 <br>
     *                 This dependency is injected by the Spring Framework
     */
    public EventController(EventDAO EventDAO) {
        this.EventDao = EventDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Event event} for the given
     * id
     * 
     * @param eventID The id used to locate the {@link Event event}
     * 
     * @return ResponseEntity with {@link Event event} object and HTTP status of
     *         OK if found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{eventID}")
    public ResponseEntity<Event> getEvent(@PathVariable int eventID) {
        LOG.info("GET /events/" + eventID);
        try {
            Event event = EventDao.getEvent(eventID);
            if (event != null)
                return new ResponseEntity<Event>(event, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Event events}
     * 
     * @return ResponseEntity with array of {@link Event event} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Event[]> getEvents() {
        LOG.info("GET /events");

        try {
            Event[] events = EventDao.getEvents();
            return new ResponseEntity<Event[]>(events, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Event events} whose name
     * contains the text in eventName
     * 
     * @param eventName The name parameter which contains the text used to find the
     *                  {@link Event events}
     * 
     * @return ResponseEntity with array of {@link Event event} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all events that contain the text "ma"
     *         GET http://localhost:8080/events/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Event[]> searchEvents(@RequestParam String eventName) {
        LOG.info("GET /events/?name=" + eventName);

        try {
            Event[] events = EventDao.findEvents(eventName);
            return new ResponseEntity<Event[]>(events, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Event event} with the provided event object
     * 
     * @param event - The {@link Event event} to create
     * 
     * @return ResponseEntity with created {@link Event event} object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Event
     *         event} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        LOG.info("POST /events " + event);
        String name = event.getEventName();
        ResponseEntity<Event[]> response = searchEvents(name);
        try {
            if (response.getBody() == null || (response.getBody() != null && response.getBody().length < 1)) {
                return new ResponseEntity<Event>(EventDao.createEvent(event), HttpStatus.CREATED);
            } else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Event event} with the provided
     * {@linkplain Event event} object, if it exists
     * 
     * @param event The {@link Event event} to update
     * 
     * @return ResponseEntity with updated {@link Event event} object and HTTP
     *         status of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        LOG.info("PUT /events " + event);

        try {
            Event newEvent = EventDao.updateEvent(event);
            if (newEvent != null)
                return new ResponseEntity<Event>(newEvent, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Event event} with the given id
     * 
     * @param eventID The id of the {@link Event event} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable int eventID) {
        LOG.info("DELETE /events/" + eventID);

        try {
            if (EventDao.deleteEvent(eventID) != false)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
