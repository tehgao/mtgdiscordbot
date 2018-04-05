package com.alvingao.mtgbot.plugins;

public interface IPlugin {
    public String getCommandName();
    public void invokeCommand(String... commandArgs);
}
