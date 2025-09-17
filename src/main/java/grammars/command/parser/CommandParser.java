package grammars.command.parser;

import java.util.ArrayList;

import grammars.command.lexer.CommandLexer;
import grammars.command.lexer.LexerException;
import grammars.command.lexer.Token;
import grammars.command.lexer.TokenType;
import grammars.command.lexer.TokenisedCommand;
import grammars.command.parser.ast.AstNode;

/**
 * Parser for commands. Recognises the following context-free grammar (Level 2) consisting of tokens obtained from the
 * lexer (tokens from lexer are in CAPITAL:
 * <pre>
 * {@code
 * command          → imperative parameter_list option_list TERMINAL
 * imperative       → WORD
 * parameter_list   → ( parameter )+
 * parameter        → word
 * option_list      → ( option )+
 * option           → SLASH option_name (COLON option_value)*
 * option_name      → word
 * option_value     → text
 * text             → TEXT
 * word             → WORD
 * }
 * </pre>
 */
public class CommandParser {
    private final TokenisedCommand tokenisedCommand;
    private int currentTokenIndex = 0;

    private CommandParser(TokenisedCommand tokenisedCommand) {
        this.tokenisedCommand = tokenisedCommand;
    }

    public static AstNode.Command parse(String command) throws LexerException {
        CommandParser parser = new CommandParser(CommandLexer.lex(command));
        return parser.parseCommand();
    }

    private AstNode.Command parseCommand() {
        AstNode.Imperative imperative = this.parseImperative();
        AstNode.ParameterList parameterList = this.parseParameterList();
        AstNode.OptionList optionList = this.parseOptionList();
        return new AstNode.Command(imperative, parameterList, optionList);
    }

    private AstNode.Imperative parseImperative() {
        AstNode.Word word = this.parseWord();
        return new AstNode.Imperative(word);
    }

    private AstNode.ParameterList parseParameterList() {
        ArrayList<AstNode.Parameter> parameters = new ArrayList<>();

        // FOLLOW(parameter_list) = { SLASH, TERMINAL }
        while (!this.check(TokenType.SLASH, TokenType.TERMINAL)) {
            AstNode.Parameter parameter = this.parseParameter();
            parameters.add(parameter);
        }

        return new AstNode.ParameterList(parameters);
    }

    private AstNode.Parameter parseParameter() {
        AstNode.Word word = this.parseWord();
        return new AstNode.Parameter(word);
    }

    private AstNode.OptionList parseOptionList() {
        ArrayList<AstNode.Option> options = new ArrayList<>();

        // FOLLOW(option_list) = { TERMINAL }
        // FIRST(option) = { SLASH }
        while (!this.check(TokenType.TERMINAL) && this.check(TokenType.SLASH)) {
            this.eat(TokenType.SLASH);
            AstNode.Option option = this.parseOption();
            options.add(option);
        }

        return new AstNode.OptionList(options);
    }

    private AstNode.Option parseOption() {
        AstNode.OptionName optionName = this.parseOptionName();
        AstNode.OptionValue optionValue = this.parseOptionValue();
        return new AstNode.Option(optionName, optionValue);
    }

    private AstNode.OptionName parseOptionName() {
        AstNode.Word word = this.parseWord();
        return new AstNode.OptionName(word);
    }

    private AstNode.OptionValue parseOptionValue() {
        if (this.check(TokenType.COLON)) {
            this.eat(TokenType.COLON);
            AstNode.Text text = this.parseText();
            return new AstNode.OptionValue(text);
        } else {
            return null;
        }
    }

    private AstNode.Word parseWord() {
        String word = this.eat(TokenType.WORD).getLiteral();
        return new AstNode.Word(word);
    }

    private AstNode.Text parseText() {
        String text = this.eat(TokenType.TEXT).getLiteral();
        return new AstNode.Text(text);
    }

    private Token eat(TokenType type) {
        if (this.check(type)) {
            return this.advance();
        }

        // TODO: throw parsing exception
        return null;
    }

    private Token advance() {
        Token token = this.peek();

        if (!this.isAtEnd()) {
            this.currentTokenIndex += 1;
        }
        ;

        return token;
    }

    private boolean isAtEnd() {
        return this.peek().getType() == TokenType.TERMINAL;
    }

    private boolean check(TokenType... types) {
        for (TokenType type : types) {
            if (this.peek().getType() == type) {
                return true;
            }
        }

        return false;
    }

    private Token peek() {
        return this.tokenisedCommand.getAtIndex(this.currentTokenIndex);
    }
}
