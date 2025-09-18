package grammars.command.lexer;

/**
 * Exception for errors encountered during lexing.
 */
public class LexerException extends Exception {
    private final LexerError lexerError;

    LexerException(LexerError lexerError) {
        super(lexerError.toString());
        this.lexerError = lexerError;
    }
}
