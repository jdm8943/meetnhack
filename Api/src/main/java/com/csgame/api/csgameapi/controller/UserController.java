package com.csgame.api.csgameapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import com.csgame.api.csgameapi.persistence.UserDAO;
import com.csgame.api.csgameapi.persistence.VolunteerUserDAO;
import com.csgame.api.csgameapi.model.User;

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
    private UserDAO userDao;
    private VolunteerUserDAO volunteerDAO;

    public UserController(UserDAO userDao, VolunteerUserDAO volunteerDAO) {
        this.userDao = userDao;
        this.volunteerDAO = volunteerDAO;
    }

    @GetMapping("/{UID}")
    public ResponseEntity<User> getUser(@PathVariable String UID) {
        
        try {
            User user = userDao.getUser(UID);

            if (user != null)
                return new ResponseEntity<User>(user, HttpStatus.OK);
            else{
                try {
                    user = volunteerDAO.getUser(UID);
        
                    if (user != null)
                        return new ResponseEntity<User>(user, HttpStatus.OK);
                    else
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                catch(IOException e) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PatchMapping("")
    // public ResponseEntity<User> updateUser(@RequestBody JsonNode requestBody) {
    //     try {
    //         String UID = requestBody.get("UID").toString();
    //         System.out.println(UID);

    //         User userToUpdate = userDao.getUser(UID);
            
    //         if (userToUpdate != null) {
    //             if (userToUpdate.getPassword().equals )
    //         }
    //     }
    //     catch (IOException e)
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    /**
     * get user
     * update user
     * login
     * 
     */
}