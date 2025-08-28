package clanker.task;

import java.time.LocalDateTime;

import fmt.DateTimeParser;

public class DeadlineTask extends Task {
    private final LocalDateTime deadline;

    public DeadlineTask(String descriptor, String deadline, boolean isDone) {
        this(descriptor, deadline);
        if (isDone) this.markAsDone();
    }

    public DeadlineTask(String descriptor, String deadline) {
        super(descriptor);
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
