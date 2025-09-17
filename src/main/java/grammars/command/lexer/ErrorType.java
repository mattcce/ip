package grammars.command.lexer;

/**
 * Enumeration of all error types the lexer may encounter.
 */
enum ErrorType {
    UNEXPECTED_CHARACTER("Unexpected character"),
    UNTERMINATED_STRING("Unterminated string");

    private final String genericDescription;

    ErrorType(String genericDescription) {
        this.genericDescription = genericDescription;
    }

    String getGenericDescription() {
        return genericDescription;
    }
}
