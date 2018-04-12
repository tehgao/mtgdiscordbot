package com.alvingao.mtgbot.client;

import java.util.HashMap;

import com.alvingao.mtgbot.plugins.IClientPlugin;
import com.google.inject.Inject;
import com.google.inject.spi.Message;

import org.apache.logging.log4j.Logger;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;

/**
 * A bot that processes Discord messages and generates custom responses based on behaviors specified by plugins.
 */
public class MagicBot {
    private final HashMap<String, IClientPlugin> plugins = new HashMap<String, IClientPlugin>();
    private final IDiscordClient client;
    private final Logger logger;

    @Inject
    public MagicBot(IDiscordClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    /**
     *
     */
    public MessageBuilder createMessageBuilder(IChannel channel) {

        return builder;
    }

    /**
     * Processes messages sent to the Discord server by other users and allows registered plugins to respond.
     */
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {
        IChannel channel = event.getChannel();
        IMessage message = event.getMessage();
        String messageContent = message.getContent();

        plugins.forEach((identifier, plugin) -> {
            if (plugin.canHandleMessage(messageContent)) {
                MessageBuilder responseBuilder = new MessageBuilder(client);
                responseBuilder.withChannel(channel);
                plugin.handleMessage(messageContent, responseBuilder);
            }
        });
    }

    /**
     * Attempts to register a plugin with the bot.
     *
     * @param plugin the plugin to register
     * @return whether the plugin registered successfully
     */
    public Boolean tryRegisterPlugin(IClientPlugin plugin) {
        String pluginIdentifier = plugin.getPluginIdentifier();
        if (plugins.containsKey(pluginIdentifier)) {
            String message = String.format(MagicBotMessages.PLUGIN_IDENTIFIER_ALREADY_EXISTS, pluginIdentifier);
            logger.error(message);
            return false;
        }

        plugins.put(pluginIdentifier, plugin);
        return true;
    }
}