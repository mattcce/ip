import serde.Serialisable;

import java.util.ArrayList;
import java.util.List;

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

    public Task getTask(int index) {
        return this.tasks.get(index);
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
