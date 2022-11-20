package com.csgame.api.csgameapi.controller;

import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import com.csgame.api.csgameapi.persistence.CompanyUserDAO;
import com.csgame.api.csgameapi.persistence.EventDAO;
import com.csgame.api.csgameapi.persistence.NPOUserDAO;
import com.csgame.api.csgameapi.persistence.UserDAO;
import com.csgame.api.csgameapi.persistence.VolunteerUserDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.csgame.api.csgameapi.model.*;

/**
 * Handles the REST API requests for the User resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Team Sol Kumar
 */

@RestController
@RequestMapping("users")
public class UserController {
    private UserDAO uDAO;
    private VolunteerUserDAO vDAO;
    private CompanyUserDAO cDAO;
    private NPOUserDAO oDAO;
    private EventDAO eDAO;

    public UserController(UserDAO userDAO,
                            VolunteerUserDAO volunteerDAO,
                            CompanyUserDAO companyDAO,
                            NPOUserDAO npoDAO,
                            EventDAO eventDAO) {
        this.uDAO = userDAO;
        this.vDAO = volunteerDAO;
        this.cDAO = companyDAO;
        this.oDAO = npoDAO;
        this.eDAO =eventDAO;
    }

    @GetMapping("/{UID}")
    public ResponseEntity<User> getUser(@PathVariable String UID) {
        try {
            // parse uid instead of guessing and checking
            // depending on first letter, use if and go to right DAO
            String userType = UID.substring(0, 1);
            User u;

            if (userType.equals("V"))
                u = vDAO.getUser(UID);
            else if (userType.equals("C"))
                u = cDAO.getUser(UID);
            else { // equals o
                u = oDAO.getUser(UID);  
            }

            if (u != null)
                return new ResponseEntity<User>(u, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user){
        try {
            
            // User checkUser = userDao.login(user.getUsername(), user.getPassword());
            User checkUser = userDao.login(user.getUsername(), user.getPassword());
            if(checkUser != null)
                return new ResponseEntity<User>(checkUser, HttpStatus.ACCEPTED);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PatchMapping("")
    // public ResponseEntity<User> updateUser(@RequestBody JsonNode requestBody) {
    //     try {
    //         String UID = requestBody.get("UID").toString(); // EX: UID = "V0" w/ quotes

    //         String userType = UID.substring(1, 2);
    //         User userToUpdate;

    //         if (userType.equals("V")) {
    //             userToUpdate = vDAO.getUser(UID);
                
    //             if (requestBody.get("currentPoints") != null){
    //                 String points = requestBody.get("currentPoints").toString();
    //                 int pointsInt = Integer.parseInt(points.substring(1, points.length() - 1));
    //                 userToUpdate.set
    //             }
    //         }
    //         else
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    //     catch (IOException e)
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        try {
            User checkUser = uDAO.login(user.getUsername(), user.getPassword());
            
            if(checkUser != null)
                return new ResponseEntity<User>(checkUser, HttpStatus.ACCEPTED);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{UID}/{eventID}")
    public ResponseEntity<User> addEvent(@PathVariable String UID, @PathVariable int eventID) {
        try {
            System.out.println("EEEEE " + UID + " " + eventID);

            // parse uid instead of guessing and checking
            // depending on first letter, use if and go to right DAO
            String userType = UID.substring(0, 1);
            VolunteerUser u;

            if (userType.equals("V"))
                u = vDAO.getUser(UID);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            if (u == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Event e = eDAO.getEvent(eventID);

            System.out.println("FFFF " + e.getEventName());

            if (u.addEvent(e)) {
                System.out.println("e");
                return new ResponseEntity<>(u, HttpStatus.OK);
            }
            else {
                System.out.println("f");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{UID}/{eventID}")
    public ResponseEntity<User> completeEvent(@PathVariable String UID, @PathVariable int eventID) {
        try {
            System.out.println("EEEEE " + UID + " " + eventID);

            // parse uid instead of guessing and checking
            // depending on first letter, use if and go to right DAO
            String userType = UID.substring(0, 1);
            VolunteerUser u;

            if (userType.equals("V"))
                u = vDAO.getUser(UID);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            if (u == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Event e = eDAO.getEvent(eventID);

            System.out.println("FFFF " + e.getEventName());

            if (u.completeEvent(e) > 0) {
                System.out.println("e");
                return new ResponseEntity<>(u, HttpStatus.OK);
            }
            else {
                System.out.println("f");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}