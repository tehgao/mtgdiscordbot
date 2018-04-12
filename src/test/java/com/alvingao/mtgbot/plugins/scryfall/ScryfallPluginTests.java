package com.alvingao.mtgbot.plugins.scryfall;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.alvingao.mtgbot.client.MagicBot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;

@DisplayName("Scryfall Plugin Tests")
class ScryfallPluginTests {
    private static final String NON_MATCH_MESSAGE = "potato";
    private static final String CARD_MATCH_MESSAGE = "Yeah, I like [[Bogardan Hellkite]].";
    private static final String PRICE_MATCH_MESSAGE = "Something about the price of [[$Llanowar Elves]] seems off.";

    private MagicBot mockMagicbot;
    private MessageBuilder mockResponseBuilder;
    private MessageReceivedEvent mockMessageReceivedEvent;
    private IMessage mockMessage;
    private ScryfallPlugin plugin;

    @BeforeEach
    public void initialize() {
        mockMessage = mock(IMessage.class);
        mockMessageReceivedEvent = mock(MessageReceivedEvent.class);
        when(mockMessageReceivedEvent.getMessage()).thenReturn(mockMessage);

        mockResponseBuilder = mock(MessageBuilder.class);
        mockMagicbot = mock(MagicBot.class);
        when(mockMagicbot.createMessageBuilder()).thenReturn(mockResponseBuilder);

        plugin = new ScryfallPlugin(mockMagicbot);
    }

    @Test
    public void shouldCheckIfCanHandleMessage_WhenMessageDoesNotMatch_AndReturnFalse() {
        when(mockMessage.getContent()).thenReturn(NON_MATCH_MESSAGE);
        boolean isMatch = plugin.canHandleMessage(mockMessageReceivedEvent);
        assertFalse(isMatch);
    }

    @Test
    public void shouldCheckIfCanHandleMessage_WhenMessageMatchesCardNamePattern_AndReturnTrue() {
        when(mockMessage.getContent()).thenReturn(CARD_MATCH_MESSAGE);
        boolean isMatch = plugin.canHandleMessage(mockMessageReceivedEvent);
        assertTrue(isMatch);
    }

    @Test
    public void shouldCheckIfCanHandleMessage_WhenMessageMatchesCardPricePattern_AndReturnTrue() {
        when(mockMessage.getContent()).thenReturn(PRICE_MATCH_MESSAGE);
        boolean isMatch = plugin.canHandleMessage(mockMessageReceivedEvent);
        assertTrue(isMatch);
    }
}