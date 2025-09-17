package clanker.command;

import clanker.Clanker;
import grammars.CommandParser;

/**
 * Stub for CommandHandler.
 */
public class CommandHandlerStub implements CommandHandler {
    private boolean hasBeenExecuted = false;

    public boolean hasBeenExecuted() {
        return this.hasBeenExecuted;
    }

    @Override
    public void handle(Clanker c, CommandParser.Command cmd) {
        this.hasBeenExecuted = true;
    }
}
