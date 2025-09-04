package clanker;

import static parsers.CommandParser.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import clanker.task.DeadlineTask;
import clanker.task.EventTask;
import clanker.task.Task;
import clanker.task.TodoTask;
import javafx.util.Pair;
import parsers.CommandParser;
import serde.Serde;
import ui.utils.Writer;

/**
 * The clanker that manages your tasks.
 */
public class Clanker {
    private static final Path STORE_PATH = Path.of("./task_store.txt");
    private final Writer writer;
    private TodoList todoList = new TodoList();

    /**
     * Creates a new clanker.
     *
     * @param writer Callback to pipe Clanker's output to.
     */
    private Clanker(Writer writer) {
        this.writer = writer;
    }

    /**
     * Initialises a new clanker.
     *
     * @param writer Callback to pipe Clanker's output to.
     * @return The created Clanker.
     */
    public static Clanker initialise(Writer writer) {
        Clanker clanker = new Clanker(writer);

        clanker.handleStartup();

        return clanker;
    }

    private void handleStartup() {
        String data = "";

        try {
            if (!Files.exists(STORE_PATH)) {
                Files.createFile(STORE_PATH);
            }

            data = Files.readString(STORE_PATH);
        } catch (IOException e) {
            this.displayPrompt("Failed to initialise/read local storage: this session will not be saved!");
            System.out.println(e);
        }

        todoList = Serde.deserialise(data);

        String[] greetings = new String[]{
            "Hello! I'm Clanker.",
            "What can I do you for today?",
        };
        this.displayPrompt(greetings);
    }

    private void displayPrompt(String... lines) {
        this.writer.write(formatLines(lines));
    }

    private static String formatLines(String... lines) {
        StringBuilder sb = new StringBuilder();

        for (String s : lines) {
            sb.append(s);
            sb.append("\n");
        }

        return sb.toString().trim();
    }

    /**
     * Handles user command.
     *
     * @param input User command, given as one string.
     */
    public void handleCommand(String input) {
        Command cmd = CommandParser.parse(input);

        switch (cmd.getImperative()) {
        case "todo":
            this.handleTodoTask(cmd);
            break;
        case "deadline":
            this.handleDeadlineTask(cmd);
            break;
        case "event":
            this.handleEventTask(cmd);
            break;
        case "list":
            this.handleList();
            break;
        case "mark":
            this.handleMark(cmd);
            break;
        case "unmark":
            this.handleUnmark(cmd);
            break;
        case "delete":
            this.handleDelete(cmd);
            break;
        case "find":
            this.handleFind(cmd);
            break;
        case "bye":
            this.handleExit();
            break;
        case "serialise":
            this.handleSerialise();
            break;
        default:
            this.displayPrompt("Unknown command!");
            break;
        }
    }

    private void handleTodoTask(CommandParser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());

        if (description.isEmpty()) {
            this.displayPrompt("Oops! The description of a task cannot be empty!");
            return;
        }

        TodoTask task = new TodoTask(description);

        todoList.addTask(task);

        this.displayPrompt("Added new task:",
            task.toString(),
            String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private void handleDeadlineTask(CommandParser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());

        if (description.isEmpty()) {
            this.displayPrompt("Oops! The description of a task cannot be empty!");
            return;
        }

        String deadline = cmd.getOptionValue("by");

        if (deadline == null || deadline.isEmpty()) {
            this.displayPrompt("Oops! You must specify a deadline!");
            return;
        }

        DeadlineTask task = null;
        try {
            task = new DeadlineTask(description, deadline);
        } catch (DateTimeParseException e) {
            this.displayPrompt(
                "Oops! You need to provide a date/time in the format: yyyy-mm-dd hhmm (hhmm is in 24hr time!)");
            return;
        }

        todoList.addTask(task);

        this.displayPrompt("Added new task:",
            task.toString(),
            String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private void handleEventTask(CommandParser.Command cmd) {
        String description = String.join(" ", cmd.getAllParameters());

        if (description.isEmpty()) {
            this.displayPrompt("Oops! The description of a task cannot be empty!");
            return;
        }

        String from = cmd.getOptionValue("from");
        String to = cmd.getOptionValue("to");

        if (from == null || to == null || from.isEmpty() || to.isEmpty()) {
            this.displayPrompt("Oops! You must specify a from and a to date/time for events!");
            return;
        }

        EventTask task = new EventTask(description, from, to);

        todoList.addTask(task);

        this.displayPrompt("Added new task:",
            task.toString(),
            String.format("There are now %d tasks in your list.", todoList.size()));
    }

    private void handleList() {
        List<String> tasks = todoList.listTasks();
        ArrayList<String> prompt = new ArrayList<>(todoList.size() + 1);

        prompt.add("These are all your tasks!");

        int count = 0;
        for (String s : tasks) {
            prompt.add(String.format("%s. %s", count + 1, s));
            count += 1;
        }

        this.displayPrompt(prompt.toArray(String[]::new));
    }

    private void handleMark(CommandParser.Command cmd) {
        int taskIndex = Integer.parseInt(cmd.getParameter(0)) - 1;

        try {
            todoList.markAsDone(taskIndex);
        } catch (IndexOutOfBoundsException e) {
            this.displayPrompt("Could not find this task!");
            return;
        }

        this.displayPrompt(
            "Marked task as done:",
            todoList.getTask(taskIndex).toString()
        );
    }

    private void handleUnmark(CommandParser.Command cmd) {
        int taskIndex = Integer.parseInt(cmd.getParameter(0)) - 1;

        try {
            todoList.markAsUndone(taskIndex);
        } catch (IndexOutOfBoundsException e) {
            this.displayPrompt("Could not find this task!");
            return;
        }

        this.displayPrompt(
            "Marked task as not done:",
            todoList.getTask(taskIndex).toString()
        );
    }

    private void handleDelete(CommandParser.Command cmd) {
        int taskIndex = Integer.parseInt(cmd.getParameter(0)) - 1;

        Task t;
        try {
            t = todoList.deleteTask(taskIndex);
        } catch (IndexOutOfBoundsException e) {
            this.displayPrompt("Could not find this task!");
            return;
        }

        this.displayPrompt(
            "Deleted this task:",
            t.toString()
        );
    }

    private void handleFind(CommandParser.Command cmd) {
        String searchString = String.join(" ", cmd.getAllParameters());
        List<Pair<Integer, String>> tasks = todoList.filterByDescription((s) -> s.contains(searchString));
        ArrayList<String> prompt = new ArrayList<>();

        prompt.add("I found these tasks matching your search term!");

        for (Pair<Integer, String> p : tasks) {
            prompt.add(String.format("%s. %s", p.getKey() + 1, p.getValue()));
        }

        this.displayPrompt(prompt.toArray(String[]::new));
    }

    private void handleSerialise() {
        String serialised = Serde.serialise(todoList);

        this.displayPrompt(serialised);
    }

    private void handleExit() {
        String serialised = Serde.serialise(todoList);

        try {
            Files.writeString(STORE_PATH, serialised, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            this.displayPrompt("Failed to write to local storage: this session will not be saved!");
        }

        String[] exiting = new String[]{
            "Bye. Hope to see you again soon!"
        };

        this.displayPrompt(exiting);
    }
}
