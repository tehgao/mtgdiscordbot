package com.alvingao.mtgbot.plugins;

public interface IPlugin {
    /**
     * Returns a string that identifies this plugin uniquely.
     */
    public String getIdentifier();

    /**
     * Returns the regular expression string that indicates whether this plugin should run for any given set of text.
     */
    public String getTriggerPattern();

    /**
     * Performs an action based on the contents of the provided text.
     *
     * @param messageText the text of a message sent to the Discord server.
     */
    public void invokeCommand(String messageText);
}
