import java.time.LocalDateTime;

public class DeadlineTask extends Task {
    private final LocalDateTime deadline;

    public DeadlineTask(String descriptor, String deadline, boolean isDone) {
        this(descriptor, deadline);
        if (isDone) this.markAsDone();
    }

    public DeadlineTask(String descriptor, String deadline) {
        super(descriptor);
        this.deadline = LocalDateTime.parse(deadline, Formatters.DT_ENTRY_FORMAT);
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), this.deadline.format(Formatters.DT_DISPLAY_FORMAT));
    }

    @Override
    public String serialise() {
        return String.format("D|%s|%s|%s", this.isDone() ? "X" : "O", this.getDescription(), this.deadline.format(Formatters.DT_ENTRY_FORMAT));
    }
}
