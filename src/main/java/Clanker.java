import java.util.Scanner;

public class Clanker {
    private static void printHorizontalLine() {
        System.out.println("---------------------------------------");
    }

    private static void writePrompt(String[] lines) {
        printHorizontalLine();
        for (String s : lines) {
            System.out.println(s);
        }
        printHorizontalLine();
    }

    private static void writePrompt(String line) {
        printHorizontalLine();
        System.out.println(line);
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

        writePrompt(greetings);

        // REPL
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String cmd = scanner.nextLine();

            if (cmd.equals("bye")) {
                break;
            } else {
                writePrompt(cmd);
            }
        }

        writePrompt(exiting);
    }
}