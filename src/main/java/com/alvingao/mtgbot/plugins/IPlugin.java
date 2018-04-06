package com.alvingao.mtgbot.plugins;

public interface IPlugin {
    public String getIdentifier();
    public String getTriggerPattern();
    public void invokeCommand(String messageText);
}
