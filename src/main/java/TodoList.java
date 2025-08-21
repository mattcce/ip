import java.util.ArrayList;
import java.util.List;

public class TodoList {
    private final ArrayList<Task> tasks;

    public TodoList() {
        tasks = new ArrayList<>();
    }

    public Task addTask(String task) {
        Task t = new Task(task);
        tasks.add(t);
        return t;
    }

    public void markAsDone(int index) {
        tasks.get(index).markAsDone();
    }

    public void markAsUndone(int index) {
        tasks.get(index).markAsUndone();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public List<String> listTasks() {
        ArrayList<String> tmp = new ArrayList<>(tasks.size());

        for (Task t : tasks) {
            tmp.add(t.toString());
        }

        return tmp;
    }

    public int size() {
        return tasks.size();
    }
}
