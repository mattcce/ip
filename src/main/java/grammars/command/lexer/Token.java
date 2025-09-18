package grammars.command.lexer;

import grammars.command.utils.Location;

/**
 * Lexer tokens.
 */
public class Token {
    private final TokenType type;
    private final String literal;
    private final Location location;

    Token(TokenType type, String literal, Location location) {
        this.type = type;
        this.literal = literal;
        this.location = location;
    }

    public TokenType getType() {
        return this.type;
    }

    public String getLiteral() {
        return this.literal;
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public String toString() {
        return String.format("[%02d:%02d] %s %s",
                this.location.start(), this.location.end(), this.type.getDescription(), this.literal);
    }
}
