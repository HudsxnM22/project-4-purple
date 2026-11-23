import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Runs the text-based campus tour and loads campus data from a file.
 *
 * @author Hudson Maiorana
 */
public class TourUMW {

    private static String saveFileName = "visitorProfile.txt";
    /**
     * Starts the tour and handles keyboard input.
     *
     * @param args the command-line arguments (unused)
     */
    public static void main(String[] args) {

        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Enter file name: ");
            if (!input.hasNextLine()) {
                return;
            }
            String fileName = input.nextLine().trim();
            Campus campus;
            try (Scanner scanner = new Scanner(new File(fileName))) {
                campus = setUpCampus(scanner);
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
                return;
            }
            if (campus == null) {
                return;
            }

            TourStatus tour = TourStatus.getInstance();
            tour.setCampus(campus);
            tour.setCurrentLocation(campus.getStartingLocation()); //needs to pull from saved visitor profile
            tour.getCurrentLocation().setHaveVisited(true); //need to save whether visited.
            System.out.println(tour.getCurrentLocation().describeLocation());

            while (true) {
                System.out.print("Enter n/s/e/w, pickup (p) itemName, drop (d) itemName, " + "backpack (b), or quit (q): ");
                if (!input.hasNextLine()) {
                    break;
                }
                String command = input.nextLine().trim();
                if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")) {
                    VisitorProfile vp = new VisitorProfile();
                    vp.save(saveFileName);
                    break;
                }
                System.out.println(parseInput(command).carryOut());
            }
        }
    }

    /**
     * Converts a complete input line into the appropriate command object.
     *
     * @param input the user input line, which may be null or blank
     * @return the matching command, or an InvalidCommand for unsupported input
     */
    public static UserInputCommand parseInput(String input) {
        if (input == null) {
            return new InvalidCommand("");
        }
        input = input.trim();

        switch (input) {
            case "n":
            case "s":
            case "e":
            case "w":
                return new MovementCommand(input);
            case "backpack":
            case "b":
                return new BackpackCommand();
        }

        // Everything after the first space is item name.
        int space = input.indexOf(" ");
        if (space != -1) {
            String command = input.substring(0, space);
            String itemName = input.substring(space + 1).trim();

            switch (command) {
                case "pickup":
                case "p":
                    return new PickupCommand(itemName);
                case "drop":
                case "d":
                    return new DropCommand(itemName);
            }
        }

        return new InvalidCommand(input);
    }

    /**
     * Builds a campus and its connections from a scanner reading campus data.
     *
     * @param s the scanner reading the campus data file
     * @return the populated campus, or null if reading or parsing fails
     */
    public static Campus setUpCampus(Scanner s) {
        try{
            Campus campus = new Campus(s.nextLine());
            if (!s.nextLine().equals("*****")) {
                throw new IllegalArgumentException("Campus separator not found.");
            }

            s.useDelimiter("\\*\\*\\*\\*\\*");
            // Strings -> function(String)

            String locationSection = s.next().trim();
            setUpLocations(campus, locationSection);

            String doorSection = s.next().trim();
            setUpDoors(campus, doorSection);

            String itemSection = s.next().trim();
            setUpItems(campus, itemSection);

            String npcSection = s.next().trim();
            setUpNPCs(campus, npcSection);

            String eventSection = s.next().trim();
            setUpEvents(campus, eventSection);
            return campus;
        } catch (Exception e) {
            System.out.println("Incorrect file format.");
            return null;
        }
    }

    /**
     * Parses location records and sets the first location as the starting point.
     *
     * @param campus the campus to populate
     * @param locationSection the location records, including the Locations: header
     * @throws IllegalArgumentException if the Locations: header is incorrect
     * @throws IndexOutOfBoundsException if a record is missing an expected newline
     */
    private static void setUpLocations(Campus campus, String locationSection) {
        String[] locationSubSection = locationSection.split("\\+\\+\\+");
        for (int i = 0; i < locationSubSection.length; i++) {
            String section = locationSubSection[i].trim();

            // Only the first record includes the Locations: header.
            if (i == 0) {
                int headerNewline = section.indexOf('\n');
                if (!section.substring(0, headerNewline).trim().equals("Locations:")) {
                    throw new IllegalArgumentException("Location header not found.");
                }
                section = section.substring(headerNewline + 1);
            }

            int newline = section.indexOf('\n');
            String name = section.substring(0, newline).trim();
            String description = section.substring(newline + 1).trim();

            Location location = new Location(name, description);
            campus.addLocation(location);
            if (i == 0) {
                campus.setStartingLocation(location);
            }
        }
    }

    /**
     * Parses door records and connects the campus's existing location objects.
     *
     * @param campus the campus containing the existing locations
     * @param doorSection the door records, including the Doors: header
     * @throws IllegalArgumentException if the header, direction, or location names are invalid
     * @throws IndexOutOfBoundsException if a record is missing an expected newline
     */
    private static void setUpDoors(Campus campus, String doorSection) {
        String[] doorSubSection = doorSection.split("\\+\\+\\+");
        for (int i = 0; i < doorSubSection.length; i++) {
            String doorRecord = doorSubSection[i].trim();

            // Only the first record includes the Doors: header.
            if (i == 0) {
                int headerNewline = doorRecord.indexOf('\n');
                if (!doorRecord.substring(0, headerNewline).trim().equals("Doors:")) {
                    throw new IllegalArgumentException("Door header not found.");
                }
                doorRecord = doorRecord.substring(headerNewline + 1);
            }

            int doorNewline = doorRecord.indexOf('\n');
            String leavingName = doorRecord.substring(0, doorNewline).trim();
            doorRecord = doorRecord.substring(doorNewline + 1);

            doorNewline = doorRecord.indexOf('\n');
            String direction = doorRecord.substring(0, doorNewline).trim();
            String enteringName = doorRecord.substring(doorNewline + 1).trim();

            Location leaving = campus.getLocation(leavingName);
            Location entering = campus.getLocation(enteringName);
            if (leaving == null || entering == null) {
                throw new IllegalArgumentException("Door location not found.");
            }
            if (!direction.equals("n") && !direction.equals("s") && !direction.equals("e") && !direction.equals("w")) {
                throw new IllegalArgumentException("Invalid door direction.");
            }

            Door door = new Door(direction, leaving, entering);
            leaving.addDoor(door);
        }
    }

    /**
     * Reads item name, location, and message records separated by +++.
     *
     * @param campus the campus whose locations receive the items
     * @param itemSection the item records, including the Items: header
     * @throws IllegalArgumentException if the header, record, or location is invalid
     * @throws IndexOutOfBoundsException if a record is missing an expected newline
     */
    private static void setUpItems(Campus campus, String itemSection) {
        if (itemSection.trim().equals("Items:")) {
            return;
        }
        String[] itemSubSection = itemSection.split("\\+\\+\\+");
        for (int i = 0; i < itemSubSection.length; i++) {
            String itemRecord = itemSubSection[i].trim();

            // Only the first record includes the Items: header.
            if (i == 0) {
                int headerNewline = itemRecord.indexOf('\n');
                if (!itemRecord.substring(0, headerNewline).trim().equals("Items:")) {
                    throw new IllegalArgumentException("Items header not found.");
                }
                itemRecord = itemRecord.substring(headerNewline + 1);
            }

            int itemNewline = itemRecord.indexOf('\n');
            String name = itemRecord.substring(0, itemNewline).trim();
            itemRecord = itemRecord.substring(itemNewline + 1);

            itemNewline = itemRecord.indexOf('\n');
            String locationName = itemRecord.substring(0, itemNewline).trim();
            String message = itemRecord.substring(itemNewline + 1).trim();

            if (name.isEmpty() || message.isEmpty() || message.indexOf('\n') != -1) {
                throw new IllegalArgumentException("Invalid item record.");
            }
            Location location = campus.getLocation(locationName);
            if (location == null) {
                throw new IllegalArgumentException("Item location not found.");
            }

            Item item = new Item(name, message);
            location.addItem(item);
        }
    }

    private static void setUpNPCs(Campus campus, String npcSection){
        //fulfill
    }

    private static void setUpEvents(Campus campus, String eventSection){
        //fulfill
    }
}
