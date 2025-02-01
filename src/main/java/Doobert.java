import java.util.Scanner;

class Doobert {
    public static void listTasks(Task[] listOfTasks, int count) {
        if (count == 1) {
            System.out.println("No tasks yet!");
            return;
        }
        for (int i = 0; i < count-1; i++) {
            System.out.println((i+1) + "." + listOfTasks[i].output());
        }
    }

    public static void markTask(Task[] listOfTasks, int count, String line) {
        int taskNo = Integer.parseInt(line.substring(5));
        if (taskNo < 1 || taskNo >= count) {
            System.out.println("Invalid task number, please try again");
            return;
        }
        listOfTasks[taskNo - 1].markDone();
        System.out.println("Task successfully marked!");
        System.out.println(listOfTasks[taskNo - 1].output());
    }

    public static void unmarkTask(Task[] listOfTasks, int count, String line) {
        int taskNo = Integer.parseInt(line.substring(7));
        if (taskNo < 1 || taskNo >= count) {
            System.out.println("Invalid task number, please try again");
            return;
        }
        listOfTasks[taskNo - 1].markUndone();
        System.out.println("Task successfully unmarked!");
        System.out.println(listOfTasks[taskNo - 1].output());
    }

    public static void addTask(Task[] listOfTasks, int count, String line) {
        System.out.println("added: " + line);
        listOfTasks[count-1] = new Task(line);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Task[] listOfTasks = new Task[100];
        int count = 1;

        System.out.println("Hello! I'm Doobert!");
        System.out.println("What can I do for you?");

        String line = input.nextLine();
        while (!line.equals("bye") && count <= 100) {
            if (line.equals("list")) { //lists out todo list
                listTasks(listOfTasks, count);
            } else if (line.matches("^mark \\d+$")) {
                markTask(listOfTasks, count, line);
            } else if (line.matches("^unmark \\d+$")) {
                unmarkTask(listOfTasks, count, line);
            } else {
                addTask(listOfTasks, count, line);
                count++;
            }
            line = input.nextLine();
        }

        System.out.println("Bye! See you later alligator");
        input.close();
    }
}