public class EventTask extends Task {
    private final String start;
    private final String end;

    public EventTask(String description, String start, String end, boolean isDone) {
        this(description, start, end);
        if (isDone) this.markAsDone();
    }

    public EventTask(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return this.start;
    }

    public String getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), this.start, this.end);
    }

    @Override
    public String serialise() {
        return String.format("E|%s|%s|%s|%s", this.isDone() ? "X" : "O", this.getDescription(), this.start, this.end);
    }
}
