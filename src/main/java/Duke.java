import java.util.Scanner;
import java.util.ArrayList;

public class Duke {


    /**
     * DukeException, an exception that arose from inaccurate input.
     *
     */
    public class DukeException extends Exception {

        public DukeException() {
            super("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }


        public DukeException(String message) {
            super(message);
        }


    }
    private static final String taskRemoved = "Noted, I've removed this task:";
    private static final String DELETE = "delete";
    private static final String TODO = "todo";
    private static final String DEADLINE = "deadline";
    private static final String EVENT = "event";
    private static final String taskComplete = "Nice, I've marked this task as done";
    private static final String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private static final String greetings = "Greetings! I'm Duke\n\tWhat can I do for you?";
    private static final String bye = "Godspeed young padawan!";
    private static final String hline = "\t----------------------------";
    //hashmap cannot enumerate
    //array of inputs capped at 100
    private final ArrayList<Task> inputs =  new ArrayList<>();
    //pointer to the last location of inputs available
    private int ptr = 0;


    /**
     * Dukes own method to print a string
     * in his own special way. (only single line inputs though, multi line requires tabs)
     *
     * @param s The string to be printed.
     */
    private void print(String s) {
        System.out.println(hline);
        System.out.println("\t" + s);
        System.out.println("\n" + hline);
    }

    /**
     * Method to print the Logo for Duke.
     */
    private void print_logo() {
        System.out.println("Hello from\n" + Duke.logo);

    }

    /**
     * Checks if the input task has been added before or not.
     *
     * @param t The Task to check.
     * @return True if the string has been added and false otherwise.
     */
    private boolean isAdded(Task t) {
        for (int i = 0; i < ptr; i++) {
            if (inputs.get(i).equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the input task to the list.
     *
     * @param t Input task
     */
    private void add(Task t) {
        this.inputs.add(t);
        this.ptr++;
        this.print("Got it, I've added this task\n\t  " + t.toString() +
                "\n\tNow you have " + ptr + " tasks in the list.");

    }

    /**
     * Prints all the strings added.
     */
    private void list() throws DukeException {
        String output = "";
        if (ptr == 0) return;
        output += "1." + this.inputs.get(0);
        for (int i = 1; i < ptr; i++) {
            int indi = i + 1;
            output += "\n\t" + indi + "." + this.inputs.get(i);
        }
        this.print(output);
    }

    /**
     * Creates a todo task with the ipt String
     *
     * @param ipt The input string
     */
    private void todo(String ipt) {
        Task t = new ToDos(ipt);
        if (isAdded(t)) {
            this.print("Added: " + t.toString());
            return;
        }
        this.add(t);

    }

    /**
     * Creates a deadLine task with the input string
     *
     * @param input The input String
     * @throws DukeException Exceptions in a duke object.
     */
    private void deadline(String input) throws DukeException {
        String[] arr = input.split("/by", 2);

        if (arr.length == 1) throw new DukeException("☹ OOPS!!! The deadline must be filled in prefixed by /by");
        Task t = new Deadlines(arr[0], arr[1]);
        if (isAdded(t)) {
            this.print("Added: " + t.toString());
            return;
        }
        this.add(t);

    }


    /**
     * Creates an Event with the input String
     *
     * @param input The input String
     * @throws DukeException Exceptions in a Duke object due to problems with task input.
     */
    private void event(String input) throws DukeException {
        String[] arr = input.split("/at", 2);
        if (arr.length == 1) throw new DukeException("☹ OOPS!!! The event deadline must be filled in prefixed by /at");
        Task t = new Events(arr[0], arr[1]);
        if (isAdded(t)) {
            this.print("Added: " + t.toString());
            return;
        }
        this.add(t);
    }


    /**
     * To understand the input.
     *
     * @param input The input String
     * @throws DukeException Exception due to wrong input.
     */
    public void inputsParser(String input) throws DukeException {
        String[] twoInputs = input.split(" ", 2);
        if (twoInputs[0].equals(DELETE)) {
            if (twoInputs.length == 1) {
                throw new DukeException("Number expected after done.");
            }
            String s = twoInputs[1].trim();
            int val = Integer.parseInt(s) - 1;
            if (val >= ptr)
                throw new DukeException("☹ OOPS!!! The list is not that long!");
            Task removed = inputs.remove(val);
            print(taskRemoved + "\n\t" + removed.toString());
            ptr--;
        } else if (twoInputs[0].equals("done")) {

            //and at what point is this considered using exception handling to dictate the control
            if (twoInputs.length == 1) {
                throw new DukeException("Number expected after done.");
            }
            String s = twoInputs[1].trim();
            int val = Integer.parseInt(s) - 1;
            if (val >= ptr)
                throw new DukeException("☹ OOPS!!! The list is not that long!");
            inputs.get(val).done();
            print(taskComplete + "\n\t" + this.inputs.get(val));
            //this is ridiculous, is there a way to nest the exceptions within the class
            //without having to use the array as an input... -- maybe encapsulate the parsing in a function to make it look neat
        } else if (twoInputs[0].equals(TODO)) {
            if (twoInputs.length == 1)
                throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
            todo(twoInputs[1]);
        } else if (twoInputs[0].equals(EVENT)) {
            if (twoInputs.length == 1)
                throw new DukeException("☹ OOPS!!! The description of a event cannot be empty.");
            event(twoInputs[1]);
        } else if (twoInputs[0].equals(DEADLINE)) {
            if (twoInputs.length == 1)
                throw new DukeException("☹ OOPS!!! The description of a deadline cannot be empty.");
            deadline(twoInputs[1]);
        } else if (input.equals("list")) {
            this.list();

        } else {
            throw new DukeException();
        }
    }



    /**
     * Running Duke.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        //initialising Duke
        //via greetings
        this.print_logo();
        this.print(greetings);
        String inpt = sc.nextLine();
        while(!inpt.equals("bye")) {
            try {
                inputsParser(inpt);
            } catch (DukeException e) {
                this.print(e.getMessage());
            }
            inpt = sc.nextLine();
        }
        this.print(bye);
    }



    public static void main(String[] args) {
        Duke d = new Duke();
        d.run();



    }
}
