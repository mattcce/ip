package clanker.task;

import java.time.LocalDateTime;

import fmt.DateTimeParser;

/**
 * Event task class that allows setting start and end date for an event.
 */
public class EventTask extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Fully specifying constructor, meant to be used by the deserialiser only.
     *
     * @param description Task description.
     * @param start       Task start datetime in string representation (serialised).
     * @param end         Task end datetime in string representation (serialised).
     * @param isDone      Flag for task completion.
     */
    public EventTask(String description, String start, String end, boolean isDone) {
        this(description, start, end);
        if (isDone) this.markAsDone();
    }

    /**
     * Basic constructor.
     *
     * @param description Task description.
     * @param start       Task start datetime in string representation (serialised).
     * @param end         Task end datetime in string representation (serialised).
     */
    public EventTask(String description, String start, String end) {
        super(description);
        this.start = DateTimeParser.parseAsEntry(start);
        this.end = DateTimeParser.parseAsEntry(end);
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), DateTimeParser.display(this.start), DateTimeParser.display(this.end));
    }

    @Override
    public String serialise() {
        return String.format("E|%s|%s|%s|%s", this.isDone() ? "X" : "O", this.getDescription(), DateTimeParser.unparse(this.start), DateTimeParser.unparse(this.end));
    }
}
