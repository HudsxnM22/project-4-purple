/** Handles pickup commands during the campus tour. */
public class PickupCommand implements UserInputCommand {
    private String itemName;

    /**
     * Stores the command argument.
     *
     * @param itemName the name of the item to pick up
     */
    public PickupCommand(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Carries out the command and returns its result.
     *
     * @return the pickup message, or an error if the item is not present
     */
    @Override
    public String carryOut() {
        Item item = TourStatus.getInstance().pickupItemFromLocation(itemName);
        if (item == null) {
            return "There is no " + itemName + " in this location.";
        }
        return item.getMessage();
    }
}
