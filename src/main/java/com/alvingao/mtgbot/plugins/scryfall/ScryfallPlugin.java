package com.alvingao.mtgbot.plugins.scryfall;

import java.util.regex.Pattern;

import com.alvingao.mtgbot.client.MagicBot;
import com.alvingao.mtgbot.plugins.IMagicBotPlugin;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;

/**
 * Parses Discord messages and returns helpful information about Magic: the Gathering cards from the Scryfall API.
 */
@Singleton
public class ScryfallPlugin implements IMagicBotPlugin {
    private static final Pattern CARD_NAME_PATTERN = Pattern.compile("\\[\\[[\\p{L}\\s]+\\]\\]");
    private static final Pattern PRICE_PATTERN = Pattern.compile("\\[\\[\\$[\\p{L}\\s]+\\]\\]");
    private final MagicBot botInstance;

    @Inject
    public ScryfallPlugin(MagicBot botInstance) {
        this.botInstance = botInstance;
        botInstance.tryRegisterPlugin(this);
    }

	@Override
	public String getPluginIdentifier() {
		return ScryfallPlugin.class.getName();
	}

	@Override
	public boolean canHandleMessage(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        String messageContent = message.getContent();

        boolean canHandle = CARD_NAME_PATTERN.matcher(messageContent).find();
        canHandle |= PRICE_PATTERN.matcher(messageContent).find();

        return canHandle;
	}

	@Override
	public void handleMessage(MessageReceivedEvent event) {
        MessageBuilder responseBuilder = botInstance.createMessageBuilder();
        responseBuilder.withContent("PLACEHOLDER_TEXT");
        responseBuilder.withChannel(event.getChannel());
        responseBuilder.build();
	}
}
