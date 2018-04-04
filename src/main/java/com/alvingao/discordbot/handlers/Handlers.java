package com.alvingao.discordbot.handlers;

import java.util.ArrayList;
import java.util.List;

public class Handlers {
    /**
     * Private constructor -- nobody should be instantiating this class
     */
    private Handlers() {

    }

    /**
     * Returns a List of all of the current active handlers.
     *
     * @return A List object containing all of the current active handlers.
     */
    public static List<IHandler> getHandlers() {
        List<IHandler> handlers = new ArrayList<IHandler>();

        // add your handlers here
        handlers.add(new DeckGeneratorHandler());

        return handlers;
    }
}
