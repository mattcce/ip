package clanker;

import java.util.Arrays;

import clanker.command.AmbiguousOperationException;
import clanker.command.CommandHandler;
import clanker.command.UnknownOperationException;
import parsers.CommandParser;

/**
 * Stores all commands and associated features (e.g. handlers).
 */
public enum Binding {
    TODO("todo", Clanker::handleTodoTask),
    DEADLINE("deadline", Clanker::handleDeadlineTask),
    EVENT("event", Clanker::handleEventTask),
    LIST("list", Clanker::handleList),
    MARK("mark", Clanker::handleMark),
    UNMARK("unmark", Clanker::handleUnmark),
    DELETE("delete", Clanker::handleDelete),
    FIND("find", Clanker::handleFind),
    BYE("bye", Clanker::handleExit),
    SERIALISE("serialise", Clanker::handleSerialise);

    private final String fullImperative;
    private final CommandHandler handler;

    Binding(String fullImperative, CommandHandler handler) {
        this.fullImperative = fullImperative;
        this.handler = handler;
    }

    /**
     * Resolves the relevant binding for the given input command.
     *
     * @param cmd Parsed command.
     * @return Relevant binding.
     */
    public static Binding resolveBinding(CommandParser.Command cmd) throws UnknownOperationException, AmbiguousOperationException {
        String imperativeHint = cmd.getImperative();
        Binding[] candidates = Arrays.stream(Binding.values())
            .filter(b -> isCandidate(b.fullImperative, imperativeHint))
            .toArray(Binding[]::new);

        if (candidates.length == 1) {
            return candidates[0];
        } else if (candidates.length == 0) {
            throw new UnknownOperationException();
        } else if (candidates.length > 1) {
            throw new AmbiguousOperationException(Arrays.stream(candidates)
                .map(b -> b.fullImperative)
                .toArray(String[]::new));
        } else {
            // unreachable
            throw new RuntimeException();
        }
    }

    /**
     * Returns true if the hint matches a given imperative. An imperative is matched if the hint is a prefix of that
     * imperative.
     *
     * @param fullImperative The full imperative to match against.
     * @param hint           The unresolved hint to be matched.
     * @return A boolean indicating whether the hint is matched to the given imperative.
     */
    public static boolean isCandidate(String fullImperative, String hint) {
        return fullImperative.startsWith(hint);
    }

    public CommandHandler getHandler() {
        return this.handler;
    }
}
