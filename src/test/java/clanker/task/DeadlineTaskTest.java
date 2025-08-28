package clanker.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DeadlineTaskTest {
    @Test
    public void serialisation_incompleteTask_serialisesCorrectly() {
        String description = "test description";
        String deadline = "2025-10-10 1000";

        DeadlineTask task = new DeadlineTask(description, deadline);

        String expected = "D|O|test description|2025-10-10 1000";
        assertEquals(expected, task.serialise());
    }

    @Test
    public void serialisation_completedTask_serialisesCorrectly() {
        String description = "test description";
        String deadline = "2025-10-10 1000";

        DeadlineTask task = new DeadlineTask(description, deadline);

        task.markAsDone();

        String expected = "D|X|test description|2025-10-10 1000";
        assertEquals(expected, task.serialise());
    }
}
