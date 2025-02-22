package Doobert;

import java.util.Scanner;
import Tasks.*;
import FileManager.TaskFile;

import java.util.ArrayList;

class Doobert {
    private static final int MAX_NO_OF_TASKS = 100;
    private static final int INDEX_AFTER_MARK = 5;
    private static final int INDEX_AFTER_UNMARK = 7;
    private static final int INDEX_AFTER_TODO = 4;
    private static final int INDEX_AFTER_DEADLINE = 8;
    private static final int INDEX_AFTER_EVENT = 5;
    private static final int INDEX_AFTER_BY = 4;
    private static final int INDEX_AFTER_FROM = 5;
    private static final int INDEX_AFTER_TO = 4;
    private static final int INDEX_AFTER_DELETE = 7;
    private static final String DASH = "--------------------------------------------------";

    public static void listTasks(ArrayList<Tasks.Task> listOfTasks) {
        if (listOfTasks.isEmpty()) {
            System.out.println("No tasks yet!");
            System.out.println(DASH);
            return;
        }
        System.out.println("Here is your list of tasks:");
        for (int i = 0; i < listOfTasks.size(); i++) {
            System.out.println((i + 1) + "." + listOfTasks.get(i).toString());
        }
        System.out.println(DASH);
    }

    public static void markTask(ArrayList<Tasks.Task> listOfTasks, String line) {
        try {
            int taskNum = Integer.parseInt(line.substring(INDEX_AFTER_MARK).trim());
            if (taskNum < 1 || taskNum > listOfTasks.size()) {
                throw new IndexOutOfBoundsException("Invalid task number");
            }
            listOfTasks.get(taskNum - 1).markDone();
            System.out.println("Task successfully marked!");

            System.out.println(listOfTasks.get(taskNum - 1).toString());
            TaskFile.editLineInTaskFile(taskNum - 1, listOfTasks.get(taskNum - 1).toString());

            System.out.println(DASH);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task format. Use: mark <number>");
            System.out.println(DASH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }

    public static void unmarkTask(ArrayList<Tasks.Task> listOfTasks, String line) {
        try {
            int taskNum = Integer.parseInt(line.substring(INDEX_AFTER_UNMARK).trim());
            if (taskNum < 1 || taskNum > listOfTasks.size()) {
                throw new IndexOutOfBoundsException("Invalid task number");
            }
            listOfTasks.get(taskNum - 1).markUndone();
            System.out.println("Task successfully unmarked!");

            System.out.println(listOfTasks.get(taskNum - 1).toString());
            TaskFile.editLineInTaskFile(taskNum - 1, listOfTasks.get(taskNum - 1).toString());

            System.out.println(DASH);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task format. Use: unmark <number>");
            System.out.println(DASH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }

    public static void addTask(ArrayList<Tasks.Task> listOfTasks, String line) { //differentiate between event, deadline, todo
        try {
            String firstWord = line.split(" ")[0];
            switch (firstWord) {
            case "todo":
                String task = line.substring(INDEX_AFTER_TODO).trim();
                if (task.isEmpty()) {
                    throw new IndexOutOfBoundsException("Todo is empty! Please add an appropriate name :P");
                }

                listOfTasks.add(new Todo(line.substring(INDEX_AFTER_TODO).trim()));
                TaskFile.appendTaskFile(listOfTasks.get(listOfTasks.size() - 1).toString());
                break;

            case "deadline":
                int slashIndex = line.indexOf('/');
                if (slashIndex == -1 || !(line.substring(slashIndex + 1).startsWith("by"))) {
                    throw new IllegalArgumentException("Invalid deadline format. Use: deadline task /by date");
                }

                String desc = line.substring(INDEX_AFTER_DEADLINE, slashIndex).trim();
                if (desc.isEmpty()) {
                    throw new IllegalArgumentException("Deadline is empty! Please add an appropriate name :P");
                }

                String date = (slashIndex + INDEX_AFTER_BY < line.length())
                        ? line.substring(slashIndex + INDEX_AFTER_BY).trim()
                        : "";
                if (date.isEmpty()) {
                    throw new IllegalArgumentException("No date entered, please enter a date!");
                }

                listOfTasks.add(new Deadline(desc, date));
                TaskFile.appendTaskFile(listOfTasks.get(listOfTasks.size() - 1).toString());
                break;

            case "event":
                int firstSlashIndex = line.indexOf('/');
                int secondSlashIndex = line.indexOf('/', firstSlashIndex + INDEX_AFTER_FROM);
                boolean isFromFound = line.substring(firstSlashIndex + 1).startsWith("from");
                boolean isToFound = line.substring(secondSlashIndex + 1).trim().startsWith("to");
                if (firstSlashIndex == -1 || secondSlashIndex == -1 || !isFromFound || !isToFound) {
                    throw new IllegalArgumentException("Invalid event format. Use: event task /from date /to date");
                }

                String description = line.substring(INDEX_AFTER_EVENT, firstSlashIndex).trim();
                if (description.isEmpty()) {
                    throw new IndexOutOfBoundsException("Event is empty! Please add an appropriate name :P");
                }

                String from = (firstSlashIndex + INDEX_AFTER_FROM < line.length())
                        ? line.substring(firstSlashIndex + INDEX_AFTER_FROM, secondSlashIndex).trim()
                        : "";
                if (from.isEmpty()) {
                    throw new IllegalArgumentException("No from date entered, please enter a date!");
                }

                String to = (secondSlashIndex + INDEX_AFTER_TO < line.length())
                        ? line.substring(secondSlashIndex+ INDEX_AFTER_TO).trim()
                        : "";
                if (to.isEmpty()) {
                    throw new IllegalArgumentException("No to date entered, please enter a date!");
                }

                listOfTasks.add(new Event(description, from, to));
                TaskFile.appendTaskFile(listOfTasks.get(listOfTasks.size() - 1).toString());
                break;
            default:
                throw new IllegalArgumentException("""
                        Unknown task type. Please use either formats:
                        1. todo task
                        2. deadline task /by date
                        3. event task /from date /to date""");
            }
            System.out.println("Task successfully added!");
            System.out.println(listOfTasks.get(listOfTasks.size() - 1).toString());
            System.out.println("You now have " + listOfTasks.size() + " task(s) in the list.");
            System.out.println(DASH);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }

    public static void deleteTask(ArrayList<Tasks.Task> listOfTasks, String line) {
        try {
            int taskNum = Integer.parseInt(line.substring(INDEX_AFTER_DELETE).trim());
            if (taskNum < 1 || taskNum > listOfTasks.size()) {
                throw new IndexOutOfBoundsException("Invalid task number");
            }

            System.out.println("Task successfully deleted!");
            TaskFile.deleteLineInTaskFile(taskNum);
            System.out.println(listOfTasks.get(taskNum - 1).toString());
            listOfTasks.remove(taskNum - 1);

            System.out.println("You now have " + listOfTasks.size() + " task(s) in the list.");
            System.out.println(DASH);

        } catch (NumberFormatException e) {
            System.out.println("Invalid task format. Use: delete <number");
            System.out.println(DASH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Tasks.Task> listOfTasks = new ArrayList<>();

        System.out.println("Hello! I'm Doobert!\n");

        System.out.println("These are your previously recorded tasks:");
        TaskFile.printTaskFileContents();
        TaskFile.updateTaskArray(listOfTasks);

        System.out.println("\nWhat can I do for you today?");
        System.out.println(DASH);

        String inputLine = input.nextLine();
        while (!inputLine.equals("bye") && listOfTasks.size() <= MAX_NO_OF_TASKS) {
            try {
                if (inputLine.equals("list")) { //lists out todo list
                    listTasks(listOfTasks);
                } else if (inputLine.matches("^mark \\d+$")) { //marks xx as done
                    markTask(listOfTasks, inputLine);
                } else if (inputLine.matches("^unmark \\d+$")) { //marks xx as undone
                    unmarkTask(listOfTasks, inputLine);
                } else if (inputLine.matches("^delete \\d+$")) {
                    deleteTask(listOfTasks, inputLine);
                } else {
                    addTask(listOfTasks, inputLine);
                }
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                System.out.println(DASH);
            }
            inputLine = input.nextLine();
        }

        System.out.println("Bye! See you later alligator");
        input.close();
    }
}