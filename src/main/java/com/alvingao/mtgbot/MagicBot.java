package com.alvingao.mtgbot;

import java.util.HashMap;

import javax.management.openmbean.KeyAlreadyExistsException;

import com.alvingao.mtgbot.plugins.IPlugin;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

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

    public void registerPlugin(IPlugin plugin) {
        String commandName = plugin.getCommandName();
        if (plugins.containsKey(commandName)) {
            throw new KeyAlreadyExistsException();
        }

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
