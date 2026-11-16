/** Displays the items carried by the user. */
public class BackpackCommand implements UserInputCommand {
    /** Creates a backpack command. */
    public BackpackCommand() {}

    /**
     * Returns the backpack contents.
     *
     * @return the item names, or a message if the backpack is empty
     */
    @Override
    public String carryOut() {
        return TourStatus.getInstance().listBackpackItems();
    }
}
