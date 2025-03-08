package Tasks;

/**
 * The Event class represents a task that happens from a certain time to another.
 * It extends the Task class
 */

public class Event extends Task {
    protected String start;
    protected String end;

    /**
     * Instantiates an Event class
     * @param description Description of event
     * @param start Start time of event
     * @param end End time of event
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Displays the event data in the format of a string
     * @return Data of event
     */
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
