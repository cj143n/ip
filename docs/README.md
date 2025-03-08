# Doobert User Guide

Doobert is an application for managing tasks, optimised for use via a Command Line Interface (CLI).

- [Features](#features)
    - [Listing tasks: `list`](#listing-tasks-list)
    - [Adding a Todo task: `todo`](#adding-a-todo-task-todo)
    - [Adding a task with Deadline: `deadline`](#adding-a-task-with-deadline-deadline)
    - [Adding an Event: `event`](#adding-an-event-event)
    - [Marking task as done: `mark`](#marking-task-as-done-mark)
    - [Unmarking task: `unmark`](#unmarking-task-unmark)
    - [Deleting task: `delete`](#deleting-task-delete)
    - [Finding task using a keyword: `find`](#finding-task-using-a-keyword-find)
    - [Exiting the program: `bye`](#exiting-the-program-bye)

## Features
Notes about the command format: <br>
- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
e.g. in `todo DESCRIPTION`, description is a parameter which can be used as `todo go for a run`.

### Listing tasks: `list`
Lists out all the tasks.<br>

Format: `list`

### Adding a Todo task: `todo`
Adds a Todo task into the task list.<br>

Format: `todo DESCRIPTION`

Example:`todo go swimming`

### Adding a task with Deadline: `deadline`
Adds a task with deadline into the task list.<br>

Format: `deadline DESCRIPTION /by DEADLINE`

Example:`deadline finish homework /by friday 2359`

### Adding an Event: `event`
Adds an event into the task list.<br>

Format: `event DESCRIPTION /from START /to END`

Example:`event attend party /from 6pm /to 11pm`

### Marking task as done: `mark`
Marks a task in the task list as done.<br>

Format: `mark INDEX`
- `INDEX` has to be less than or equal to the number of tasks in the task list<br>

Example:`mark 3`

### Unmarking task: `unmark`
Unmarks a task in the task list as undone.<br>

Format: `unmark INDEX`
- `INDEX` has to be less than or equal to the number of tasks in the list<br>

Example:`unmark 7`

### Deleting task: `delete`
Deletes a task in task list.<br>

Format: `delete INDEX`
- `INDEX` has to be less than or equal to the number of tasks in the list<br>

Example:`delete 1`

### Finding task using a keyword: `find`
Lists out all tasks from task list containing the keyword.<br>

Format: `find KEYWORD`
- `KEYWORD` has to be one word.<br>
- `KEYWORD` is case sensitive.<br>

Example:`find book`

### Exiting the program: `bye`

Exits the program.<br>

Format: `exit`
