package grammars.command.lexer;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Lexed command, representing a linear stream of tokens.
 */
public class TokenisedCommand {
    private final String ingest;
    private final ArrayList<Token> tokens;

    TokenisedCommand(String ingest, ArrayList<Token> tokens) {
        this.ingest = ingest;
        this.tokens = tokens;
    }

    public Stream<Token> stream() {
        return this.tokens.stream();
    }
}
