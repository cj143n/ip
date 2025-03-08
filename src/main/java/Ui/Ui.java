package Ui;

import FileManager.TaskFile;
import TaskList.TaskList;

public class Ui {
    private static final String DASH = "--------------------------------------------------";

    public Ui() {}

    public void welcomeMessage(TaskFile taskFile, TaskList listOfTasks) {
        System.out.println("Hello! I'm Doobert!\n");

        System.out.println("These are your previously recorded tasks:");
        loadTasks(taskFile, listOfTasks);

        System.out.println("\nWhat can I do for you today?");
        System.out.println(DASH);
    }

    public void loadTasks(TaskFile taskFile, TaskList listOfTasks) {
        taskFile.printTaskFileContents();
        taskFile.updateTaskArray(listOfTasks);
    }

    public void goodbyeMessage() {
        System.out.println("Bye! See you later alligator");
    }
}
