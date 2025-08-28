package fmt;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Utility class for parsing commands.
 */
public class CommandParser {
    /**
     * Parses a command into a queryable structure.
     *
     * @param cmd The full string of the command.
     * @return A queryable Command.
     */
    public static Command parse(String cmd) {
        String[] splitCmd = Arrays.stream(cmd.split("/")).map(String::trim).toArray(String[]::new);

        String[] splitMainCommand = splitCmd[0].split(" ");
        String imperative = splitMainCommand[0];
        String[] parameters = Arrays.stream(splitMainCommand).skip(1).toArray(String[]::new);

        HashMap<String, String> options = new HashMap<>();
        for (int i = 1; i < splitCmd.length; i++) {
            String[] splitOption = splitCmd[i].split(" ", 2);
            String optionKey = splitOption[0];
            String optionValue = splitOption.length > 1 ? splitOption[1] : "";

            options.put(optionKey, optionValue);
        }

        return new Command(imperative, parameters, options);
    }

    /**
     * Command class that stores the various command tokens in an easily-queryable manner.
     */
    public static class Command {
        private final String imperative;
        private final String[] parameters;
        private final HashMap<String, String> options;

        /**
         * Standard constructor to be called by the parser only.
         *
         * @param imperative First word of the command.
         * @param parameters Subsequent words of the command.
         * @param options    Named options of the command.
         */
        public Command(String imperative, String[] parameters, HashMap<String, String> options) {
            this.imperative = imperative;
            this.parameters = parameters;
            this.options = options;
        }

        public String getImperative() {
            return this.imperative;
        }

        public String getParameter(int index) {
            return this.parameters[index];
        }

        public String[] getAllParameters() {
            return this.parameters;
        }

        /**
         * Returns the value associated with the key, if provided as an option.
         *
         * @param key The option key to look for.
         * @return The value associated with the given key.
         */
        public String getOptionValue(String key) {
            return this.options.get(key);
        }
    }
}
