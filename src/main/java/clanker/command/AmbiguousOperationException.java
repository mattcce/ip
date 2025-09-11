package clanker.command;

/**
 * Exception for when a specified operation is ambiguous.
 */
public class AmbiguousOperationException extends RuntimeException {
    public AmbiguousOperationException(String... operations) {
        super("Ambiguous operation; matching operations include: " + String.join(", ", operations));
    }
}
