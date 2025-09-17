package grammars.command.lexer;

import java.util.ArrayList;

/**
 * Errors encountered by the lexer.
 */
class Error {
    private final ErrorType errorType;
    private final String ingest;
    private final String offendingLiteral;
    private final Location location;

    Error(ErrorType errorType, String ingest, String offendingLiteral, Location location) {
        this.errorType = errorType;
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

        lines.add(String.format("%s: %s", this.errorType.getGenericDescription(), this.offendingLiteral));

        return String.join("\n", lines);
    }

    private String makeVisualDelimiter(int start, int end) {
        StringBuilder sb = new StringBuilder();
        int current = 0;

        while (current < start) {
            sb.append(" ");
            current++;
        }

        sb.append("^");
        current++;

        while (current < end - 1) {
            sb.append("-");
            current++;
        }

        if (current == end - 1) {
            sb.append("^");
        }

        return sb.toString();
    }
}
