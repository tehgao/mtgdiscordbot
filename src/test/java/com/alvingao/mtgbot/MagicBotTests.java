package com.alvingao.mtgbot;

import javax.management.openmbean.KeyAlreadyExistsException;

import com.alvingao.mtgbot.plugins.IPlugin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import sx.blah.discord.util.DiscordException;

@DisplayName("MTG Bot Tests")
class MagicBotTests extends TestBase {
    protected static final String COMMAND_ONE_IDENTIFIER = "c1";
    protected static final String COMMAND_TWO_IDENTIFIER = "c2";

    @Test
    public void shouldTryAuthenticating_WhenNoAuthorizationTokenProvided_AndThrowException() {
        Executable authFn = new Executable() {
            public void execute() throws Throwable {
                MagicBot.client = null;
                MagicBot.INSTANCE.authenticate(null);
            }
        };

        Assertions.assertThrows(DiscordException.class, authFn);
    }

    @Test
    public void shouldTryAuthenticating_WhenAuthorizationTokenProvided_AndInitializeDiscordClient() {
        Assertions.assertEquals(TEST_AUTH_KEY, MagicBot.INSTANCE.getAuthorizationToken());
    }

    @Test
    public void shouldTryRegisteringPlugin_WhenProvidedPluginWithUniqueName_AndRegisterSuccessfully() {
        IPlugin plugin = Mockito.mock(IPlugin.class);
        Mockito.when(plugin.getIdentifier()).thenReturn(COMMAND_ONE_IDENTIFIER);
        MagicBot.INSTANCE.registerPlugin(plugin);
        Assertions.assertEquals(1, MagicBot.plugins.size());
        Assertions.assertTrue(MagicBot.plugins.values().contains(plugin));
    }

    @Test
    public void shouldTryRegisteringPlugin_WhenProvidedPluginWithDuplicateName_AndThrowException() {
        IPlugin firstPlugin = Mockito.mock(IPlugin.class);
        Mockito.when(firstPlugin.getIdentifier()).thenReturn(COMMAND_ONE_IDENTIFIER);
        MagicBot.INSTANCE.registerPlugin(firstPlugin);

        final IPlugin secondPlugin = Mockito.mock(IPlugin.class);
        Mockito.when(secondPlugin.getIdentifier()).thenReturn(COMMAND_ONE_IDENTIFIER);
        Executable registerFn = new Executable() {
            public void execute() throws Throwable {
                MagicBot.INSTANCE.registerPlugin(secondPlugin);
            }
        };

        Assertions.assertThrows(KeyAlreadyExistsException.class, registerFn);
    }
}
