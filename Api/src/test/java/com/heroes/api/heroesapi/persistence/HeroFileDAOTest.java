package com.heroes.api.heroesapi.persistence;

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
import com.heroes.api.heroesapi.model.Hero;

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
    Hero[] testHeroes;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testHeroes = new Hero[3];
        testHeroes[0] = new Hero(99,"Wi-Fire");
        testHeroes[1] = new Hero(100,"Galactic Agent");
        testHeroes[2] = new Hero(101,"Ice Gladiator");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Hero[].class))
                .thenReturn(testHeroes);
        heroFileDAO = new HeroFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetHeroes() {
        // Invoke
        Hero[] heroes = heroFileDAO.getHeroes();

        // Analyze
        assertEquals(heroes.length,testHeroes.length);
        for (int i = 0; i < testHeroes.length;++i)
            assertEquals(heroes[i],testHeroes[i]);
    }

    @Test
    public void testFindHeroes() {
        // Invoke
        Hero[] heroes = heroFileDAO.findHeroes("la");

        // Analyze
        assertEquals(heroes.length,2);
        assertEquals(heroes[0],testHeroes[1]);
        assertEquals(heroes[1],testHeroes[2]);
    }

    @Test
    public void testGetHero() {
        // Invoke
        Hero hero = heroFileDAO.getHero(99);

        // Analzye
        assertEquals(hero,testHeroes[0]);
    }

    @Test
    public void testDeleteHero() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> heroFileDAO.deleteHero(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test heroes array - 1 (because of the delete)
        // Because heroes attribute of HeroFileDAO is package private
        // we can access it directly
        assertEquals(heroFileDAO.heroes.size(),testHeroes.length-1);
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
        assertEquals(heroFileDAO.heroes.size(),testHeroes.length);
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
