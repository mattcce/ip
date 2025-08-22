public class DeadlineTask extends Task {
    private String deadline;

    public DeadlineTask(String descriptor, String deadline) {
        super(descriptor);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), this.deadline);
    }

    @Override
    public String serialise() {
        return String.format("D|%s|%s", this.getDescription(), this.deadline);
    }
}
