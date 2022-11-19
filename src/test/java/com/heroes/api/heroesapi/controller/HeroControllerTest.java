package com.heroes.api.heroesapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.heroes.api.heroesapi.persistence.HeroDAO;
import com.heroes.api.heroesapi.model.Hero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Hero Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class HeroControllerTest {
    private HeroController heroController;
    private HeroDAO mockHeroDAO;

    /**
     * Before each test, create a new HeroController object and inject
     * a mock Hero DAO
     */
    @BeforeEach
    public void setupHeroController() {
        mockHeroDAO = mock(HeroDAO.class);
        heroController = new HeroController(mockHeroDAO);
    }

    @Test
    public void testGetHero() throws IOException {  // getHero may throw IOException
        // Setup
        Hero hero = new Hero(99,"Galactic Agent");
        // When the same id is passed in, our mock Hero DAO will return the Hero object
        when(mockHeroDAO.getHero(hero.getId())).thenReturn(hero);

        // Invoke
        ResponseEntity<Hero> response = heroController.getHero(hero.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(hero,response.getBody());
    }

    @Test
    public void testGetHeroNotFound() throws Exception { // createHero may throw IOException
        // Setup
        int heroId = 99;
        // When the same id is passed in, our mock Hero DAO will return null, simulating
        // no hero found
        when(mockHeroDAO.getHero(heroId)).thenReturn(null);

        // Invoke
        ResponseEntity<Hero> response = heroController.getHero(heroId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetHeroHandleException() throws Exception { // createHero may throw IOException
        // Setup
        int heroId = 99;
        // When getHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockHeroDAO).getHero(heroId);

        // Invoke
        ResponseEntity<Hero> response = heroController.getHero(heroId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all HeroController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateHero() throws IOException {  // createHero may throw IOException
        // Setup
        Hero hero = new Hero(99,"Wi-Fire");
        // when createHero is called, return true simulating successful
        // creation and save
        when(mockHeroDAO.createHero(hero)).thenReturn(hero);

        // Invoke
        ResponseEntity<Hero> response = heroController.createHero(hero);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(hero,response.getBody());
    }

    @Test
    public void testCreateHeroFailed() throws IOException {  // createHero may throw IOException
        // Setup
        Hero hero = new Hero(99,"Bolt");
        // when createHero is called, return false simulating failed
        // creation and save
        when(mockHeroDAO.createHero(hero)).thenReturn(null);

        // Invoke
        ResponseEntity<Hero> response = heroController.createHero(hero);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateHeroHandleException() throws IOException {  // createHero may throw IOException
        // Setup
        Hero hero = new Hero(99,"Ice Gladiator");

        // When createHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockHeroDAO).createHero(hero);

        // Invoke
        ResponseEntity<Hero> response = heroController.createHero(hero);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateHero() throws IOException { // updateHero may throw IOException
        // Setup
        Hero hero = new Hero(99,"Wi-Fire");
        // when updateHero is called, return true simulating successful
        // update and save
        when(mockHeroDAO.updateHero(hero)).thenReturn(hero);
        ResponseEntity<Hero> response = heroController.updateHero(hero);
        hero.setName("Bolt");

        // Invoke
        response = heroController.updateHero(hero);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(hero,response.getBody());
    }

    @Test
    public void testUpdateHeroFailed() throws IOException { // updateHero may throw IOException
        // Setup
        Hero hero = new Hero(99,"Galactic Agent");
        // when updateHero is called, return true simulating successful
        // update and save
        when(mockHeroDAO.updateHero(hero)).thenReturn(null);

        // Invoke
        ResponseEntity<Hero> response = heroController.updateHero(hero);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateHeroHandleException() throws IOException { // updateHero may throw IOException
        // Setup
        Hero hero = new Hero(99,"Galactic Agent");
        // When updateHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockHeroDAO).updateHero(hero);

        // Invoke
        ResponseEntity<Hero> response = heroController.updateHero(hero);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetHeroes() throws IOException { // getHeroes may throw IOException
        // Setup
        Hero[] heroes = new Hero[2];
        heroes[0] = new Hero(99,"Bolt");
        heroes[1] = new Hero(100,"The Great Iguana");
        // When getHeroes is called return the heroes created above
        when(mockHeroDAO.getHeroes()).thenReturn(heroes);

        // Invoke
        ResponseEntity<Hero[]> response = heroController.getHeroes();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(heroes,response.getBody());
    }

    @Test
    public void testGetHeroesHandleException() throws IOException { // getHeroes may throw IOException
        // Setup
        // When getHeroes is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockHeroDAO).getHeroes();

        // Invoke
        ResponseEntity<Hero[]> response = heroController.getHeroes();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchHeroes() throws IOException { // findHeroes may throw IOException
        // Setup
        String searchString = "la";
        Hero[] heroes = new Hero[2];
        heroes[0] = new Hero(99,"Galactic Agent");
        heroes[1] = new Hero(100,"Ice Gladiator");
        // When findHeroes is called with the search string, return the two
        /// heroes above
        when(mockHeroDAO.findHeroes(searchString)).thenReturn(heroes);

        // Invoke
        ResponseEntity<Hero[]> response = heroController.searchHeroes(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(heroes,response.getBody());
    }

    @Test
    public void testSearchHeroesHandleException() throws IOException { // findHeroes may throw IOException
        // Setup
        String searchString = "an";
        // When createHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockHeroDAO).findHeroes(searchString);

        // Invoke
        ResponseEntity<Hero[]> response = heroController.searchHeroes(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteHero() throws IOException { // deleteHero may throw IOException
        // Setup
        int heroId = 99;
        // when deleteHero is called return true, simulating successful deletion
        when(mockHeroDAO.deleteHero(heroId)).thenReturn(true);

        // Invoke
        ResponseEntity<Hero> response = heroController.deleteHero(heroId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteHeroNotFound() throws IOException { // deleteHero may throw IOException
        // Setup
        int heroId = 99;
        // when deleteHero is called return false, simulating failed deletion
        when(mockHeroDAO.deleteHero(heroId)).thenReturn(false);

        // Invoke
        ResponseEntity<Hero> response = heroController.deleteHero(heroId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteHeroHandleException() throws IOException { // deleteHero may throw IOException
        // Setup
        int heroId = 99;
        // When deleteHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockHeroDAO).deleteHero(heroId);

        // Invoke
        ResponseEntity<Hero> response = heroController.deleteHero(heroId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
