import java.util.ArrayList;

/**
 * Represents a campus location, its description, visit status, and exits.
 *
 * @author Hudson Maiorana
 */
public class Location {
    private String name;
    private String description;
    private Boolean haveVisited;
    private ArrayList<Door> doors;
    private ArrayList<Item> items;

    /**
     * Creates an unvisited location with an empty list of doors.
     */
    public Location() {
        haveVisited = false;
        doors = new ArrayList<>();
        items = new ArrayList<>();
    }

    /**
     * Creates an unvisited location with the given name and description.
     *
     * @param name the location name
     * @param desc the location description
     */
    public Location(String name, String desc) {
        this();
        this.name = name;
        description = desc;
    }

    /**
     * Returns the location name.
     *
     * @return the location name, or null if not set
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the location description.
     *
     * @return the location description, or null if not set
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether this location has been visited.
     *
     * @return true if the location has been visited; false otherwise
     */
    public boolean getHaveVisited() {
        return haveVisited;
    }

    /**
     * Sets the location name.
     *
     * @param n the location name
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * Sets the location description.
     *
     * @param d the location description
     */
    public void setDescription(String d) {
        description = d;
    }

    /**
     * Sets whether this location has been visited.
     *
     * @param v whether the location has been visited
     */
    public void setHaveVisited(boolean v) {
        haveVisited = v;
    }

    /**
     * Describes all available doors from this location.
     *
     * @return the door descriptions separated by newlines, or an empty string if there are no doors
     */
    public String describeDoors() {
        StringBuilder result = new StringBuilder();
        for (Door door : doors) {
            result.append(door.describe()).append("\n");
        }
        return result.toString();
    }

    /**
     * Finds the destination for a direction, or null if no door matches.
     *
     * @param dir the direction to search for
     * @return the destination location, or null if no door matches
     */
    public Location leaveLocation(String dir) {
        for (Door door : doors) {
            if (door.getDirection().equals(dir)) {
                return door.getEntering();
            }
        }
        return null;
    }

    /**
     * Adds an outgoing door to this location.
     *
     * @param door the outgoing door to add
     */
    public void addDoor(Door door) {
        doors.add(door);
    }
    /**
     * Describes the location, its exits, and its current items.
     *
     * @return the name, description, doors, and current item listing
     */
    public String describeLocation() {
        return "\n" + name + "\n" + description + "\n" + describeDoors() + getItemsInLocation();
    }

    /**
     * Adds an item to this location.
     *
     * @param item the item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from this location.
     *
     * @param item the item to remove
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Finds an item by its exact name.
     *
     * @param name the case-sensitive item name to search for
     * @return the matching item, or null if none is found
     */
    public Item getItemNamed(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Lists the items currently in this location.
     *
     * @return the item names, or a message if the location has no items
     */
    public String getItemsInLocation() {
        if (items.isEmpty()) {
            return "There are no items here.";
        }
        String result = "Items here:";
        for (Item item : items) {
            result += "\n- " + item.getName();
        }
        return result;
    }
}
