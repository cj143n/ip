package FileManager;

import Tasks.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskFile {
    private static final String FILE_PATH = "./data/Doobert.txt";
    private static final int INDEX_OF_TASK = 1;
    private static final int INDEX_OF_STATUS = 4;
    private static final int INDEX_OF_DESCRIPTION = 6;
    private static final int INDEX_AFTER_BY = 4;
    private static final int INDEX_AFTER_FROM = 6;
    private static final int INDEX_AFTER_TO = 4;

    public static void createTaskFile() {
        File taskFile = new File(FILE_PATH);
        File parentDirectory = taskFile.getParentFile(); //get data/ directory

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

    public static void appendTaskFile(String task) {
        File taskFile = new File(FILE_PATH);
        if (taskFile.exists()) {
            try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
                fw.write(task + System.lineSeparator());
            } catch (IOException e) {
                System.out.println("File write failed: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist!");
        }
    }

    public static void editLineInTaskFile(int lineNum, String task) {
        File taskFile = new File(FILE_PATH);
        List<String> lines = new ArrayList<>();

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

            FileOutputStream outputFile = new FileOutputStream(FILE_PATH);
            outputFile.write(inputBuffer.toString().getBytes()); //rewrites file
            outputFile.close();

            System.out.println("Task file successfully edited!");

        } catch (IOException e) {
            System.out.println("File read failed: " + e.getMessage());
        }
    }

    public static void printTaskFileContents() {
        File taskFile = new File(FILE_PATH);
        try (Scanner s = new Scanner(taskFile)) {
            while (s.hasNext()) {
                System.out.println(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, the file may not have been created yet! Add a task to create it!");
        }
    }

    public static int updateTaskArray(Task[] listOfTasks) {
        File taskFile = new File(FILE_PATH);

        if (!taskFile.exists()) {
            createTaskFile();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(taskFile));
            String line;

            int currentLineNum = 0;
            while ((line = reader.readLine()) != null) {
                char typeOfTask = line.charAt(INDEX_OF_TASK);
                char taskStatus = line.charAt(INDEX_OF_STATUS);

                String description;
                switch (typeOfTask) {
                    case 'T':
                        description = line.substring(INDEX_OF_DESCRIPTION).trim();
                        listOfTasks[currentLineNum] = new Todo(description);
                        break;
                case 'D':
                    int indexOfOpenBracket = line.indexOf('(');
                    description = line.substring(INDEX_OF_DESCRIPTION, indexOfOpenBracket).trim();

                    String deadline = line.substring(indexOfOpenBracket + INDEX_AFTER_BY, line.indexOf(")")).trim();

                    listOfTasks[currentLineNum] = new Deadline(description, deadline);
                    break;
                case 'E':
                    int indexOfOpenBracket2 = line.indexOf('(');
                    description = line.substring(INDEX_OF_DESCRIPTION, indexOfOpenBracket2).trim();

                    int indexOfTo = line.indexOf("to:");

                    String fromDate = line.substring(indexOfOpenBracket2 + INDEX_AFTER_FROM, indexOfTo).trim();
                    String toDate = line.substring(indexOfTo + INDEX_AFTER_TO - 1, line.indexOf(")") ).trim();

                    listOfTasks[currentLineNum] = new Event(description, fromDate, toDate);
                    break;
                }

                if (taskStatus == 'X') {
                    listOfTasks[currentLineNum].markDone();
                }
                currentLineNum++;
            }
            return currentLineNum + 1;
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
            return 0;
        }
    }

}
