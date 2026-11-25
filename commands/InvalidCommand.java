/** Handles invalid commands during the campus tour. */
public class InvalidCommand implements UserInputCommand {
    private String invalidCommand;

    /**
     * Stores the command argument.
     *
     * @param invalidCommand the unsupported input entered by the user
     */
    public InvalidCommand(String invalidCommand) {
        this.invalidCommand = invalidCommand;
    }

    /**
     * Carries out the command and returns its result.
     *
     * @return an error message listing the valid commands
     */
    @Override
    public String carryOut() {
        return "Invalid command: " + invalidCommand + ". Use n/s/e/w, pickup (p) itemName, drop (d) itemName, " + "backpack (b), or quit (q).";
    }
}
