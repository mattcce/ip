package grammars.command.parser.ast;

public interface AstVisitor<R> {
    R visitCommand(AstNode.Command node);

    R visitImperative(AstNode.Imperative node);

    R visitParameterList(AstNode.ParameterList node);

    R visitParameter(AstNode.Parameter node);

    R visitOptionList(AstNode.OptionList node);

    R visitOption(AstNode.Option node);

    R visitOptionName(AstNode.OptionName node);

    R visitOptionValue(AstNode.OptionValue node);

    R visitText(AstNode.Text node);

    R visitWord(AstNode.Word node);
}
