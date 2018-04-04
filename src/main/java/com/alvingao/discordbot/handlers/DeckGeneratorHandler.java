package com.alvingao.discordbot.handlers;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class DeckGeneratorHandler implements IHandler {
    private List<String> archetypes;

    public DeckGeneratorHandler() {

    }

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

    private String getColorCombo() {
        List<String> colorCombos = new ArrayList<String>(Arrays.asList("mono-W", "mono-U", "mono-B", "mono-R", "mono-G",
                "WU", "WB", "WR", "GW", "UB", "UR", "UG", "BR", "GB", "GR", "Jund", "Bant", "Grixis", "Naya", "Esper",
                "Jeskai", "Mardu", "Sultai", "Temur", "Abzan", "four-color", "five-color", "colorless"));

        return this.getRandom(colorCombos);
    }

    private String getArchetype() {
        List<String> archetypes = new ArrayList<String>(Arrays.asList("Aggro", "Midrange", "Control", "Combo",
                "And Taxes"));

        return this.getRandom(archetypes);
    }

    private String getDescriptor() {
        List<String> archetypes = new ArrayList<String>(Arrays.asList("Eldrazi", "Collected Company", "Storm",
                "Scapeshift", "Tribal Flames", "Tron", "Hollow One"));

        return this.getRandom(archetypes);
    }

    private String getDeck() {
        return this.addContext(this.getColorCombo() + " " + this.getDescriptor() + " " + this.getArchetype());
    }

    private <T> T getRandom(List<T> list) {
        return list.get((new Random()).nextInt(list.size()));
    }

    public boolean fire(String string) {
        Pattern pattern = Pattern.compile("^!deck$");

        return pattern.matcher(string).find();
    }

    public String respond(String string) {
        return this.getDeck();
    }
}
