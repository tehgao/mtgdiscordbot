package com.alvingao.discordbot.handlers;

import junit.framework.TestCase;

public class DeckGeneratorHandlerTest extends TestCase {
    public void testGenerator() {
        DeckGeneratorHandler classUnderTest = new DeckGeneratorHandler();

        String response = classUnderTest.respond("");
        System.out.println(response);
        assertNotNull(response);
    }

    public void testFire() {
        DeckGeneratorHandler classUnderTest = new DeckGeneratorHandler();
        String input = "!deck";

        assertTrue(classUnderTest.fire(input));
    }

    public void testNotFire() {
        DeckGeneratorHandler classUnderTest = new DeckGeneratorHandler();
        String input = "fdjsfjsldj";

        assertFalse(classUnderTest.fire(input));
    }
}