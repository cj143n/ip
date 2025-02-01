import java.util.Scanner;

class Doobert{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        String[] listOfTasks = new String[100];
        int count = 1;

        System.out.println("Hello! I'm Doobert!");
        System.out.println("What can I do for you?");

        String line = input.nextLine();
        while (!line.equals("bye") && count <= 100) {
            if (line.equals("list")) { //lists out todo list
                for (int i = 0; i < count-1; i++) {
                    System.out.println(listOfTasks[i]);
                }
            } else {
                System.out.println("added: " + line); //adding task into list
                listOfTasks[count-1] = (count + ". " + line);
                count++;
            }
            line = input.nextLine();
        }

        System.out.println("Bye! See you later alligator");
    }
}
