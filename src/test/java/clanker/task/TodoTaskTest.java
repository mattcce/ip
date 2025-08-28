package clanker.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTaskTest {
    @Test
    public void serialisation_incompleteTask_serialisesCorrectly() {
        String description = "test description";

        TodoTask task = new TodoTask(description);

        String expected = "T|O|test description";
        assertEquals(expected, task.serialise());
    }

    @Test
    public void serialisation_completedTask_serialisesCorrectly() {
        String description = "test description";

        TodoTask task = new TodoTask(description);

        task.markAsDone();

        String expected = "T|X|test description";
        assertEquals(expected, task.serialise());
    }
}
