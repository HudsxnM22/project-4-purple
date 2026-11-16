/** Handles movement commands during the campus tour. */
public class MovementCommand implements UserInputCommand {
    private String dir;

    /**
     * Stores the command argument.
     *
     * @param dir the direction of travel (n, s, e, or w)
     */
    public MovementCommand(String dir) {
        this.dir = dir;
    }

    /**
     * Carries out the command and returns its result.
     *
     * @return the destination description, or an error if movement is blocked
     */
    @Override
    public String carryOut() {
        Location location = TourStatus.getInstance().updateTourLocation(dir);
        if (location == null) {
            return "You cannot go in that direction.";
        }
        location.setHaveVisited(true);
        return location.describeLocation();
    }
}
