package TaskList;

import FileManager.TaskFile;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;

import java.util.ArrayList;

public class TaskList {
    private static final String DASH = "--------------------------------------------------";
    private static final int INDEX_AFTER_MARK = 5;
    private static final int INDEX_AFTER_UNMARK = 7;
    private static final int INDEX_AFTER_TODO = 4;
    private static final int INDEX_AFTER_DEADLINE = 8;
    private static final int INDEX_AFTER_EVENT = 5;
    private static final int INDEX_AFTER_BY = 4;
    private static final int INDEX_AFTER_FROM = 5;
    private static final int INDEX_AFTER_TO = 4;
    private static final int INDEX_AFTER_DELETE = 7;
    private static final int INDEX_AFTER_FIND = 5;

    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public int size() {
        return tasks.size();
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet!");
            System.out.println(DASH);
            return;
        }
        System.out.println("Here is your list of tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i).toString());
        }
        System.out.println(DASH);
    }

    public void markTask(TaskFile taskFile, String line) {
        try {
            int taskNum = Integer.parseInt(line.substring(INDEX_AFTER_MARK).trim());
            if (taskNum < 1 || taskNum > tasks.size()) {
                throw new IndexOutOfBoundsException("Invalid task number");
            }
            tasks.get(taskNum - 1).markDone();
            System.out.println("Task successfully marked!");

            System.out.println(tasks.get(taskNum - 1).toString());
            taskFile.editLineInTaskFile(taskNum - 1, tasks.get(taskNum - 1).toString());

            System.out.println(DASH);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task format. Use: mark <number>");
            System.out.println(DASH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }

    public void unmarkTask(TaskFile taskFile, String line) {
        try {
            int taskNum = Integer.parseInt(line.substring(INDEX_AFTER_UNMARK).trim());
            if (taskNum < 1 || taskNum > tasks.size()) {
                throw new IndexOutOfBoundsException("Invalid task number");
            }
            tasks.get(taskNum - 1).markUndone();
            System.out.println("Task successfully unmarked!");

            System.out.println(tasks.get(taskNum - 1).toString());
            taskFile.editLineInTaskFile(taskNum - 1, tasks.get(taskNum - 1).toString());

            System.out.println(DASH);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task format. Use: unmark <number>");
            System.out.println(DASH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }

    public void addTask(TaskFile taskFile, String line) { //differentiate between event, deadline, todo
        try {
            String firstWord = line.split(" ")[0];
            switch (firstWord) {
            case "todo":
                String task = line.substring(INDEX_AFTER_TODO).trim();
                if (task.isEmpty()) {
                    throw new IndexOutOfBoundsException("Todo is empty! Please add an appropriate name :P");
                }

                tasks.add(new Todo(line.substring(INDEX_AFTER_TODO).trim()));
                taskFile.appendTaskFile(tasks.get(tasks.size() - 1).toString());
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

                tasks.add(new Deadline(desc, date));
                taskFile.appendTaskFile(tasks.get(tasks.size() - 1).toString());
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

                tasks.add(new Event(description, from, to));
                taskFile.appendTaskFile(tasks.get(tasks.size() - 1).toString());
                break;
            default:
                throw new IllegalArgumentException("""
                        Unknown task type. Please use either formats:
                        1. todo task
                        2. deadline task /by date
                        3. event task /from date /to date""");
            }
            System.out.println("Task successfully added!");
            System.out.println(tasks.get(tasks.size() - 1).toString());
            System.out.println("You now have " + tasks.size() + " task(s) in the list.");
            System.out.println(DASH);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void deleteTask(TaskFile taskFile, String line) {
        try {
            int taskNum = Integer.parseInt(line.substring(INDEX_AFTER_DELETE).trim());
            if (taskNum < 1 || taskNum > tasks.size()) {
                throw new IndexOutOfBoundsException("Invalid task number");
            }

            System.out.println("Task successfully deleted!");
            taskFile.deleteLineInTaskFile(taskNum);
            System.out.println(tasks.get(taskNum - 1).toString());
            tasks.remove(taskNum - 1);

            System.out.println("You now have " + tasks.size() + " task(s) in the list.");
            System.out.println(DASH);

        } catch (NumberFormatException e) {
            System.out.println("Invalid task format. Use: delete <number");
            System.out.println(DASH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }

    public void find(TaskFile taskFile, String line) {
        try {
            String keyword = line.substring(INDEX_AFTER_FIND).trim();
            if (keyword.split(" ").length > 1) {
                throw new IndexOutOfBoundsException("More than one keyword detected. Please enter only one word");
            } else if (keyword.isEmpty()) {
                throw new IndexOutOfBoundsException("Keyword is empty! Please add a keyword :P");
            }

            int count = 0;
            for (Task task : tasks) {
                if (task.getDescription().contains(keyword)) {
                    if (count == 0) {
                        System.out.println("Here are the matching tasks in your list:");
                    }
                    count += 1;
                    System.out.print(count);
                    System.out.println(". " + task.toString());
                }
            }

            if (count == 0) {
                System.out.println("No matching tasks :(");
            }
            System.out.println(DASH);

        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println(DASH);
        }
    }
}
