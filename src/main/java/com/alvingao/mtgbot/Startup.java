package com.alvingao.mtgbot;

import com.alvingao.mtgbot.client.ClientConfig;
import com.alvingao.mtgbot.client.ClientModule;
import com.alvingao.mtgbot.plugins.scryfall.ScryfallPlugin;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Handles initialization and dependency injection for the application.
 */
class Startup {
    public static void main(String... args) {
        Injector injector = Guice.createInjector(new ClientModule());
        initializeBot(injector);
        initializePlugins(injector);
    }

    /**
     * Loads the ClientConfig class, which will automatically request the injection of a MagicBot instance.
     * Once loaded, it will tell Discord4J to register that instance as the main message handler.
     */
    private static void initializeBot(Injector injector) {
        injector.getInstance(ClientConfig.class);
    }

    /**
     * Any plugins loaded here will automatically register themselves with the MagicBot instance and handle messages.
     */
    private static void initializePlugins(Injector injector) {
        injector.getInstance(ScryfallPlugin.class);
    }
}
