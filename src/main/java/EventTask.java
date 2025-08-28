import java.time.LocalDateTime;

public class EventTask extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public EventTask(String description, String start, String end, boolean isDone) {
        this(description, start, end);
        if (isDone) this.markAsDone();
    }

    public EventTask(String description, String start, String end) {
        super(description);
        this.start = LocalDateTime.parse(start, Formatters.DT_ENTRY_FORMAT);
        this.end = LocalDateTime.parse(end, Formatters.DT_ENTRY_FORMAT);
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), this.start.format(Formatters.DT_DISPLAY_FORMAT), this.end.format(Formatters.DT_DISPLAY_FORMAT));
    }

    @Override
    public String serialise() {
        return String.format("E|%s|%s|%s|%s", this.isDone() ? "X" : "O", this.getDescription(), this.start.format(Formatters.DT_ENTRY_FORMAT), this.end.format(Formatters.DT_ENTRY_FORMAT));
    }
}
