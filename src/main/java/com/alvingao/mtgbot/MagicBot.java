package com.alvingao.mtgbot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.management.openmbean.KeyAlreadyExistsException;

import com.alvingao.mtgbot.plugins.IPlugin;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;

public enum MagicBot {
    INSTANCE;

    private static final String DISCORD_BOT_PREFIX = "Bot ";

    public static HashMap<String, IPlugin> plugins = new HashMap<String, IPlugin>();
    public static IDiscordClient client;

    /**
     * Creates an internally-managed IDiscordClient using the provided authorization token, then attempts to log in
     * using the credentials of that client.
     *
     * @param clientAuthToken a security token provided by Discord for authorizing bots with a server
     */
    public void authenticate(String clientAuthToken) {
        if (MagicBot.client == null) {
            ClientBuilder clientBuilder = new ClientBuilder();
            clientBuilder.withToken(clientAuthToken);
            MagicBot.client = clientBuilder.build();
        }
    }

    /**
     * Connects the underlying IDiscordClient to the Discord server, logging in with the credentials provided to
     * the authenticate method.
     *
     * @return whether the connection was established
     */
    public Boolean tryConnect() {
        if (MagicBot.client == null) {
            return false;
        }

        if (client.isLoggedIn() != true) {
            MagicBot.client.login();
        }

        return MagicBot.client.isLoggedIn();
    }

    /**
     * Disconnects the underlying IDiscordClient from the Discord server. If the client is not initialized or is not
     * currently logged in, does nothing.
     */
    public void disconnect() {
        if (MagicBot.client != null && MagicBot.client.isLoggedIn()) {
            MagicBot.client.logout();
        }
    }

    /**
     * Retrieves the authorization token of the underlying IDiscordClient instance.
     *
     * @return the authorization token if the client is initialized, null if the client is not
     */
    public String getAuthorizationToken() {
        if (MagicBot.client == null) {
            return null;
        }

        String authToken = MagicBot.client.getToken();
        return authToken.replace(DISCORD_BOT_PREFIX, "");
    }

    /**
     * Handles a message from the Discord server, checking the triggers of all registered IPlugin instances and invoking
     * the command of any IPlugin that matches with the message's content.
     *
     * @param event an event containing the message sent to the Discord server
     */
    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        String messageText = message.getContent();

        plugins.forEach((identifier, plugin) -> {
            String triggerPattern = plugin.getTriggerPattern();
            if (Pattern.matches(triggerPattern, messageText)) {
                plugin.invokeCommand(messageText);
            }
        });
    }

    /**
     * Registers an IPlugin with the bot; if an IPlugin with the same identifier exists, a KeyAlreadyExists exception is
     * thrown.
     */
    public void registerPlugin(IPlugin plugin) throws KeyAlreadyExistsException {
        String commandIdentifier = plugin.getIdentifier();
        if (plugins.containsKey(commandIdentifier)) {
            throw new KeyAlreadyExistsException();
        }

        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(plugin);
        plugins.put(commandIdentifier, plugin);
    }
}
