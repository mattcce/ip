package clanker.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EventTaskTest {
    @Test
    public void serialisation_incompleteTask_serialisesCorrectly() {
        String description = "test description";
        String from = "2025-10-10 1000";
        String to = "2025-10-10 1300";

        EventTask task = new EventTask(description, from, to);

        String expected = "E|O|test description|2025-10-10 1000|2025-10-10 1300";
        assertEquals(expected, task.serialise());
    }

    @Test
    public void serialisation_completedTask_serialisesCorrectly() {
        String description = "test description";
        String from = "2025-10-10 1000";
        String to = "2025-10-10 1300";

        EventTask task = new EventTask(description, from, to);

        task.markAsDone();

        String expected = "E|X|test description|2025-10-10 1000|2025-10-10 1300";
        assertEquals(expected, task.serialise());
    }
}
