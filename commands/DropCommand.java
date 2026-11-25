/** Handles drop commands during the campus tour. */
public class DropCommand implements UserInputCommand {
    private String itemName;

    /**
     * Stores the command argument.
     *
     * @param itemName the name of the item to drop
     */
    public DropCommand(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Carries out the command and returns its result.
     *
     * @return the drop confirmation, or an error if the item is not carried
     */
    @Override
    public String carryOut() {
        TourStatus tour = TourStatus.getInstance();
        Item item = tour.dropItemFromBackpack(itemName);
        if (item == null) {
            return "Your backpack does not contain " + itemName + ".";
        }
        return "The " + item.getName() + " has been dropped in " + tour.getCurrentLocation().getName() + ".";
    }
}
