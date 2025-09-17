package grammars.command.parser.ast;

import java.util.ArrayList;

public abstract class AstNode {
    public abstract <R> R accept(AstVisitor<R> visitor);

    public static class Command extends AstNode {
        private final Imperative imperative;
        private final ParameterList parameterList;
        private final OptionList optionList;

        public Command(Imperative imperative, ParameterList parameterList, OptionList optionList) {
            this.imperative = imperative;
            this.parameterList = parameterList;
            this.optionList = optionList;
        }

        public Imperative getImperative() {
            return imperative;
        }

        public ParameterList getParameterList() {
            return parameterList;
        }

        public OptionList getOptionList() {
            return optionList;
        }

        @Override
        public <R> R accept(AstVisitor<R> visitor) {
            return visitor.visitCommand(this);
        }
    }

    public static class Imperative extends AstNode {
        private final Word word;

        /**
         * Constructs a new Imperative node.
         *
         * @param word Imperative word.
         */
        public Imperative(Word word) {
            this.word = word;
        }

        public Word getWord() {
            return word;
        }

        @Override
        public <R> R accept(AstVisitor<R> visitor) {
            return visitor.visitImperative(this);
        }
    }

    public static class ParameterList extends AstNode {
        private final ArrayList<Parameter> parameters;

        public ParameterList(ArrayList<Parameter> parameters) {
            this.parameters = parameters;
        }

        public ArrayList<Parameter> getParameters() {
            return parameters;
        }

        @Override
        public <R> R accept(AstVisitor<R> visitor) {
            return visitor.visitParameterList(this);
        }
    }

    public static class Parameter extends AstNode {
        private final Word word;

        public Parameter(Word word) {
            this.word = word;
        }

        public Word getWord() {
            return word;
        }

        @Override
        public <R> R accept(AstVisitor<R> visitor) {
            return visitor.visitParameter(this);
        }
    }

    public static class OptionList extends AstNode {
        private final ArrayList<Option> options;

        public OptionList(ArrayList<Option> options) {
            this.options = options;
        }

        public ArrayList<Option> getOptions() {
            return options;
        }

        @Override
        public <R> R accept(AstVisitor<R> visitor) {
            return visitor.visitOptionList(this);
        }
    }

    public static class Option extends AstNode {
        private final OptionName optionName;
        private final OptionValue optionValue;

        public Option(OptionName optionName, OptionValue optionValue) {
            this.optionName = optionName;
            this.optionValue = optionValue;
        }

        public OptionName getOptionName() {
            return optionName;
        }

        public OptionValue getOptionValue() {
            return optionValue;
        }

        @Override
        public <R> R accept(AstVisitor<R> visitor) {
            return visitor.visitOption(this);
        }
    }

    public static class OptionName extends AstNode {
        private final Word word;

        public OptionName(Word word) {
            this.word = word;
        }

        public Word getWord() {
            return word;
        }

        @Override
        public <R> R accept(AstVisitor<R> visitor) {
            return visitor.visitOptionName(this);
        }
    }

    public static class OptionValue extends AstNode {
        private final Text text;

        public OptionValue(Text text) {
            this.text = text;
        }

        public Text getText() {
            return text;
        }

        @Override
        public <R> R accept(AstVisitor<R> visitor) {
            return visitor.visitOptionValue(this);
        }
    }

    public static class Text extends AstNode {
        private final String text;

        public Text(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        @Override
        public <R> R accept(AstVisitor<R> visitor) {
            return visitor.visitText(this);
        }
    }

    public static class Word extends AstNode {
        private final String word;

        public Word(String word) {
            this.word = word;
        }

        public String getWord() {
            return this.word;
        }

        @Override
        public <R> R accept(AstVisitor<R> visitor) {
            return visitor.visitWord(this);
        }
    }
}
