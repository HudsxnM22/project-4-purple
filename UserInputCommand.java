/** A command that responds to user input during the tour. */
public interface UserInputCommand {
    /**
     * Carries out the command and returns a response for the user.
     *
     * @return the response to display to the user
     */
    String carryOut();
}
