import java.util.Scanner;

class Doobert{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        System.out.println("Hello! I'm Doobert!");
        System.out.println("What can I do for you?");

        String line = input.nextLine();
        while (!line.equals("bye")) {
            System.out.println(line);
            line = input.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
