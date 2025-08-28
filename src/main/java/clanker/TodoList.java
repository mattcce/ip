package clanker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import clanker.task.Task;
import javafx.util.Pair;
import serde.Serialisable;

public class TodoList implements Serialisable {
    private final ArrayList<Task> tasks;

    public TodoList() {
        this.tasks = new ArrayList<>();
    }

    public Task addTask(Task t) {
        this.tasks.add(t);
        return t;
    }

    public void markAsDone(int index) {
        this.tasks.get(index).markAsDone();
    }

    public void markAsUndone(int index) {
        this.tasks.get(index).markAsUndone();
    }

    public Task deleteTask(int index) {
        return this.tasks.remove(index);
    }

    public List<String> listTasks() {
        ArrayList<String> tmp = new ArrayList<>(this.tasks.size());

        for (Task t : tasks) {
            tmp.add(t.toString());
        }

        return tmp;
    }

    public List<Pair<Integer, String>> filterByDescription(Predicate<String> pred) {
        ArrayList<Pair<Integer, String>> tmp = new ArrayList<>();

        for (int i = 0; i < this.size(); i++) {
            Task t = this.getTask(i);

            if (pred.test(t.getDescription())) {
                tmp.add(new Pair<>(i, t.toString()));
            }
        }

        return tmp;
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

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
