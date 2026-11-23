/**
 * Represents a one-way connection between two locations.
 *
 * @author Hudson Maiorana
 */
public class Door {
    private String direction;
    private Location leavingLocation;
    private Location enter;
    private Location car;
    private Location enteringLocation;

    /**
     * Creates a door without a direction or connected locations.
     */
    public Door() {}

    /**
     * Creates a door connecting the given locations in the given direction.
     *
     * @param dir the direction of travel
     * @param leave the location the door leaves
     * @param enter the location the door enters
     */
    public Door(String dir, Location leave, Location enter) {
        direction = dir;
        leavingLocation = leave;
        enteringLocation = enter;
    }

    /**
     * Describes the direction and destination of this door.
     *
     * @return the door's direction and destination as a sentence
     */
    public String describe() {
        return "You can go " + this.direction + " to get to " + this.enteringLocation.getName() + ".";
    }

    /**
     * Returns the location this door leaves.
     *
     * @return the leaving location, or null if not set
     */
    public Location getLeaving() {
        return leavingLocation;
    }

    /**
     * Returns the location this door enters.
     *
     * @return the entering location, or null if not set
     */
    public Location getEntering() {
        return enteringLocation;
    }

    /**
     * Returns the direction of travel.
     *
     * @return the direction of travel, or null if not set
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the location this door leaves.
     *
     * @param leave the location the door leaves
     */
    public void setLeaving(Location leave) {
        leavingLocation = leave;
    }

    /**
     * Sets the location this door enters.
     *
     * @param enter the location the door enters
     */
    public void setEntering(Location enter) {
        enteringLocation = enter;
    }

    /**
     * Sets the direction of travel.
     *
     * @param dir the direction of travel
     */
    public void setDirection(String dir) {
        direction = dir;
    }
}
