package com.alvingao.discordbot.handlers;

public interface IHandler {
    /**
     * Determines whether the Handler in question handles the current message.
     *
     * @param string The body of the message.
     * @return True if this is a request that the implementing class can handle, false otherwise.
     */
    public boolean fire(String string);

    /**
     * Produces a response based on the input. We assume that {@code this.fire()} has returned true for {@code string}.
     *
     * @param string The body of the message.
     * @return A response appropriate to {@code string}, the input.
     */
    public String respond(String string);
}
