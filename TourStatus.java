import java.util.ArrayList;

/**
 * Tracks the campus being toured and the user's current location.
 *
 * @author Hudson Maiorana
 */
public class TourStatus {
    private Campus campus;
    private Location currentLocation;
    private ArrayList<Item> backpack;
    private static TourStatus tourInstance;

    /**
     * Creates a tour status without a selected campus or location.
     */
    private TourStatus() {
        backpack = new ArrayList<>();
    }

    /**
     * Returns the single shared tour status.
     *
     * @return the singleton tour status
     */
    //Hope static is allowed, dont know another way.
    public static TourStatus getInstance() {
        if (tourInstance == null) {
            tourInstance = new TourStatus();
        }
        return tourInstance;
    }

    /**
     * Sets the campus being toured and empties the backpack.
     *
     * @param campus the campus being toured
     */
    public void setCampus(Campus campus) {
        this.campus = campus;
        backpack.clear();
    }

    /**
     * Sets the user's current location.
     *
     * @param loc the user's current location
     */
    public void setCurrentLocation(Location loc) {
        currentLocation = loc;
    }

    /**
     * Returns the campus being toured.
     *
     * @return the campus being toured, or null if not set
     */
    public Campus getCampus() {
        return campus;
    }

    /**
     * Returns the user's current location.
     *
     * @return the current location, or null if not set
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Moves in the given direction only if an appropriate door exists.
     *
     * @param dir the requested direction of travel
     * @return the destination, or null if no door exists in that direction
     */
    public Location updateTourLocation(String dir) {
        Location destination = currentLocation.leaveLocation(dir);
        if (destination != null) {
            currentLocation = destination;
        }
        return destination;
    }

    /**
     * Adds an item to the backpack.
     *
     * @param item the item to add
     */
    public void addToBackpack(Item item) {
        backpack.add(item);
    }

    /**
     * Moves a named backpack item to the current location.
     *
     * @param name the item name to match, ignoring capitalization
     * @return the dropped item, or null if it is not in the backpack
     */
    public Item dropItemFromBackpack(String name) {
        for (int i = 0; i < backpack.size(); i++) {
            Item item = backpack.get(i);
            if (item.getName().equalsIgnoreCase(name)) {
                backpack.remove(i);
                currentLocation.addItem(item);
                return item;
            }
        }
        return null;
    }

    /**
     * Moves a named local item into the backpack.
     *
     * @param name the exact name of the item to pick up
     * @return the picked-up item, or null if it is not in the current location
     */
    public Item pickupItemFromLocation(String name) {
        Item item = currentLocation.getItemNamed(name);
        if (item != null) {
            currentLocation.removeItem(item);
            addToBackpack(item);
        }
        return item;
    }

    /**
     * Lists backpack contents, including a message when empty.
     *
     * @return the item names, or a message if the backpack is empty
     */
    public String listBackpackItems() {
        if (backpack.isEmpty()) {
            return "Your backpack is empty.";
        }
        String result = "Backpack:";
        for (Item item : backpack) {
            result += "\n- " + item.getName();
        }
        return result;
    }
}
