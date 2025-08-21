import java.util.List;
import java.util.Scanner;

public class Clanker {
    private static final TodoList todoList = new TodoList();

    private static void printHorizontalLine() {
        System.out.println("---------------------------------------");
    }

    private static void writePrompt(String... lines) {
        printHorizontalLine();
        for (String s : lines) {
            System.out.println(s);
        }
        printHorizontalLine();
    }

    private static void handleTodoTask(Parser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());

        TodoTask t = new TodoTask(description);

        todoList.addTask(t);

        writePrompt("Added new task:",
                t.toString(),
                String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private static void handleDeadlineTask(Parser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());
        String deadline = cmd.getOptionValue("by");

        DeadlineTask t = new DeadlineTask(description, deadline);

        todoList.addTask(t);

        writePrompt("Added new task:",
                t.toString(),
                String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private static void handleEventTask(Parser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());
        String from = cmd.getOptionValue("from");
        String to = cmd.getOptionValue("to");

        EventTask t = new EventTask(description, from, to);

        todoList.addTask(t);

        writePrompt("Added new task:",
                t.toString(),
                String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private static void handleList() {
        List<String> tasks = todoList.listTasks();
        String[] formattedTasks = new String[todoList.size()];
        int count = 0;
        for (String s : tasks) {
            formattedTasks[count] = String.format("%s. %s", count + 1, s);
            count += 1;
        }
        writePrompt(formattedTasks);
    }

    private static void handleMark(Parser.Command cmd) {
        int taskIndexMark = Integer.parseInt(cmd.getParameter(0)) - 1;

        try {
            todoList.markAsDone(taskIndexMark);
        } catch (IndexOutOfBoundsException e) {
            writePrompt("Could not find this task!");
            return;
        }

        writePrompt(
                "Marked task as done:",
                todoList.getTask(taskIndexMark).toString()
                );
    }

    private static void handleUnmark(Parser.Command cmd) {
        int taskIndexUnmark = Integer.parseInt(cmd.getParameter(0)) - 1;

        try {
            todoList.markAsUndone(taskIndexUnmark);
        } catch (IndexOutOfBoundsException e) {
            writePrompt("Could not find this task!");
            return;
        }

        writePrompt(
                "Marked task as not done:",
                todoList.getTask(taskIndexUnmark).toString()
                );
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
            Parser.Command cmd = Parser.parse(scanner.nextLine());

            switch (cmd.getImperative()) {
                case "todo":
                    handleTodoTask(cmd);
                    break;
                case "deadline":
                    handleDeadlineTask(cmd);
                    break;
                case "event":
                    handleEventTask(cmd);
                    break;
                case "list":
                    handleList();
                    break;
                case "mark":
                    handleMark(cmd);
                    break;
                case "unmark":
                    handleUnmark(cmd);
                    break;
                case "bye":
                    break repl;
                default:
                    writePrompt("Unknown command!");
                    break;
            }
        }

        writePrompt(exiting);
    }


}