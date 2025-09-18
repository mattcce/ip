package clanker.command;

import clanker.Clanker;
import grammars.command.Command;

/**
 * Stub for CommandHandler.
 */
public class CommandHandlerStub implements CommandHandler {
    private boolean hasBeenExecuted = false;

    public boolean hasBeenExecuted() {
        return this.hasBeenExecuted;
    }

    @Override
    public void handle(Clanker c, Command cmd) {
        this.hasBeenExecuted = true;
    }
}
