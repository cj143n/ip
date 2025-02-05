import java.util.Scanner;

class Doobert {
    public static void listTasks(Task[] listOfTasks, String[] typeOfTask, int count) {
        if (count == 1) {
            System.out.println("No tasks yet!");
            return;
        }
        System.out.println("Here is your list of tasks: ");
        for (int i = 0; i < count - 1; i++) {
            System.out.println((i + 1) + "." + typeOfTask[i] + listOfTasks[i].toString());
        }
    }

    public static void markTask(Task[] listOfTasks, int count, String line) {
        try {
            int taskNo = Integer.parseInt(line.substring(5).trim()); //index after "mark"
            if (taskNo < 1 || taskNo >= count) {
                throw new IndexOutOfBoundsException("Invalid task number");
            }
            listOfTasks[taskNo - 1].markDone();
            System.out.println("Task successfully marked!");
            System.out.println(listOfTasks[taskNo - 1].toString());
        } catch (NumberFormatException e) {
            System.out.println("Invalid task format. Use: mark <number>");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void unmarkTask(Task[] listOfTasks, int count, String line) {
        try {
            int taskNo = Integer.parseInt(line.substring(7).trim()); //index after "unmark"
            if (taskNo < 1 || taskNo >= count) {
                throw new IndexOutOfBoundsException("Invalid task number");
            }
            listOfTasks[taskNo - 1].markUndone();
            System.out.println("Task successfully unmarked!");
            System.out.println(listOfTasks[taskNo - 1].toString());
        } catch (NumberFormatException e) {
            System.out.println("Invalid task format. Use: unmark <number>");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean addTask(Task[] listOfTasks, String[] typeOfTask, int count, String line) { //differentiate between event, deadline, todo
        try {
            String firstWord = line.split(" ")[0];
            switch (firstWord) {
            case "todo":
                listOfTasks[count - 1] = new Task(line.substring(4).trim());
                typeOfTask[count - 1] = "[T]";
                break;

            case "deadline":
                int slashIndex = line.indexOf('/');
                if (slashIndex == -1 || !(line.substring(slashIndex + 1).startsWith("by "))) {
                    throw new IllegalArgumentException("Invalid deadline format. Use: deadline task /by date");
                }

                listOfTasks[count - 1] = new Deadline(line.substring(8, slashIndex).trim(), line.substring(slashIndex + 4).trim());
                typeOfTask[count - 1] = "[D]";
                break;

            case "event":
                int firstSlashIndex = line.indexOf('/');
                int secondSlashIndex = line.indexOf('/', firstSlashIndex + 5);
                if (firstSlashIndex == -1 || secondSlashIndex == -1 || !(line.substring(firstSlashIndex + 1).startsWith("from ")) || !(line.substring(secondSlashIndex + 1).trim().startsWith("to "))) {
                    throw new IllegalArgumentException("Invalid deadline format. Use: event task /from date /to date");
                }

                listOfTasks[count - 1] = new Event(line.substring(5, firstSlashIndex).trim(), line.substring(firstSlashIndex + 6, secondSlashIndex).trim(), line.substring(secondSlashIndex + 4).trim());
                typeOfTask[count - 1] = "[E]";
                break;
            default:
                throw new IllegalArgumentException("Unknown task type. Please use either formats:\n 1. todo task \n 2. deadline task /by date \n 3. event task /from date /to date ");
            }
            System.out.println("Task successfully added!");
            System.out.println(typeOfTask[count - 1] + listOfTasks[count - 1].toString());
            System.out.println("You now have " + count + " task(s) in the list.");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Task[] listOfTasks = new Task[100];
        String[] typeOfTask = new String[100];
        int count = 1;

        System.out.println("Hello! I'm Doobert!");
        System.out.println("What can I do for you?");

        String line = input.nextLine();
        while (!line.equals("bye") && count <= 100) {
            try {
                if (line.equals("list")) { //lists out todo list
                    listTasks(listOfTasks,typeOfTask, count);
                } else if (line.matches("^mark \\d+$")) { //marks xx as done
                    markTask(listOfTasks, count, line);
                } else if (line.matches("^unmark \\d+$")) { //marks xx as undone
                    unmarkTask(listOfTasks, count, line);
                } else {
                    boolean isAdded = addTask(listOfTasks, typeOfTask, count, line);
                    if (isAdded) {
                        count++;
                    }

                }
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
            line = input.nextLine();
        }

        System.out.println("Bye! See you later alligator");
        input.close();
    }
}