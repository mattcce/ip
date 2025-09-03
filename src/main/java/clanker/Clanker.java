package clanker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import clanker.task.DeadlineTask;
import clanker.task.EventTask;
import clanker.task.Task;
import clanker.task.TodoTask;
import javafx.util.Pair;
import parsers.CommandParser;
import serde.Serde;

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
            displayPrompt("Failed to initialise/read local storage: this session will not be saved!");
            System.out.println(e);
        }

        todoList = Serde.deserialise(data);

        String[] greetings = new String[]{
                "Hello! I'm Clanker.",
                "What can I do you for today?",
        };
        displayPrompt(greetings);
    }

    private static void handleTodoTask(CommandParser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());

        if (description.isEmpty()) {
            displayPrompt("Oops! The description of a task cannot be empty!");
            return;
        }

        TodoTask task = new TodoTask(description);

        todoList.addTask(task);

        displayPrompt("Added new task:",
                task.toString(),
                String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private static void handleDeadlineTask(CommandParser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());

        if (description.isEmpty()) {
            displayPrompt("Oops! The description of a task cannot be empty!");
            return;
        }

        String deadline = cmd.getOptionValue("by");

        if (deadline == null || deadline.isEmpty()) {
            displayPrompt("Oops! You must specify a deadline!");
            return;
        }

        DeadlineTask task = null;
        try {
            task = new DeadlineTask(description, deadline);
        } catch (DateTimeParseException e) {
            displayPrompt("Oops! You need to provide a date/time in the format: yyyy-mm-dd hhmm (hhmm is in 24hr time!)");
            return;
        }

        todoList.addTask(task);

        displayPrompt("Added new task:",
                task.toString(),
                String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private static void handleEventTask(CommandParser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());

        if (description.isEmpty()) {
            displayPrompt("Oops! The description of a task cannot be empty!");
            return;
        }

        String from = cmd.getOptionValue("from");
        String to = cmd.getOptionValue("to");

        if (from == null || to == null || from.isEmpty() || to.isEmpty()) {
            displayPrompt("Oops! You must specify a from and a to date/time for events!");
            return;
        }

        EventTask task = new EventTask(description, from, to);

        todoList.addTask(task);

        displayPrompt("Added new task:",
                task.toString(),
                String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private static void handleList() {
        List<String> tasks = todoList.listTasks();
        ArrayList<String> prompt = new ArrayList<>(todoList.size() + 1);

        prompt.add("These are all your tasks!");

        int count = 0;
        for (String s : tasks) {
            prompt.add(String.format("%s. %s", count + 1, s));
            count += 1;
        }

        displayPrompt(prompt.toArray(String[]::new));
    }

    private static void handleMark(CommandParser.Command cmd) {
        int taskIndex = Integer.parseInt(cmd.getParameter(0)) - 1;

        try {
            todoList.markAsDone(taskIndex);
        } catch (IndexOutOfBoundsException e) {
            displayPrompt("Could not find this task!");
            return;
        }

        displayPrompt(
                "Marked task as done:",
                todoList.getTask(taskIndex).toString()
        );
    }

    private static void handleUnmark(CommandParser.Command cmd) {
        int taskIndex = Integer.parseInt(cmd.getParameter(0)) - 1;

        try {
            todoList.markAsUndone(taskIndex);
        } catch (IndexOutOfBoundsException e) {
            displayPrompt("Could not find this task!");
            return;
        }

        displayPrompt(
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
            displayPrompt("Could not find this task!");
            return;
        }

        displayPrompt(
                "Deleted this task:",
                t.toString()
        );
    }

    private static void handleFind(CommandParser.Command cmd) {
        String searchString = String.join(" ", cmd.getAllParameters());
        List<Pair<Integer, String>> tasks = todoList.filterByDescription((s) -> s.contains(searchString));
        ArrayList<String> prompt = new ArrayList<>();

        prompt.add("I found these tasks matching your search term!");

        for (Pair<Integer, String> p : tasks) {
            prompt.add(String.format("%s. %s", p.getKey() + 1, p.getValue()));
        }

        displayPrompt(prompt.toArray(String[]::new));
    }

    private static void displayPrompt(String... lines) {
        printHorizontalLine();
        for (String s : lines) {
            System.out.println(s);
        }
        printHorizontalLine();
    }

    private static void printHorizontalLine() {
        System.out.println("---------------------------------------");
    }

    public static void handleSerialise() {
        String serialised = Serde.serialise(todoList);

        displayPrompt(serialised);
    }

    private static void handleExit() {
        String serialised = Serde.serialise(todoList);

        try {
            Files.writeString(STORE_PATH, serialised, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            displayPrompt("Failed to write to local storage: this session will not be saved!");
        }
    }

    /**
     * Application main loop.
     *
     * @param args Ignored.
     */
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
            case "find":
                handleFind(cmd);
                break;
            case "bye":
                handleExit();
                break repl;
            case "serialise":
                handleSerialise();
                break;
            default:
                displayPrompt("Unknown command!");
                break;
            }
        }

        displayPrompt(exiting);
    }
}