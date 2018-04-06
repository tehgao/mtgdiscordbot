package com.alvingao.mtgbot.plugins;

public interface IPlugin {
    public String getCommandIdentifier();
    public String getTriggerPattern();
    public void invokeCommand(String messageText);
}
