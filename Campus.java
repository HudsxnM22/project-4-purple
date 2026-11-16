import java.util.Hashtable;

/**
 * Represents a campus, its locations, and the starting point of a tour.
 *
 * @author Hudson Maiorana
 */
public class Campus {
    private String campusName;
    private Hashtable<String, Location> locations;
    private Location startingLocation;
    private String filename;

    /**
     * Creates an empty campus.
     */
    public Campus() {
        locations = new Hashtable<>();
    }

    /**
     * Creates a campus with the given name.
     *
     * @param name the campus name
     */
    public Campus(String name) {
        this();
        campusName = name;
    }

    /**
     * Adds a location under its name.
     *
     * @param location the location to add under its name
     * @throws NullPointerException if the location or its name is null
     */
    public void addLocation(Location location) {
        locations.put(location.getName(), location);
    }

    /**
     * Returns the named location, or null if it does not exist.
     *
     * @param name the location name to look up
     * @return the named location, or null if it does not exist
     * @throws NullPointerException if the name is null
     */
    public Location getLocation(String name) {
        return locations.get(name);
    }

    /**
     * Sets the campus name.
     *
     * @param n the campus name
     */
    public void setCampusName(String n) {
        campusName = n;
    }

    /**
     * Returns the campus name.
     *
     * @return the campus name, or null if not set
     */
    public String getCampusName() {
        return campusName;
    }

    /**
     * Sets the starting location.
     *
     * @param start the starting location
     */
    public void setStartingLocation(Location start) {
        startingLocation = start;
    }

    /**
     * Returns the starting location.
     *
     * @return the starting location, or null if not set
     */
    public Location getStartingLocation() {
        return startingLocation;
    }

    /**
     * Sets the campus data filename.
     *
     * @param f the campus data filename
     */
    public void setFilename(String f) {
        filename = f;
    }

    /**
     * Returns the campus data filename.
     *
     * @return the campus data filename, or null if not set
     */
    public String getFilename() {
        return filename;
    }
}
