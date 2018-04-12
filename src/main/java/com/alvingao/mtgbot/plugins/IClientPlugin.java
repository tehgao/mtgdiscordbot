package com.alvingao.mtgbot.plugins;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Represents a plugin that can be registered with an instance of MagicBot.
 */
public interface IClientPlugin {
    /**
     * Returns the identifier used to register this plugin with an instance of MagicBot.
     *
     * @return the identifier of the plugin
     */
    public String getPluginIdentifier();

    /**
     * Returns whether this plugin can handle a given message.
     *
     * @param event the message event to check
     * @return whether or not the message can be processed by the plugin
     */
    public Boolean canHandleMessage(MessageReceivedEvent event);

    /**
     * Attempts to process the provided message using the plugin.
     *
     * @param event the message event to process
     */
    public void handleMessage(MessageReceivedEvent event);
}
