package com.alvingao.mtgbot;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import sx.blah.discord.api.IDiscordClient;

abstract class TestBase {
    protected static final String TEST_AUTH_KEY = "some_auth_key";

    protected ByteArrayOutputStream errStream;
    protected ByteArrayOutputStream outStream;

    @BeforeEach
    protected void routeConsoleOutputToLocalStreams() {
        outStream = new ByteArrayOutputStream();
        errStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        System.setErr(new PrintStream(errStream));
    }

    @BeforeEach
    protected void initializeBotInstanceAndPreventDiscordApiCalls() {
         MagicBot.INSTANCE.authenticate(TEST_AUTH_KEY);
         IDiscordClient mockDiscordClient = Mockito.spy(MagicBot.client);
         Mockito.doNothing().when(mockDiscordClient).login();
         Mockito.doNothing().when(mockDiscordClient).logout();
         MagicBot.client = mockDiscordClient;
    }

    @AfterEach
    protected void clearBotInstance() {
        MagicBot.client = null;
        MagicBot.plugins.clear();
    }

    @AfterEach
    protected void resetConsoleOutputToDefaultStreams() {
        try {
            System.setOut(System.out);
            System.setErr(System.err);
            outStream.close();
            errStream.close();
            outStream = null;
            errStream = null;
        } catch (Exception ex) {
            Assertions.fail("Failed to clean up streams during test session");
        }
    }
}