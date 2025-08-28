package clanker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import clanker.task.TaskStub;

public class TodoListTest {
    @Test
    public void size_noTasks_correctlyReturns() {
        TodoList tdl = new TodoList();

        assertEquals(0, tdl.size());
    }

    @Test
    public void size_someTasks_correctlyReturns() {
        TodoList tdl = new TodoList();

        tdl.addTask(new TaskStub());
        tdl.addTask(new TaskStub());
        tdl.addTask(new TaskStub());

        assertEquals(3, tdl.size());
    }

    @Test
    public void serialisation_someTasks_correctlyReturns() {
        TodoList tdl = new TodoList();

        tdl.addTask(new TaskStub());
        tdl.addTask(new TaskStub());
        tdl.addTask(new TaskStub());

        assertEquals("Stub\nStub\nStub", tdl.serialise());
    }
}
