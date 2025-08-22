import java.util.ArrayList;

// Ser(ialise)-de(serialise)r
public class Serde {
    public static String serialise(Serialisable obj) {
        return obj.serialise();
    }

    public static TodoList deserialise(String s) {
        String[] taskStrings = s.split("\n");
        ArrayList<Task> tasks = new ArrayList<>();
        for (String ts : taskStrings) {
            String[] taskString = ts.split("\\|");

            switch (taskString[0]) {
                case "T":
                    tasks.add(new TodoTask(taskString[1]));
                    break;
                case "D":
                    tasks.add(new DeadlineTask(taskString[1], taskString[2]));
                    break;
                case "E":
                    tasks.add(new EventTask(taskString[1], taskString[2], taskString[3]));
                    break;
            }
        }

        TodoList todoList = new TodoList();
        for (Task t : tasks) {
            todoList.addTask(t);
        }

        return todoList;
    }
}
