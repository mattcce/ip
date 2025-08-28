package serde;

import clanker.TodoList;
import clanker.task.DeadlineTask;
import clanker.task.EventTask;
import clanker.task.Task;
import clanker.task.TodoTask;

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
                    tasks.add(new TodoTask(taskString[2], taskString[1].equals("X")));
                    break;
                case "D":
                    tasks.add(new DeadlineTask(taskString[2], taskString[3], taskString[1].equals("X")));
                    break;
                case "E":
                    tasks.add(new EventTask(taskString[2], taskString[3], taskString[4], taskString[1].equals("X")));
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
