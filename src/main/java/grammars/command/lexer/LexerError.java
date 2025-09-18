package grammars.command.lexer;

import static grammars.command.utils.Utils.makeVisualDelimiter;

import java.util.ArrayList;

import grammars.command.utils.Location;

/**
 * Errors encountered by the lexer.
 */
class LexerError {
    private final LexerErrorType lexerErrorType;
    private final String ingest;
    private final String offendingLiteral;
    private final Location location;

    LexerError(LexerErrorType lexerErrorType, String ingest, String offendingLiteral, Location location) {
        this.lexerErrorType = lexerErrorType;
        this.ingest = ingest;
        this.offendingLiteral = offendingLiteral;
        this.location = location;
    }

    @Override
    public String toString() {
        ArrayList<String> lines = new ArrayList<>();

        lines.add("Error occurred during lexing.");

        lines.add(this.ingest);

        lines.add(makeVisualDelimiter(this.location.start(), this.location.end()));

        lines.add(String.format("%s: %s", this.lexerErrorType.getGenericDescription(), this.offendingLiteral));

        return String.join("\n", lines);
    }
}
