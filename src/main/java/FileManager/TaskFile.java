package FileManager;

import TaskList.TaskList;
import Tasks.Todo;
import Tasks.Event;
import Tasks.Deadline;

import java.io.*;
import java.util.Scanner;

/**
 * The TaskFile class manages the storing of task data from Doobert task manager into the filePath given
 */
public class TaskFile {
    private String filePath;

    /**
     * Instantiates instance of TaskFile
     * @param filePath File where tasks are saved in
     */
    public TaskFile(String filePath) {
        this.filePath = filePath;
    }

    private static final int INDEX_OF_TASK = 1;
    private static final int INDEX_OF_STATUS = 4;
    private static final int INDEX_OF_DESCRIPTION = 6;
    private static final int INDEX_AFTER_BY = 4;
    private static final int INDEX_AFTER_FROM = 6;
    private static final int INDEX_AFTER_TO = 4;

    /**
     * Creates a text file in the filePath if it does not exist
     */
    public void createTaskFile() {
        File taskFile = new File(filePath);
        File parentDirectory = taskFile.getParentFile();

        try {
            if (!parentDirectory.exists()) {
                if (parentDirectory.mkdirs()) {
                    System.out.println("Directory successfully created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }

            if (!taskFile.exists()) {
                if (taskFile.createNewFile()) {
                    System.out.println("Task file created");
                }
            }
        } catch (IOException e) {
            System.out.println("File creation failed: " + e.getMessage());
        }
    }

    /**
     * Adds on a task to the task file
     * @param task Task to be written into the file
     */
    public void appendTaskFile(String task) {
        File taskFile = new File(filePath);
        if (taskFile.exists()) {
            try (FileWriter fw = new FileWriter(filePath, true)) {
                fw.write(task + System.lineSeparator());
                fw.close();
            } catch (IOException e) {
                System.out.println("File write failed: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist!");
        }
    }

    /**
     * Edits corresponding line in file
     * @param lineNum Line index of task to be edited
     * @param task Task overwriting previous task
     */
    public void editLineInTaskFile(int lineNum, String task) {
        File taskFile = new File(filePath);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(taskFile)); //allows us to read file line by line
            StringBuffer inputBuffer = new StringBuffer(); //stores contents of file as we loop through and modify
            String line;

            int currentLineNum = 0;
            while ((line = reader.readLine()) != null) {
                if (currentLineNum == lineNum) {
                    line = task;
                }
                inputBuffer.append(line);
                inputBuffer.append("\n");
                currentLineNum++;
            }
            reader.close();

            FileOutputStream outputFile = new FileOutputStream(filePath);
            outputFile.write(inputBuffer.toString().getBytes()); //rewrites file
            outputFile.close();

            System.out.println("Task file successfully edited!");

        } catch (IOException e) {
            System.out.println("File read failed: " + e.getMessage());
        }
    }

    /**
     * Deletes a line in file
     * @param lineNum Line index of task to be deleted
     */
    public void deleteLineInTaskFile(int lineNum) {
        File taskFile = new File(filePath);
        File tempFile = new File("./data/temp.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(taskFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            int currentLineNum = 1;
            String line;

            while ((line = reader.readLine()) != null) {
                if (currentLineNum != lineNum) {
                    writer.write(line + System.lineSeparator());
                }
                currentLineNum++;
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("Line deletion in file failed: " + e.getMessage());
        }

        if (!taskFile.delete()) {
            System.out.println("Failed to delete original file.");
            return;
        }
        if (!tempFile.renameTo(taskFile)) {
            System.out.println("Failed to rename temp file.");
        }
    }

    /**
     * Prints out all contents stored in file
     */
    public void printTaskFileContents() {
        File taskFile = new File(filePath);
        try (Scanner s = new Scanner(taskFile)) {
            while (s.hasNext()) {
                System.out.println(s.nextLine());
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found, the file may not have been created yet!");
        }
    }

    /**
     * Updates the ArrayList listOfTasks with the data stored in file when Doobert task manager is rerun
     * @param listOfTasks ArrayList of tasks
     */
    public void updateTaskArray(TaskList listOfTasks) {
        File taskFile = new File(filePath);

        if (!taskFile.exists()) {
            createTaskFile();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(taskFile));
            String line;

            while ((line = reader.readLine()) != null) {
                char typeOfTask = line.charAt(INDEX_OF_TASK);
                char taskStatus = line.charAt(INDEX_OF_STATUS);

                String description;
                switch (typeOfTask) {
                    case 'T':
                        description = line.substring(INDEX_OF_DESCRIPTION).trim();
                        listOfTasks.add(new Todo(description));
                        break;
                case 'D':
                    int indexOfOpenBracket = line.indexOf('(');
                    description = line.substring(INDEX_OF_DESCRIPTION, indexOfOpenBracket).trim();

                    String deadline = line.substring(indexOfOpenBracket + INDEX_AFTER_BY, line.indexOf(")")).trim();

                    listOfTasks.add(new Deadline(description, deadline));
                    break;
                case 'E':
                    int indexOfOpenBracket2 = line.indexOf('(');
                    description = line.substring(INDEX_OF_DESCRIPTION, indexOfOpenBracket2).trim();

                    int indexOfTo = line.indexOf("to:");

                    String fromDate = line.substring(indexOfOpenBracket2 + INDEX_AFTER_FROM, indexOfTo).trim();
                    String toDate = line.substring(indexOfTo + INDEX_AFTER_TO - 1, line.indexOf(")") ).trim();

                    listOfTasks.add(new Event(description, fromDate, toDate));
                    break;
                }

                if (taskStatus == 'X') {
                    listOfTasks.get(listOfTasks.size() - 1).markDone();
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

}
