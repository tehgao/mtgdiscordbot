package com.alvingao.discordbot.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Handler class that generates rather bold decklist ideas.
 */
public class DeckGeneratorHandler implements IHandler {
    private List<String> archetypes;

    public DeckGeneratorHandler() {

    }

    /**
     * Wraps an input string in a random context, i.e. "I really think that ... is well positioned in the format right
     * now."
     *
     * @param input The string we want to wrap.
     * @return The wrapped string.
     */
    private String addContext(String input) {
        List<Pair<String, String>> contexts = new ArrayList<Pair<String, String>>();

        contexts.add(new Pair<String, String>("I really think that", "is well positioned in the format right now."));
        contexts.add(new Pair<String, String>("It's Seth, prooooobably better known as SaffronOlive, and today we're playing",
                "iiiiiiiin modern."));
        contexts.add(new Pair<String, String>("I think that", "is the best shell for Jace in modern."));
        contexts.add(new Pair<String, String>("Wizards really needs to do something about", "before it ruins magic."));

        Pair<String, String> thisContext = this.getRandom(contexts);
        return thisContext.getKey() + " " + input + " " + thisContext.getValue();
    }

    /**
     * Returns a color combo, i.e. mono-R or Jeskai.
     *
     * @return A random color combination
     */
    private String getColorCombo() {
        List<String> colorCombos = new ArrayList<String>(Arrays.asList("mono-W", "mono-U", "mono-B", "mono-R", "mono-G",
                "WU", "WB", "WR", "GW", "UB", "UR", "UG", "BR", "GB", "GR", "Jund", "Bant", "Grixis", "Naya", "Esper",
                "Jeskai", "Mardu", "Sultai", "Temur", "Abzan", "four-color", "five-color", "colorless"));

        return this.getRandom(colorCombos);
    }

    /**
     * Returns a random archetype, i.e. aggro, control.
     *
     * @return A random archetype.
     */
    private String getArchetype() {
        List<String> archetypes = new ArrayList<String>(Arrays.asList("Aggro", "Midrange", "Control", "Combo",
                "And Taxes"));

        return this.getRandom(archetypes);
    }

    /**
     * Returns a unique deck descriptor or characteristic, i.e. Eldrazi, Storm, etc.
     *
     * @return A deck descriptor.
     */
    private String getDescriptor() {
        List<String> archetypes = new ArrayList<String>(Arrays.asList("Eldrazi", "Collected Company", "Storm",
                "Scapeshift", "Tribal Flames", "Tron", "Hollow One"));

        return this.getRandom(archetypes);
    }

    /**
     * Generates a deck with context.
     *
     * @return A full deck string with context ready for output.
     */
    private String getDeck() {
        return this.addContext(this.getColorCombo() + " " + this.getDescriptor() + " " + this.getArchetype());
    }

    private <T> T getRandom(List<T> list) {
        return list.get((new Random()).nextInt(list.size()));
    }

    /**
     * Method that determines whether this handler handles the message in question. Returns true if the message body is
     * "!deck" exactly.
     *
     * @param string The body of the message.
     * @return True if {@code string} equals "!deck" exactly, false otherwise.
     */
    public boolean fire(String string) {
        Pattern pattern = Pattern.compile("^!deck$");

        return pattern.matcher(string).find();
    }

    /**
     * Returns a random deck list.
     *
     * @param string The body of the message.
     * @return Random deck list.
     */
    public String respond(String string) {
        return this.getDeck();
    }

    private class Pair<U, V> {
        U key;
        V value;

        public Pair(U key, V value) {
            this.key = key;
            this.value = value;
        }

        public U getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }
    }
}
