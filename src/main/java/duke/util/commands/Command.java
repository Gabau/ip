package duke.util.commands;


import duke.util.tasks.DukeException;


@FunctionalInterface
public interface Command {
    /**
     * The execution of a command.
     *
     * @throws DukeException When the command activity throws the exception.
     */
    public String execute() throws DukeException;
}