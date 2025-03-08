package Tasks;

public class Deadline extends Task {
    protected String by;

    /**
     * Instantiates a Deadline class
     * @param description Description of deadline
     * @param by Due date of deadline
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Displays the deadline data in the format of a string
     * @return Data of deadline
     */
    @Override
    public String toString(){
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
