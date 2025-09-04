package clanker.task;

/**
 * Stub class for testing Tasks.
 */
public class TaskStub extends Task {
    public TaskStub() {
        super("Stub");
    }

    @Override
    public String serialise() {
        return "Stub";
    }
}
