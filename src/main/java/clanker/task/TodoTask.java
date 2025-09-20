package clanker.task;

/**
 * Basic task class.
 */
public class TodoTask extends Task {
    /**
     * Constructs a new TodoTask with all details specified, meant to be used by the deserialiser only.
     *
     * @param description Task description.
     * @param isDone      Flag for task completion.
     */
    public TodoTask(String description, boolean isDone) {
        this(description);
        if (isDone) {
            this.markAsDone();
        }
    }

    /**
     * Constructs a new TodoTask.
     *
     * @param description Task description.
     */
    public TodoTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return String.format("[T] %s", super.toString());
    }

    @Override
    public String serialise() {
        return String.format("T|%s|%s", this.isDone() ? "X" : "O", this.getDescription());
    }
}
