package com.csgame.api.csgameapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.csgame.api.csgameapi.model.Hero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Hero File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class HeroFileDAOTest {
    HeroFileDAO heroFileDAO;
    Hero[] testcsgame;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testcsgame = new Hero[3];
        testcsgame[0] = new Hero(99,"Wi-Fire");
        testcsgame[1] = new Hero(100,"Galactic Agent");
        testcsgame[2] = new Hero(101,"Ice Gladiator");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Hero[].class))
                .thenReturn(testcsgame);
        heroFileDAO = new HeroFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetcsgame() {
        // Invoke
        Hero[] csgame = heroFileDAO.getcsgame();

        // Analyze
        assertEquals(csgame.length,testcsgame.length);
        for (int i = 0; i < testcsgame.length;++i)
            assertEquals(csgame[i],testcsgame[i]);
    }

    @Test
    public void testFindcsgame() {
        // Invoke
        Hero[] csgame = heroFileDAO.findcsgame("la");

        // Analyze
        assertEquals(csgame.length,2);
        assertEquals(csgame[0],testcsgame[1]);
        assertEquals(csgame[1],testcsgame[2]);
    }

    @Test
    public void testGetHero() {
        // Invoke
        Hero hero = heroFileDAO.getHero(99);

        // Analzye
        assertEquals(hero,testcsgame[0]);
    }

    @Test
    public void testDeleteHero() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> heroFileDAO.deleteHero(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test csgame array - 1 (because of the delete)
        // Because csgame attribute of HeroFileDAO is package private
        // we can access it directly
        assertEquals(heroFileDAO.csgame.size(),testcsgame.length-1);
    }

    @Test
    public void testCreateHero() {
        // Setup
        Hero hero = new Hero(102,"Wonder-Person");

        // Invoke
        Hero result = assertDoesNotThrow(() -> heroFileDAO.createHero(hero),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Hero actual = heroFileDAO.getHero(hero.getId());
        assertEquals(actual.getId(),hero.getId());
        assertEquals(actual.getName(),hero.getName());
    }

    @Test
    public void testUpdateHero() {
        // Setup
        Hero hero = new Hero(99,"Galactic Agent");

        // Invoke
        Hero result = assertDoesNotThrow(() -> heroFileDAO.updateHero(hero),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Hero actual = heroFileDAO.getHero(hero.getId());
        assertEquals(actual,hero);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Hero[].class));

        Hero hero = new Hero(102,"Wi-Fire");

        assertThrows(IOException.class,
                        () -> heroFileDAO.createHero(hero),
                        "IOException not thrown");
    }

    @Test
    public void testGetHeroNotFound() {
        // Invoke
        Hero hero = heroFileDAO.getHero(98);

        // Analyze
        assertEquals(hero,null);
    }

    @Test
    public void testDeleteHeroNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> heroFileDAO.deleteHero(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(heroFileDAO.csgame.size(),testcsgame.length);
    }

    @Test
    public void testUpdateHeroNotFound() {
        // Setup
        Hero hero = new Hero(98,"Bolt");

        // Invoke
        Hero result = assertDoesNotThrow(() -> heroFileDAO.updateHero(hero),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the HeroFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Hero[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new HeroFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
