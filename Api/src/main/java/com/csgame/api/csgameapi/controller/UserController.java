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

import com.csgame.api.csgameapi.persistence.CompanyUserDAO;
import com.csgame.api.csgameapi.persistence.NPOUserDAO;
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
    private VolunteerUserDAO vDAO;
    private CompanyUserDAO cDAO;
    private NPOUserDAO oDAO;

    public UserController(VolunteerUserDAO volunteerDAO,
                            CompanyUserDAO companyDAO,
                            NPOUserDAO npoDAO) {
        this.vDAO = volunteerDAO;
        this.cDAO = companyDAO;
        this.oDAO = npoDAO;
    }

    @GetMapping("/{UID}")
    public ResponseEntity<User> getUser(@PathVariable String UID) {
        
        try {
            // parse uid instead of guessing and checking
            // depending on first letter, use if and go to right DAO
            String userType = UID.substring(0, 0);
            
            User u;

            if (userType.equals("v"))
                u = vDAO.getUser(UID);

            else if (userType.equals("v"))
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
    
    // @PostMapping("/login")
    // public ResponseEntity<User> loginUser(@RequestBody User user){
    //     try {
            
    //         User checkUser = userDAO.checkUserLogin(user.getUsername(), user.getPassword());
    //         if(checkUser != null)
    //             return new ResponseEntity<User>(checkUser, HttpStatus.ACCEPTED);
    //         else
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }catch(IOException e){
    //         LOG.log(Level.SEVERE,e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    /**
     * get user
     * update user
     * login
     * 
     */
}