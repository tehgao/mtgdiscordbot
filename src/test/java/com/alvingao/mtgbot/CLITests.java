package com.alvingao.mtgbot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Command Line Interface Tests")
class CLITests extends TestBase {
    @Test
    public void shouldRun_WhenInvokedWithZeroArgs_AndPrintErrorMessage() {
        CLI.main();
        Assertions.assertNotEquals(0, errStream.size());
    }

    @Test
    public void shouldRun_WhenInvokedWithZeroArgs_AndPrintHelpMessage() {
        CLI.main();
        Assertions.assertNotEquals(0, outStream.size());

        String output = outStream.toString();
        Assertions.assertTrue(output.contains(CLI.HELP_MESSAGE_HEADER));
        Assertions.assertTrue(output.contains(CLI.HELP_MESSAGE_FOOTER));
        Assertions.assertTrue(output.contains(CLI.HELP_MESSAGE_SYNTAX));
        Assertions.assertTrue(output.contains(CLI.TOKEN_FLAG_LONG));
        Assertions.assertTrue(output.contains(CLI.TOKEN_FLAG_SHORT));
        Assertions.assertTrue(output.contains(CLI.TOKEN_FLAG_DESCRIPTION));
    }

    @ParameterizedTest
    @ValueSource(strings = { CLI.TOKEN_FLAG_LONG, CLI.TOKEN_FLAG_SHORT })
    public void shouldRun_WhenInvokedWithFlagForAuthToken_AndSetMagicBotTokenValue(String tokenFlag) {
        CLI.main(String.format("-%s", tokenFlag), TEST_AUTH_KEY);
        Assertions.assertEquals(0, errStream.size());
        Assertions.assertEquals(TEST_AUTH_KEY, MagicBot.INSTANCE.getAuthorizationToken());
    }
}
