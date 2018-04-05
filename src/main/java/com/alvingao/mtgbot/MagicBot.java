package com.alvingao.mtgbot;

import java.util.Arrays;
import java.util.HashMap;

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

    public void authenticate(String clientAuthToken) {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(clientAuthToken);
        client = clientBuilder.build();
    }

    public void connect(String clientAuthToken) {
        if (client == null) {
            authenticate(clientAuthToken);
        }

        client.login();
    }

    public void disconnect() {
        client.logout();
    }

    public String getAuthorizationToken() {
        String authToken = client.getToken();
        return authToken.replace(DISCORD_BOT_PREFIX, "");
    }

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        String messageText = message.getContent();
        String[] messageParts = messageText.split(" ");

        String providedCommandName = null;
        if (messageParts.length > 0) {
            providedCommandName = messageParts[0];
        }

        if (plugins.keySet().contains(providedCommandName) != true) {
            return;
        }

        String[] providedCommandParams = new String[] {};
        if (messageParts.length > 1) {
            providedCommandParams = Arrays.copyOfRange(messageParts, 1, messageParts.length);
        }

        IPlugin plugin = plugins.get(providedCommandName);
        plugin.invokeCommand(providedCommandParams);
    }

    public void registerPlugin(IPlugin plugin) {
        String commandName = plugin.getCommandName();
        if (plugins.containsKey(commandName)) {
            throw new KeyAlreadyExistsException();
        }


        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(plugin);
        plugins.put(commandName, plugin);
    }

    public Boolean tryInvokePluginCommand(String commandName, String... commandArgs) {
        IPlugin plugin = plugins.get(commandName);
        Boolean pluginExists = plugin != null;
        if (pluginExists) {
            plugin.invokeCommand(commandArgs);
        }

        return pluginExists;
    }
}
