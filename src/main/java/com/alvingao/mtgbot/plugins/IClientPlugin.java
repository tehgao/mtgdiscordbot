package com.alvingao.mtgbot.plugins;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;

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
     * @param messageContent the message to check
     * @return whether or not the message can be processed by the plugin
     */
    public Boolean canHandleMessage(String messageContent);

    /**
     * Attempts to process the provided message using the plugin.
     *
     * @param messageContent the message to process
     * @param responseBuilder a MessageBuilder with the client and channel already set
     */
    public IMessage handleMessage(String messageContent, MessageBuilder responseBuilder);
}
