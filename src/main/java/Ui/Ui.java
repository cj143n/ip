package Ui;

import FileManager.TaskFile;
import TaskList.TaskList;

/**
 * The Ui class handles the user interface interactions in the Doobert task manager.
 */
public class Ui {
    private static final String DASH = "--------------------------------------------------";

    public Ui() {}

    /**
     * Prints out welcome message to user and updates the listOfTasks with data loaded from taskFile
     * @param taskFile File where tasks are saved in
     * @param listOfTasks ArrayList of tasks
     */
    public void welcomeMessage(TaskFile taskFile, TaskList listOfTasks) {
        System.out.println("Hello! I'm Doobert!\n");

        System.out.println("These are your previously recorded tasks:");
        loadTasks(taskFile, listOfTasks);

        System.out.println("\nWhat can I do for you today?");
        System.out.println(DASH);
    }

    /**
     * Updates listOfTasks with data loaded from taskFile
     * @param taskFile File where tasks are saved in
     * @param listOfTasks ArrayList of tasks
     */
    public void loadTasks(TaskFile taskFile, TaskList listOfTasks) {
        taskFile.printTaskFileContents();
        taskFile.updateTaskArray(listOfTasks);
    }

    /**
     * Prints out goodbye message
     */
    public void goodbyeMessage() {
        System.out.println("Bye! See you later alligator");
    }
}
