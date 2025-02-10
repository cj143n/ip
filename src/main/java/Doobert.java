import java.util.Scanner;

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
    private static final String DASH = "--------------------------------------------------";

    public static void listTasks(Task[] listOfTasks, int count) {
        if (count == 1) {
            System.out.println("No tasks yet!");
            System.out.println(DASH);
            return;
        }
        System.out.println("Here is your list of tasks:");
        for (int i = 0; i < count - 1; i++) {
            System.out.println((i + 1) + "." + listOfTasks[i].toString());
        }
        System.out.println(DASH);
    }

    public static void markTask(Task[] listOfTasks, int count, String line) {
        try {
            int taskNum = Integer.parseInt(line.substring(INDEX_AFTER_MARK).trim());
            if (taskNum < 1 || taskNum >= count) {
                throw new IndexOutOfBoundsException("Invalid task number");
            }
            listOfTasks[taskNum - 1].markDone();
            System.out.println("Task successfully marked!");
            System.out.println(listOfTasks[taskNum - 1].toString());
            System.out.println(DASH);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task format. Use: mark <number>");
            System.out.println(DASH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }

    public static void unmarkTask(Task[] listOfTasks, int count, String line) {
        try {
            int taskNum = Integer.parseInt(line.substring(INDEX_AFTER_UNMARK).trim());
            if (taskNum < 1 || taskNum >= count) {
                throw new IndexOutOfBoundsException("Invalid task number");
            }
            listOfTasks[taskNum - 1].markUndone();
            System.out.println("Task successfully unmarked!");
            System.out.println(listOfTasks[taskNum - 1].toString());
            System.out.println(DASH);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task format. Use: unmark <number>");
            System.out.println(DASH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }

    public static boolean addTask(Task[] listOfTasks, int count, String line) { //differentiate between event, deadline, todo
        try {
            String firstWord = line.split(" ")[0];
            switch (firstWord) {
            case "todo":
                listOfTasks[count - 1] = new Todo(line.substring(INDEX_AFTER_TODO).trim());
                break;

            case "deadline":
                int slashIndex = line.indexOf('/');
                if (slashIndex == -1 || !(line.substring(slashIndex + 1).startsWith("by "))) {
                    throw new IllegalArgumentException("Invalid deadline format. Use: deadline task /by date");
                }

                String desc = line.substring(INDEX_AFTER_DEADLINE, slashIndex).trim();
                String date = line.substring(slashIndex + INDEX_AFTER_BY).trim();
                listOfTasks[count - 1] = new Deadline(desc, date);
                break;

            case "event":
                int firstSlashIndex = line.indexOf('/');
                int secondSlashIndex = line.indexOf('/', firstSlashIndex + INDEX_AFTER_FROM);
                boolean isFromFound = line.substring(firstSlashIndex + 1).startsWith("from ");
                boolean isToFound = line.substring(secondSlashIndex + 1).trim().startsWith("to ");
                if (firstSlashIndex == -1 || secondSlashIndex == -1 || !isFromFound || !isToFound) {
                    throw new IllegalArgumentException("Invalid deadline format. Use: event task /from date /to date");
                }

                String description = line.substring(INDEX_AFTER_EVENT, firstSlashIndex).trim();
                String from = line.substring(firstSlashIndex + INDEX_AFTER_FROM, secondSlashIndex).trim();
                String to = line.substring(secondSlashIndex+ INDEX_AFTER_TO).trim();
                listOfTasks[count - 1] = new Event(description, from, to);
                break;
            default:
                throw new IllegalArgumentException("""
                        Unknown task type. Please use either formats:
                        1. todo task
                        2. deadline task /by date
                        3. event task /from date /to date""");
            }
            System.out.println("Task successfully added!");
            System.out.println(listOfTasks[count - 1].toString());
            System.out.println("You now have " + count + " task(s) in the list.");
            System.out.println(DASH);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Task[] listOfTasks = new Task[MAX_NO_OF_TASKS];
        int count = 1;

        System.out.println("Hello! I'm Doobert!");
        System.out.println("What can I do for you?");
        System.out.println(DASH);

        String inputLine = input.nextLine();
        while (!inputLine.equals("bye") && count <= MAX_NO_OF_TASKS) {
            try {
                if (inputLine.equals("list")) { //lists out todo list
                    listTasks(listOfTasks, count); //typeOfTask, count);
                } else if (inputLine.matches("^mark \\d+$")) { //marks xx as done
                    markTask(listOfTasks, count, inputLine);
                } else if (inputLine.matches("^unmark \\d+$")) { //marks xx as undone
                    unmarkTask(listOfTasks, count, inputLine);
                } else {
                    boolean isAdded = addTask(listOfTasks, count, inputLine);
                    if (isAdded) {
                        count++;
                    }

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