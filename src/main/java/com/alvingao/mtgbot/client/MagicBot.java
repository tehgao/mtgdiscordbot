package com.alvingao.mtgbot.client;

import java.util.HashMap;

import com.alvingao.mtgbot.plugins.IMagicBotPlugin;
import com.alvingao.mtgbot.utils.ErrorMessages;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.logging.log4j.Logger;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

/**
 * A bot that processes Discord messages and generates custom responses based on behaviors specified by plugins.
 */
@Singleton
public class MagicBot {
    private final HashMap<String, IMagicBotPlugin> plugins = new HashMap<String, IMagicBotPlugin>();
    private final IDiscordClient client;
    private final Logger logger;

    @Inject
    public MagicBot(IDiscordClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    /**
     * Creates a new MessageBuilder using the underlying IDiscordClient instance.
     */
    public MessageBuilder createMessageBuilder() {
        return new MessageBuilder(client);
    }

    /**
     * Processes messages sent to the Discord server by other users and allows registered plugins to respond.
     */
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {
        plugins.forEach((identifier, plugin) -> {
            if (plugin.canHandleMessage(event)) {
                plugin.handleMessage(event);
            }
        });
    }

    /**
     * Attempts to register a plugin with the bot.
     *
     * @param plugin the plugin to register
     * @return whether the plugin registered successfully
     */
    public boolean tryRegisterPlugin(IMagicBotPlugin plugin) {
        String pluginIdentifier = plugin.getPluginIdentifier();
        if (plugins.containsKey(pluginIdentifier)) {
            String message = String.format(ErrorMessages.PLUGIN_IDENTIFIER_ALREADY_EXISTS, pluginIdentifier);
            logger.error(message);
            return false;
        }

        plugins.put(pluginIdentifier, plugin);
        return true;
    }

    /**
     * Attempts to disable and remove the specified plugin.
     *
     * @param pluginIdentifier the registered identifier of the plugin
     * @return whether the plugin was successfully unregistered
     */
    public boolean tryUnregisterPlugin(String pluginIdentifier) {
        IMagicBotPlugin plugin = plugins.get(pluginIdentifier);
        return plugins.remove(pluginIdentifier, plugin);
    }
}