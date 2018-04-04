package com.alvingao.discordbot.bot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class BaseBot {
    public static BaseBot INSTANCE; // Singleton instance of the bot.
    public IDiscordClient client; // The instance of the discord client.

    public static void main(String[] args) { // Main method
        if (args.length < 1) // Needs a bot token provided
            throw new IllegalArgumentException("This bot needs at least 1 argument!");

        INSTANCE = login(args[0]); // Creates the bot instance and logs it in.
    }

    public BaseBot(IDiscordClient client) {
        this.client = client; // Sets the client instance to the one provided
    }

    /**
     * Logs in the discord bot and returns a bot instance.
     *
     * @param token The Discord token.
     * @return The bot instance.
     */
    public static BaseBot login(String token) {
        BaseBot bot = null; // Initializing the bot variable

        ClientBuilder builder = new ClientBuilder(); // Creates a new client builder instance
        builder.withToken(token); // Sets the bot token for the client
        try {
            IDiscordClient client = builder.login(); // Builds the IDiscordClient instance and logs it in
            bot = new MessageBot(client); // Creating the bot instance
        } catch (DiscordException e) { // Error occurred logging in
            System.err.println("Error occurred while logging in!");
            e.printStackTrace();
        }

        return bot;
    }
}
