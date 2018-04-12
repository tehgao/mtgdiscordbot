package com.alvingao.mtgbot.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alvingao.mtgbot.plugins.IMagicBotPlugin;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

@DisplayName("MTG Bot Tests")
class MagicBotTests {
    private IDiscordClient mockDiscordClient;
    private IMagicBotPlugin mockPlugin;
    private Logger mockLogger;
    private MagicBot botInstance;

    @BeforeEach
    public void initialize() {
        mockDiscordClient = mock(IDiscordClient.class);
        mockLogger = mock(Logger.class);
        mockPlugin = mock(IMagicBotPlugin.class);
        when(mockPlugin.getPluginIdentifier()).thenReturn("PLUGIN_A");

        botInstance = new MagicBot(mockDiscordClient, mockLogger);
    }

    @Test
    public void shouldTryToRegisterPlugin_WhenPluginNotRegisteredYet_AndReturnTrue() {
        boolean wasRegistered = botInstance.tryRegisterPlugin(mockPlugin);
        assertTrue(wasRegistered);
    }

    @Test
    public void shouldTryToRegisterPlugin_WhenPluginAlreadyRegistered_AndReturnFalse() {
        boolean wasRegisteredFirstTime = botInstance.tryRegisterPlugin(mockPlugin);
        boolean wasRegisteredSecondTime = botInstance.tryRegisterPlugin(mockPlugin);
        assertTrue(wasRegisteredFirstTime);
        assertFalse(wasRegisteredSecondTime);
    }

    @Test
    public void shouldCheckIfCanHandleMessage_WhenPluginsCannot_AndDoNothing() {
        MessageReceivedEvent mockEvent = mock(MessageReceivedEvent.class);
        when(mockPlugin.canHandleMessage(mockEvent)).thenReturn(false);

        botInstance.tryRegisterPlugin(mockPlugin);
        botInstance.onMessageReceived(mockEvent);
        verify(mockPlugin, times(0)).handleMessage(mockEvent);
    }

    @Test
    public void shouldCheckIfCanHandleMessage_WhenPluginsCan_AndCallHandler() {
        MessageReceivedEvent mockEvent = mock(MessageReceivedEvent.class);
        when(mockPlugin.canHandleMessage(mockEvent)).thenReturn(true);

        botInstance.tryRegisterPlugin(mockPlugin);
        botInstance.onMessageReceived(mockEvent);
        verify(mockPlugin, times(1)).handleMessage(mockEvent);
    }

    @Test
    public void shouldCheckIfCanHandleMessage_WhenOnlySomePluginsCan_AndOnlyCallThoseHandlers() {
        MessageReceivedEvent mockEvent = mock(MessageReceivedEvent.class);
        IMagicBotPlugin mockPluginA = mockPlugin;
        IMagicBotPlugin mockPluginB = mock(IMagicBotPlugin.class);
        IMagicBotPlugin mockPluginC = mock(IMagicBotPlugin.class);
        when(mockPluginA.canHandleMessage(mockEvent)).thenReturn(true);
        when(mockPluginB.getPluginIdentifier()).thenReturn("PLUGIN_B");
        when(mockPluginB.canHandleMessage(mockEvent)).thenReturn(false);
        when(mockPluginC.getPluginIdentifier()).thenReturn("PLUGIN_C");
        when(mockPluginC.canHandleMessage(mockEvent)).thenReturn(true);

        botInstance.tryRegisterPlugin(mockPluginA);
        botInstance.tryRegisterPlugin(mockPluginB);
        botInstance.tryRegisterPlugin(mockPluginC);
        botInstance.onMessageReceived(mockEvent);
        verify(mockPluginA, times(1)).handleMessage(mockEvent);
        verify(mockPluginB, times(0)).handleMessage(mockEvent);
        verify(mockPluginC, times(1)).handleMessage(mockEvent);
    }

    @Test
    public void shouldCreateMessageBuilder_WhenRequested_AndReturnMessageBuilderWithDiscordClientInstance() {
        MessageBuilder builder = botInstance.createMessageBuilder();
        long channelId = 1234;
        builder.withChannel(channelId);
        verify(mockDiscordClient, times(1)).getChannelByID(channelId);
    }
}
