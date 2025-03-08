package Doobert;

import java.util.Scanner;

import FileManager.TaskFile;
import TaskList.TaskList;
import Parser.Parser;
import Ui.Ui;


class Doobert {
    private static final int MAX_NO_OF_TASKS = 100;

    private TaskFile taskFile;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Doobert(String filePath) {
        ui = new Ui();
        taskFile = new TaskFile(filePath);
        parser = new Parser();
        tasks = new TaskList();
    }


    public void run() {
        Scanner input = new Scanner(System.in);

        ui.welcomeMessage(taskFile, tasks);

        String inputLine = input.nextLine();
        while (!inputLine.equals("bye") && tasks.size() <= MAX_NO_OF_TASKS) {
            parser.readInput(inputLine, tasks, taskFile);

            inputLine = input.nextLine();
        }

        if (tasks.size() >= MAX_NO_OF_TASKS) {
            System.out.println("You have reached the maximum input for tasks in the list.");
        }

        ui.goodbyeMessage();
        input.close();
    }

    public static void main(String[] args) {
        new Doobert("./data/Doobert.txt").run();
    }
}