import serde.Serialisable;

public abstract class Task implements Serialisable {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        String done = this.isDone ? "X" : " ";
        return String.format("[%s] %s", done, this.description);
    }
}
