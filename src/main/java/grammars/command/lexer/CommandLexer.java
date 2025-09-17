package grammars.command.lexer;

import java.util.ArrayList;

/**
 * Lexer for commands. Recognises the following regular grammar (Level 3):
 *
 * <pre>
 * {@code
 * <word> ::= [A-z0-9]+
 * <text> ::= "[A-z0-9][A-z0-9 ]+"
 * <slash> ::= /
 * <colon> ::= :
 * <terminal> ::= $
 * }
 * </pre>
 */
public class CommandLexer {
    private final String ingest;
    private final ArrayList<Token> tokens = new ArrayList<>();

    // current lexeme
    private int start = 0;
    private int current = 0;

    private CommandLexer(String ingest) {
        this.ingest = ingest;
    }

    /**
     * Lexes a command.
     *
     * @param ingest Command string to be lexed.
     * @return A TokenisedCommand consisting of the lexed tokens.
     * @throws LexerException When an error occurs during lexing.
     */
    public static TokenisedCommand lex(String ingest) throws LexerException {
        CommandLexer lexer = new CommandLexer(ingest);
        lexer.lex();
        return lexer.toTokenisedCommand();
    }

    private static boolean isCharacterOfWord(char c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || ('0' <= c && c <= '9');
    }

    private void lex() throws LexerException {
        while (!this.isAtEnd()) {
            this.scanToken();
            start = current;
        }

        this.addToken(TokenType.TERMINAL);
    }

    private boolean isAtEnd() {
        return current >= ingest.length();
    }

    private void scanToken() throws LexerException {
        char c = this.advance();
        switch (c) {
        case '/':
            this.addToken(TokenType.SLASH);
            break;
        case ':':
            this.addToken(TokenType.COLON);
            break;
        case 'A':
        case 'a':
        case 'B':
        case 'b':
        case 'C':
        case 'c':
        case 'D':
        case 'd':
        case 'E':
        case 'e':
        case 'F':
        case 'f':
        case 'G':
        case 'g':
        case 'H':
        case 'h':
        case 'I':
        case 'i':
        case 'J':
        case 'j':
        case 'K':
        case 'k':
        case 'L':
        case 'l':
        case 'M':
        case 'm':
        case 'N':
        case 'n':
        case 'O':
        case 'o':
        case 'P':
        case 'p':
        case 'Q':
        case 'q':
        case 'R':
        case 'r':
        case 'S':
        case 's':
        case 'T':
        case 't':
        case 'U':
        case 'u':
        case 'V':
        case 'v':
        case 'W':
        case 'w':
        case 'X':
        case 'x':
        case 'Y':
        case 'y':
        case 'Z':
        case 'z':
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            this.munchWord();
            this.addToken(TokenType.WORD);
            break;
        case '"':
            this.munchText();

            // trim quotes
            String literal = this.ingest.substring(start + 1, current - 1);
            this.addToken(TokenType.TEXT, literal, start + 1, current - 1);
            break;
        case ' ':
            // ignore whitespace
            break;
        default:
            this.error(ErrorType.UNEXPECTED_CHARACTER);
            break;
        }
    }

    private char advance() {
        char c = ingest.charAt(current);
        current += 1;
        return c;
    }

    private void addToken(TokenType type) {
        String literal = this.ingest.substring(start, current);
        this.addToken(type, literal, start, current);
    }

    private void addToken(TokenType type, String literal, int start, int end) {
        this.tokens.add(new Token(type, literal, new Location(start, end)));
    }

    private void munchWord() {
        while (!this.isAtEnd() && isCharacterOfWord(this.peek())) {
            this.advance();
        }
    }

    private char peek() {
        return ingest.charAt(current);
    }

    private void munchText() throws LexerException {
        while (!this.isAtEnd() && this.peek() != '"') {
            this.advance();
        }

        if (this.isAtEnd()) {
            this.error(ErrorType.UNTERMINATED_STRING);
        }

        this.advance(); // closing quote
    }

    private void error(ErrorType type) throws LexerException {
        String offendingLiteral = this.ingest.substring(start, current);
        Error error = new Error(type, this.ingest, offendingLiteral, new Location(start, current));
        throw new LexerException(error);
    }

    private TokenisedCommand toTokenisedCommand() {
        return new TokenisedCommand(this.ingest, this.tokens);
    }
}
