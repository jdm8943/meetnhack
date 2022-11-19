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
import com.fasterxml.jackson.databind.JsonNode;
import com.csgame.api.csgameapi.model.User;

@RestController
@RequestMapping("users")
public class UserController {
    private UserDAO userDao;

    public UserController(UserDAO userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/{uid}")
    public ResponseEntity<User> getUser(@PathVariable String uid) {
        try {
            User user = userDao.getUser(uid);

            if (user != null)
                return new ResponseEntity<User>(user,HttpStatus.OK);
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