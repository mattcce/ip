package clanker.task;

public class TaskStub extends Task {
    public TaskStub() {
        super("Stub");
    }

    @Override
    public String serialise() {
        return "Stub";
    }
}
