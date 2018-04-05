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
    protected static final String COMMAND_ONE_NAME = "c1";
    protected static final String COMMAND_TWO_NAME = "c2";

    @Test
    public void shouldTryAuthenticating_WhenNoAuthorizationTokenProvided_AndThrowException() {
        Executable authFn = new Executable() {
            public void execute() throws Throwable {
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
        Mockito.when(plugin.getCommandName()).thenReturn(COMMAND_ONE_NAME);
        MagicBot.INSTANCE.registerPlugin(plugin);
        Assertions.assertEquals(1, MagicBot.plugins.size());
        Assertions.assertTrue(MagicBot.plugins.values().contains(plugin));
    }

    @Test
    public void shouldTryRegisteringPlugin_WhenProvidedPluginWithDuplicateName_AndThrowException() {
        IPlugin firstPlugin = Mockito.mock(IPlugin.class);
        Mockito.when(firstPlugin.getCommandName()).thenReturn(COMMAND_ONE_NAME);
        MagicBot.INSTANCE.registerPlugin(firstPlugin);

        final IPlugin secondPlugin = Mockito.mock(IPlugin.class);
        Mockito.when(secondPlugin.getCommandName()).thenReturn(COMMAND_ONE_NAME);
        Executable registerFn = new Executable() {
            public void execute() throws Throwable {
                MagicBot.INSTANCE.registerPlugin(secondPlugin);
            }
        };

        Assertions.assertThrows(KeyAlreadyExistsException.class, registerFn);
    }

    @Test
    public void shouldTryInvokingCommand_WhenNoCommandWithNameExists_AndReturnFalse() {
        Boolean wasAbleToRunCommand = MagicBot.INSTANCE.tryInvokePluginCommand(null);
        Assertions.assertFalse(wasAbleToRunCommand);
    }

    @Test
    public void shouldTryInvokingCommand_WhenCommandWithNameDoesExist_AndReturnTrue() {
        IPlugin firstPlugin = Mockito.mock(IPlugin.class);
        Mockito.when(firstPlugin.getCommandName()).thenReturn(COMMAND_ONE_NAME);
        MagicBot.INSTANCE.registerPlugin(firstPlugin);

        Boolean wasAbleToRunCommand = MagicBot.INSTANCE.tryInvokePluginCommand(COMMAND_ONE_NAME);
        Assertions.assertTrue(wasAbleToRunCommand);
    }
}
