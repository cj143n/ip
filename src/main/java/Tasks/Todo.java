package Tasks;

public class Todo extends Task {

    /**
     * Instantiates a Todo class
     * @param description Description of todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Displays the todo data in the format of a string
     * @return Data of todo task
     */
    public String toString() {
        return "[T]" + super.toString();
    }
}
