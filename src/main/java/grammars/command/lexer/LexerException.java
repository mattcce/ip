package grammars.command.lexer;

/**
 * Exception for errors encountered during lexing.
 */
public class LexerException extends Exception {
    private final Error error;

    LexerException(Error error) {
        super(error.toString());
        this.error = error;
    }
}
