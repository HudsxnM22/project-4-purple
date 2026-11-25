import java.util.HashMap;

/** Gear that can be picked up during the campus tour. */
public class Item {
    private String name;
    private String message;

    /** Creates an item without a name or message. */
    public Item() {}

    /**
     * Creates a named item with its pickup message.
     *
     * @param n the item name
     * @param m the message displayed when picked up
     */
    public Item(String n, String m) {
        name = n;
        message = m;
    }

    /**
     * Returns the item name.
     *
     * @return the item name, or null if not set
     */
    public String getName() { return name; }

    /**
     * Returns the pickup message.
     *
     * @return the pickup message, or null if not set
     */
    public String getMessage() { return message; }

    /**
     * Sets the item name.
     *
     * @param name the new item name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets the pickup message.
     *
     * @param msg the new pickup message
     */
    public void setMessage(String msg) { message = msg; }
}
