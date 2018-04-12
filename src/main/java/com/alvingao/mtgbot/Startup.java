package com.alvingao.mtgbot;

import com.alvingao.mtgbot.client.ClientModule;
import com.alvingao.mtgbot.client.MagicBot;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Handles initialization and dependency injection for the application.
 */
class Startup {
    public static void main(String... args) {
        Injector injector = Guice.createInjector(new ClientModule());
        injector.getInstance(MagicBot.class);
    }
}
