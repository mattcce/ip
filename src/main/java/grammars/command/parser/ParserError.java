package grammars.command.parser;

import static grammars.command.utils.Utils.makeVisualDelimiter;
import static grammars.command.utils.Utils.reverseArrayList;

import java.util.ArrayList;

import grammars.command.lexer.Token;
import grammars.command.lexer.TokenType;
import grammars.command.utils.Location;

class ParserError {
    private final ArrayList<String> productionNonterminalStack = new ArrayList<>();
    private final String ingest;
    private final Token offendingToken;
    private final TokenType expectedTokenType;

    ParserError(String ingest, Token offendingToken, TokenType expectedTokenType) {
        this.ingest = ingest;
        this.offendingToken = offendingToken;
        this.expectedTokenType = expectedTokenType;
    }

    void addProductionNonterminal(String productionRule) {
        this.productionNonterminalStack.add(productionRule);
    }

    @Override
    public String toString() {
        ArrayList<String> lines = new ArrayList<>();

        lines.add("Error occurred during parsing.");

        lines.add(this.ingest);

        Location location = this.offendingToken.getLocation();
        lines.add(makeVisualDelimiter(location.start(), location.end()));

        String productionRuleStack = String.join(" > ", reverseArrayList(this.productionNonterminalStack));
        lines.add(String.format("Error occurred while applying production rules: %s", productionRuleStack));

        lines.add(String.format("Expected token: %s", expectedTokenType.getDescription()));

        lines.add(String.format("Found:          %s", offendingToken.getType().getDescription()));

        return String.join("\n", lines);
    }
}
