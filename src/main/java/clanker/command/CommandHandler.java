package clanker.command;

import clanker.Clanker;
import grammars.CommandParser;

/**
 * Functional interface for command handlers.
 */
@FunctionalInterface
public interface CommandHandler {
    void handle(Clanker c, CommandParser.Command cmd);
}
