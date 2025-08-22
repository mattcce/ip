public class TodoTask extends Task {
    public TodoTask(String description, boolean isDone) {
        this(description);
        if (isDone) this.markAsDone();
    }

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
