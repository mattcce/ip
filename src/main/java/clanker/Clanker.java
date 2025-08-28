package clanker;

import clanker.task.DeadlineTask;
import clanker.task.EventTask;
import clanker.task.Task;
import clanker.task.TodoTask;
import fmt.CommandParser;
import serde.Serde;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Clanker {
    private static final Path STORE_PATH = Path.of("./task_store.txt");
    private static TodoList todoList = new TodoList();

    private static void handleStartup() {
        String data = "";

        try {
            if (!Files.exists(STORE_PATH)) {
                Files.createFile(STORE_PATH);
            }

            data = Files.readString(STORE_PATH);
        } catch (IOException e) {
            writePrompt("Failed to initialise/read local storage: this session will not be saved!");
            System.out.println(e);
        }

        todoList = Serde.deserialise(data);

        String[] greetings = new String[]{
            "Hello! I'm Clanker.",
            "What can I do you for today?",
        };
        writePrompt(greetings);
    }

    private static void writePrompt(String... lines) {
        printHorizontalLine();
        for (String s : lines) {
            System.out.println(s);
        }
        printHorizontalLine();
    }

    private static void printHorizontalLine() {
        System.out.println("---------------------------------------");
    }

    private static void handleTodoTask(CommandParser.Command cmd) {
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

    private static void handleDeadlineTask(CommandParser.Command cmd) {
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

        DeadlineTask t = null;
        try {
            t = new DeadlineTask(description, deadline);
        } catch (DateTimeParseException e) {
            writePrompt("Oops! You need to provide a date/time in the format: yyyy-mm-dd hhmm (hhmm is in 24hr time!)");
            return;
        }

        todoList.addTask(t);

        writePrompt("Added new task:",
            t.toString(),
            String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private static void handleEventTask(CommandParser.Command cmd) {
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

    private static void handleMark(CommandParser.Command cmd) {
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

    private static void handleUnmark(CommandParser.Command cmd) {
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

    private static void handleDelete(CommandParser.Command cmd) {
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

    public static void handleSerialise() {
        String serialised = Serde.serialise(todoList);

        writePrompt(serialised);
    }

    public static void handleExit() {
        String serialised = Serde.serialise(todoList);

        try {
            Files.writeString(STORE_PATH, serialised, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            writePrompt("Failed to write to local storage: this session will not be saved!");
        }
    }

    public static void main(String[] args) {
        String[] exiting = new String[]{
            "Bye. Hope to see you again soon!"
        };

        handleStartup();

        // REPL
        Scanner scanner = new Scanner(System.in);

        repl:
        while (true) {
            CommandParser.Command cmd = CommandParser.parse(scanner.nextLine());

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
                    handleExit();
                    break repl;
                case "serialise":
                    handleSerialise();
                    break;
                default:
                    writePrompt("Unknown command!");
                    break;
            }
        }

        writePrompt(exiting);
    }


}