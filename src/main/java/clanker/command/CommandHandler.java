package clanker.command;

import clanker.Clanker;
import grammars.command.Command;

/**
 * Functional interface for command handlers.
 */
@FunctionalInterface
public interface CommandHandler {
    void handle(Clanker c, Command cmd);
}
