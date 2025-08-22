public class EventTask extends Task {
    private String start;
    private String end;

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
        return String.format("E|%s|%s|%s", this.getDescription(), this.start, this.end);
    }
}
