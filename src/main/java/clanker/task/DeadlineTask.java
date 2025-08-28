package clanker.task;

import java.time.LocalDateTime;

import fmt.DateTimeParser;

/**
 * Deadline task class that allows setting deadlines for a particular task.
 */
public class DeadlineTask extends Task {
    private final LocalDateTime deadline;

    /**
     * Fully specifying constructor, meant to be used by the deserialiser only.
     *
     * @param description Task description.
     * @param deadline    Task deadline in string representation (serialised).
     * @param isDone      Flag for task completion.
     */
    public DeadlineTask(String description, String deadline, boolean isDone) {
        this(description, deadline);
        if (isDone) this.markAsDone();
    }

    /**
     * Basic constructor.
     *
     * @param description Task description.
     * @param deadline    Task deadline in string representation (serialised).
     */
    public DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = DateTimeParser.parseAsEntry(deadline);
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), DateTimeParser.display(this.deadline));
    }

    @Override
    public String serialise() {
        return String.format("D|%s|%s|%s", this.isDone() ? "X" : "O", this.getDescription(), DateTimeParser.unparse(this.deadline));
    }
}
