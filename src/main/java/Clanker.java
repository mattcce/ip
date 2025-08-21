public class Clanker {
    private static void printHorizontalLine() {
        System.out.println("---------------------------------------");
    }

    private static void writePrompt(String[] lines) {
        for (String s : lines) {
            System.out.println(s);
        }
        printHorizontalLine();
    }

    public static void main(String[] args) {
        String[] greetings = new String[]{
                "Hello! I'm Clanker.",
                "What can I do you for today?",
        };

        String[] exiting = new String[] {
                "Bye. Hope to see you again soon!"
        };

        printHorizontalLine();

        writePrompt(greetings);

        writePrompt(exiting);
    }
}