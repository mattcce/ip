package grammars.command.parser.ast;

public class AstPrinter extends AstVisitor<String> {
    @Override
    public String visitCommand() {
    }

    @Override
    public String visitImperative() {
        return "";
    }

    @Override
    public String visitParameterList() {
        return "";
    }

    @Override
    public String visitParameter() {
        return "";
    }

    @Override
    public String visitOptionList() {
        return "";
    }

    @Override
    public String visitOption() {
        return "";
    }

    @Override
    public String visitOptionName() {
        return "";
    }

    @Override
    public String visitOptionValue() {
        return "";
    }

    @Override
    public String visitText() {
        return "";
    }

    @Override
    public String visitWord() {
        return "";
    }
}
