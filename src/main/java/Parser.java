import java.util.Arrays;
import java.util.HashMap;

public class Parser {
    public static class Command {
        private final String imperative;
        private final String[] parameters;
        private final HashMap<String, String> options;

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

        public String getOptionValue(String key) {
            return this.options.get(key);
        }
    }

    public static Command parse(String cmd) {
        String[] splitCmd = Arrays.stream(cmd.split("/")).map(String::trim).toArray(String[]::new);

        String[] splitMainCommand = splitCmd[0].split(" ");
        String imperative = splitMainCommand[0];
        String[] parameters = Arrays.stream(splitMainCommand).skip(1).toArray(String[]::new);

        HashMap<String, String> options = new HashMap<>();
        for (int i = 1; i < splitCmd.length; i++) {
            String[] splitOption = splitCmd[i].split(" ", 2);
            String optionKey = splitOption[0];
            String optionValue = splitOption[1];

            options.put(optionKey, optionValue);
        }

        return new Command(imperative, parameters, options);
    }
}
