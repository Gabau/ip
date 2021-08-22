package util.tasks;

import util.commons.Messages;


/**
 * The class representing the deadline task.
 *
 *
 */
public class Deadline extends DatedTask {
    private static String label = "[D]";
    private static final String DELIMITER = "/by";



    private Deadline(String s, String dl) {
        super(s.trim(), dl.trim());

    }

    /**
     * The factory method of Deadline that takes in the
     * string with the delimiter.
     *
     * @param s The string that has the delimiter for the name and date.
     * @return The created deadline object.
     * @throws DukeException
     */
    public static Deadline of(String s) throws DukeException {
        String[] ss = s.split(Deadline.DELIMITER, 2);
        if (ss.length == 0) {
            throw new DukeException(Messages.DEADLINE_NO_INPUT_ERROR_MESSAGE);
        }
        return new Deadline(ss[0], ss[1]);
    }

    /**
     * The factory method of Deadline that takes in the string
     * already separated.
     *
     * @param name The name of the deadline.
     * @param date The date of the deadline.
     * @return The created deadline object.
     * @throws DukeException
     */
    public static Deadline of(String name, String date) throws DukeException {
        return new Deadline(name, date);
    }


    @Override
    public String toString() {
        return this.label + super.toString() + " " + "(by: " + this.localDate() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Deadline) {
            Deadline dl = (Deadline) obj;
            return this.name.equals(dl.name) && this.lDate.equals(dl.lDate);
        }
        return false;
    }

    @Override
    public String encode() {
        //String indicating whether this task is done or not.
        String d = this.isDone()
                ? Task.DONE
                : Task.NOTDONE;

        return Task.Label.D + Task.DELIMITER + d + Task.DELIMITER + this.name + Task.DELIMITER + this.lDate;
    }
}
