package com.alvingao.mtgbot;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class MagicBotModule extends AbstractModule {
    protected static final String CONFIG_FILE_PATH = "config.properties";
    protected static final String TOKEN_KEY = "authorization_token";
    protected IDiscordClient discordClient;

    @Override
    protected void configure() {
        try {
            Properties props = new Properties();
            FileReader propReader = new FileReader(CONFIG_FILE_PATH);
            props.load(propReader);
            Names.bindProperties(binder(), props);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Provides
    IDiscordClient provideIDiscordClient(@Named(TOKEN_KEY) String authToken) {
        try {
            if (discordClient == null) {
                ClientBuilder builder = new ClientBuilder();
                builder.withToken(authToken);
                discordClient = builder.build();
            }

            if (discordClient.isLoggedIn() == false) {
                discordClient.login();
            }
        } catch (DiscordException ex) {
            ex.printStackTrace();
        }

        return discordClient;
    }
}
