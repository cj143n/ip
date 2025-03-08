package Parser;

import FileManager.TaskFile;
import TaskList.TaskList;


public class Parser {

    private static final String DASH = "--------------------------------------------------";

    public void readInput(String input, TaskList taskList, TaskFile taskFile) {
         try {
             if (input.equals("list")) { //lists out todo list
                 taskList.listTasks();
             } else if (input.matches("^mark \\d+$")) { //marks xx as done
                 taskList.markTask(taskFile, input);
             } else if (input.matches("^unmark \\d+$")) { //marks xx as undone
                 taskList.unmarkTask(taskFile, input);
             } else if (input.matches("^delete \\d+$")) {
                 taskList.deleteTask(taskFile, input);
             } else if (input.startsWith("find ")) {
                 taskList.find(taskFile, input);
             } else {
                 taskList.addTask(taskFile, input);
             }
         } catch (Exception e) {
             System.out.println("Unexpected error: " + e.getMessage());
             System.out.println(DASH);
         }
    }
}
