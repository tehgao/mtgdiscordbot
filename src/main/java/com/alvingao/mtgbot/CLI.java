package com.alvingao.mtgbot;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public final class CLI {
    public static final int DEFAULT_CONSOLE_WIDTH = 120;

    public static final String HELP_MESSAGE_FOOTER = String.format("To report issues, visit https://github.com/tehgao/mtgdiscordbot/issues.%n");
    public static final String HELP_MESSAGE_HEADER = String.format("Initializes a Discord bot that responds to parameterized requests sent to it by other users.%n");
    public static final String HELP_MESSAGE_SYNTAX = "mtgbot";

    public static final String TOKEN_FLAG_DESCRIPTION = "A Discord client authorization token.";
    public static final String TOKEN_FLAG_LONG = "token";
    public static final String TOKEN_FLAG_SHORT = "T";

    private static Options consoleFlags = new Options();

    public static void main(String... args) {
        createConsoleFlag(TOKEN_FLAG_SHORT, TOKEN_FLAG_LONG, TOKEN_FLAG_DESCRIPTION, true);

        try {
            CommandLineParser cliParser = new DefaultParser();
            CommandLine cli = cliParser.parse(consoleFlags, args);
            String authToken = cli.getOptionValue(TOKEN_FLAG_SHORT);
            if (authToken == null) {
                printHelpMessage();
                return;
            }

            MagicBot.INSTANCE.connect(authToken);
        } catch (ParseException pEx) {
            System.err.print(pEx.getMessage());
            printHelpMessage();
            return;
        }
    }

    private static void createConsoleFlag(String shortFlag, String longFlag, String description, Boolean isRequired) {
        Builder optBuilder = Option.builder(shortFlag);
        optBuilder.required(isRequired);
        optBuilder.hasArg(true);
        optBuilder.desc(description);
        optBuilder.longOpt(longFlag);

        Option newFlag = optBuilder.build();
        consoleFlags.addOption(newFlag);
    }

    private static void printHelpMessage() {
        HelpFormatter hFormatter = new HelpFormatter();
        hFormatter.printHelp(DEFAULT_CONSOLE_WIDTH, HELP_MESSAGE_SYNTAX, HELP_MESSAGE_HEADER, consoleFlags, HELP_MESSAGE_FOOTER);
    }
}
