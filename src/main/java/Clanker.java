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

        if (description.isEmpty()) {
            writePrompt("Oops! The description of a task cannot be empty!");
            return;
        }

        TodoTask t = new TodoTask(description);

        todoList.addTask(t);

        writePrompt("Added new task:",
                t.toString(),
                String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private static void handleDeadlineTask(Parser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());

        if (description.isEmpty()) {
            writePrompt("Oops! The description of a task cannot be empty!");
            return;
        }

        String deadline = cmd.getOptionValue("by");

        if (deadline == null || deadline.isEmpty()) {
            writePrompt("Oops! You must specify a deadline!");
            return;
        }

        DeadlineTask t = new DeadlineTask(description, deadline);

        todoList.addTask(t);

        writePrompt("Added new task:",
                t.toString(),
                String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private static void handleEventTask(Parser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());

        if (description.isEmpty()) {
            writePrompt("Oops! The description of a task cannot be empty!");
            return;
        }

        String from = cmd.getOptionValue("from");
        String to = cmd.getOptionValue("to");

        if (from == null || to == null || from.isEmpty() || to.isEmpty()) {
            writePrompt("Oops! You must specify a from and a to date/time for events!");
            return;
        }

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
        int taskIndex = Integer.parseInt(cmd.getParameter(0)) - 1;

        try {
            todoList.markAsDone(taskIndex);
        } catch (IndexOutOfBoundsException e) {
            writePrompt("Could not find this task!");
            return;
        }

        writePrompt(
                "Marked task as done:",
                todoList.getTask(taskIndex).toString()
        );
    }

    private static void handleUnmark(Parser.Command cmd) {
        int taskIndex = Integer.parseInt(cmd.getParameter(0)) - 1;

        try {
            todoList.markAsUndone(taskIndex);
        } catch (IndexOutOfBoundsException e) {
            writePrompt("Could not find this task!");
            return;
        }

        writePrompt(
                "Marked task as not done:",
                todoList.getTask(taskIndex).toString()
        );
    }

    private static void handleDelete(Parser.Command cmd) {
        int taskIndex = Integer.parseInt(cmd.getParameter(0)) - 1;

        Task t;
        try {
            t = todoList.deleteTask(taskIndex);
        } catch (IndexOutOfBoundsException e) {
            writePrompt("Could not find this task!");
            return;
        }

        writePrompt(
                "Deleted this task:",
                t.toString()
        );
    }

    public static void main(String[] args) {
        String[] greetings = new String[]{
                "Hello! I'm Clanker.",
                "What can I do you for today?",
        };

        String[] exiting = new String[]{
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
                case "delete":
                    handleDelete(cmd);
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