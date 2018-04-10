package com.alvingao.mtgbot;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Startup {
    public static void main(String... args) {
        Injector injector = Guice.createInjector(new MagicBotModule());
        injector.getInstance(MagicBot.class);
    }
}
