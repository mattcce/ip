package clanker;

import java.util.ArrayList;
import java.util.List;

import clanker.task.Task;
import serde.Serialisable;

/**
 * To-do list class that handles a list of Tasks.
 */
public class TodoList implements Serialisable {
    private final ArrayList<Task> tasks;

    /**
     * Constructor that initialises an empty TodoList.
     */
    public TodoList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param t Task to be added.
     */
    public void addTask(Task t) {
        this.tasks.add(t);
    }

    /**
     * Marks a task on the list as done.
     *
     * @param index Index of the task (1-indexed).
     */
    public void markAsDone(int index) {
        this.tasks.get(index).markAsDone();
    }

    /**
     * Marks a task on the list as not done.
     *
     * @param index Index of the task (1-indexed).
     */
    public void markAsUndone(int index) {
        this.tasks.get(index).markAsUndone();
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Removes a task from the list and shifts all tasks further down up.
     *
     * @param index Index of the task (1-indexed).
     * @return The deleted task.
     */
    public Task deleteTask(int index) {
        return this.tasks.remove(index);
    }

    /**
     * List all tasks with their string representations, in order.
     *
     * @return List of string representations of all tasks in the list, in order.
     */
    public List<String> listTasks() {
        ArrayList<String> tmp = new ArrayList<>(this.tasks.size());

        for (Task t : tasks) {
            tmp.add(t.toString());
        }

        return tmp;
    }

    /**
     * Returns the size of the list of tasks.
     *
     * @return Size of list of tasks.
     */
    public int size() {
        return this.tasks.size();
    }

    @Override
    public String serialise() {
        ArrayList<String> strings = new ArrayList<>();
        for (Task t : this.tasks) {
            strings.add(t.serialise());
        }

        return String.join("\n", strings);
    }
}
