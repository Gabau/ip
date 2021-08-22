package util.tasks;

import java.time.LocalDate;
import util.commons.*;

public class Events extends DatedTask {
    private static String label = "[E]";



    private Events(String name, String date) {
        super(name.trim(), date.trim());

    }

    public static Events of(String total) throws DukeException {
        String[] ss = total.split("/at", 2);
        if (ss.length == 0) throw new DukeException(Messages.EVENT_NO_INPUT_ERROR_MESSAGE);
        return new Events(ss[0], ss[1]);
    }

    public static Events of(String name, String date) throws DukeException {
        return new Events(name, date);
    }



    @Override
    public String toString() {
        return this.label + super.toString() + " (at: " + this.localDate() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Events) {
            Events e = (Events) obj;
            return this.name.equals(e.name) && e.lDate.equals(this.lDate);
        }
        return false;
    }

    @Override
    public String encode() {
        //String indicating whether this task is done or not.
        String d = this.isDone()
                ? Task.DONE
                : Task.NOTDONE;

        return Task.Label.E + Task.DELIMITER + d + Task.DELIMITER + this.name + Task.DELIMITER + this.lDate;
    }
}
