import java.util.ArrayList;
import java.util.List;

public class TodoList {
    private final ArrayList<String> tasks;

    public TodoList() {
        tasks = new ArrayList<>();
    }

    public void addTask(String task) {
        tasks.add(task);
    }

    public List<String> listTasks() {
        // copy list to prevent external mutation
        ArrayList<String> tmp = new ArrayList<>(tasks.size());
        tmp.addAll(tasks);
        return tmp;
    }

    public int size() {
        return tasks.size();
    }
}
