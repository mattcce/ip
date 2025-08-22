import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {
    private static final DateTimeFormatter DT_ENTRY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd kkmm");
    private static final DateTimeFormatter DT_DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private LocalDateTime deadline;

    public DeadlineTask(String descriptor, String deadline, boolean isDone) {
        this(descriptor, deadline);
        if (isDone) this.markAsDone();
    }

    public DeadlineTask(String descriptor, String deadline) {
        super(descriptor);
        this.deadline = LocalDateTime.parse(deadline, DT_ENTRY_FORMAT);
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), this.deadline.format(DT_DISPLAY_FORMAT));
    }

    @Override
    public String serialise() {
        return String.format("D|%s|%s|%s", this.isDone() ? "X" : "O", this.getDescription(), this.deadline.format(DT_ENTRY_FORMAT));
    }
}
