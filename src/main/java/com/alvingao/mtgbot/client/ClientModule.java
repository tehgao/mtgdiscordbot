package com.alvingao.mtgbot.client;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

/**
 * Provides a pre-configured client that can interface with Discord servers.
 */
public class ClientModule extends AbstractModule {
    protected Logger logger = LogManager.getLogger();
    protected IDiscordClient discordClient;

    @Override
    protected void configure() {
        try {
            FileReader configReader = new FileReader(ClientConfig.FILENAME);
            Properties configProperties = new Properties();
            configProperties.load(configReader);

            ClientBuilder discordClientBuilder = new ClientBuilder();
            String authorizationToken = configProperties.getProperty(ClientConfig.AUTH_TOKEN_KEY);
            discordClientBuilder.withToken(authorizationToken);
            discordClient = discordClientBuilder.build();
            discordClient.login();
        } catch (IOException ex) {
            logger.error(ex.getStackTrace());
        } catch (DiscordException ex) {
            logger.error(ex.getStackTrace());
        }
    }

    @Provides
    protected IDiscordClient provideIDiscordClient() {
        return discordClient;
    }

    @Provides
    protected Logger provideLogger() {
        return logger;
    }
}
