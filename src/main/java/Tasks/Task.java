package Tasks;

/**
 * The Task class represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean done;

    /**
     * Instantiates a Task class
     * @param description Description of task
     */
    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    /**
     * Retrieves description of task
     * @return Description of task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Displays completion of task in a square bracket. Having the letter X means the task is marked done.
     * @return Completion of task
     */
    public String getStatusIcon() {
        return (done ? "X" : " ");
    }

    public void markDone() {
        this.done = true;
    }

    public void markUndone() {
        this.done = false;
    }

    public String toString() {
        return "[" + getStatusIcon() + "] " + getDescription();
    }
}
