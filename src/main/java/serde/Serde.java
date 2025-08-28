package serde;

import java.util.ArrayList;

import clanker.TodoList;
import clanker.task.DeadlineTask;
import clanker.task.EventTask;
import clanker.task.Task;
import clanker.task.TodoTask;

/**
 * Serialiser-deserialiser for Clanker. This class provides functionality for serialising and deserialising the internal
 * state of Clanker, to save states between sessions.
 */
public class Serde {
    /**
     * Returns a string representation of the object to be serialised.
     *
     * @param obj Object to be serialised.
     * @return String representation or serialisation of given object.
     */
    public static String serialise(Serialisable obj) {
        return obj.serialise();
    }

    /**
     * Reconstructs a TodoList from a given serialisation/string representation.
     *
     * @param s String representation/serialisation of a TodoList.
     * @return TodoList obtained by deserialising the input.
     */
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
