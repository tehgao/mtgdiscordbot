package com.alvingao.mtgbot.client;

import com.google.inject.Inject;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.modules.IModule;

/**
 * Registers a MagicBot instance as a message handler when the app starts up.
 */
public final class ClientConfig implements IModule {
    public static final String AUTH_TOKEN_KEY = "authorization_token";
    public static final String BOT_AUTHOR = "alvingao";
    public static final String BOT_NAME = "MTG Discord Bot";
    public static final String BOT_VERSION = "0.1";
    public static final String FILENAME = "config.properties";
    public static final String MIN_DISCORD4J_VERSION = "2.10.0";

    private final IDiscordClient client;
    private final MagicBot botInstance;

    @Inject
    public ClientConfig(IDiscordClient client, MagicBot botInstance) {
        this.client = client;
        this.botInstance = botInstance;
        enable(client);
    }

	@Override
	public boolean enable(IDiscordClient client) {
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(botInstance);
		return true;
    }

	@Override
	public void disable() {
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.unregisterListener(botInstance);
    }

	@Override
	public String getName() {
		return BOT_NAME;
    }

	@Override
	public String getAuthor() {
		return BOT_AUTHOR;
    }

	@Override
	public String getVersion() {
		return BOT_VERSION;
    }

	@Override
	public String getMinimumDiscord4JVersion() {
		return MIN_DISCORD4J_VERSION;
	}
}
