package com.heroes.api.heroesapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Hero class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class HeroTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";

        // Invoke
        Hero hero = new Hero(expected_id,expected_name);

        // Analyze
        assertEquals(expected_id,hero.getId());
        assertEquals(expected_name,hero.getName());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        Hero hero = new Hero(id,name);

        String expected_name = "Galactic Agent";

        // Invoke
        hero.setName(expected_name);

        // Analyze
        assertEquals(expected_name,hero.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        String expected_string = String.format(Hero.STRING_FORMAT,id,name);
        Hero hero = new Hero(id,name);

        // Invoke
        String actual_string = hero.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}