import java.util.List;
import java.util.Scanner;

public class Clanker {
    private static final TodoList todoList = new TodoList();

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

        repl:
        while (true) {
            String cmd = scanner.nextLine();

            switch (cmd) {
                case "list":
                    List<String> tasks = todoList.listTasks();
                    String[] formattedTasks = new String[todoList.size()];
                    int count = 0;
                    for (String s : todoList.listTasks()) {
                        formattedTasks[count] = String.format("%s. %s", count + 1, tasks.get(count));
                        count += 1;
                    }
                    writePrompt(formattedTasks);
                    break;
                case "bye":
                    break repl;
                default:
                    todoList.addTask(cmd);
                    writePrompt(String.format("added: %s", cmd));
                    break;
            }
        }

        writePrompt(exiting);
    }
}