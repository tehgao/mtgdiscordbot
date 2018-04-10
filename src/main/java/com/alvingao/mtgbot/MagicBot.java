package com.alvingao.mtgbot;

import com.google.inject.Inject;

import sx.blah.discord.api.IDiscordClient;

class MagicBot {
    protected final IDiscordClient client;

    @Inject
    MagicBot(IDiscordClient client) {
        this.client = client;
    }
}
