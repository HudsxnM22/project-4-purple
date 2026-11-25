import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Hashtable;

public class VisitorProfile {

    private int regularPoints = 0;
    private int enjoymentPoints = 0;
    private int distancePoints = 0;
    private TourStatus tourStatus = TourStatus.getInstance();

    /**
     * Public vistor Profile
     */
    public VisitorProfile(){}

    /**
     * adds regular score points
     *
     * @param points
     */
    public void addPoints(int points){
        this.regularPoints = this.regularPoints + points;
    }

    /**
     * adds enjoyment score points
     *
     * @param points
     */
    public void addEnjoyment(int points){
        this.enjoymentPoints = this.enjoymentPoints + points;
    }

    /**
     * adds distance score points
     *
     * @param points
     */
    public void addDistance(int points){
        this.distancePoints = this.distancePoints + points;
        this.calulateDistancePoints();
    }

    private void calulateDistancePoints(){
        this.enjoymentPoints = this.enjoymentPoints + 5;
        this.regularPoints = this.regularPoints + 2;
    }

    /**
     * enjoyment + distance + regular points
     *
     * @return Total points
     */
    public int getTotalPoints(){
        return this.distancePoints + this.regularPoints + this.enjoymentPoints;
    }

    /**
     * Saves the visitor profile to the file name passed in as arg
     *
     * @param fileName
     */
    public void save(String fileName){
        try {
            File file = new File(fileName);
            PrintWriter pw = new PrintWriter(file);
            String savedFormattedString = this.formatString();
            pw.print(savedFormattedString);
            pw.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restores a profile after the campus data has been loaded.
     * @param fileName the saved profile filename
     * @return the restored profile, or null if the save is invalid
     */
    public static VisitorProfile load(String fileName){
        try (Scanner scanner = new Scanner(new File(fileName))) {
            VisitorProfile profile = new VisitorProfile();
            TourStatus tour = TourStatus.getInstance();
            Campus campus = tour.getCampus();
            //get points and current location.
            profile.regularPoints = Integer.parseInt(scanner.nextLine().trim());
            profile.enjoymentPoints = Integer.parseInt(scanner.nextLine().trim());
            profile.distancePoints = Integer.parseInt(scanner.nextLine().trim());
            Location current = campus.getLocation(scanner.nextLine().trim());
            if (current == null) {
                throw new IllegalArgumentException("Saved location not found.");
            }


            Hashtable<String, Item> allItems = new Hashtable<>();
            for (Location location : campus.getAllLocations().values()) {
                for (Item item : location.getItems()) {
                    allItems.put(item.getName(), item);
                }
            }

            ArrayList<String> usedNames = new ArrayList<>();
            ArrayList<Item> savedBackpack = parseItems(scanner.nextLine().trim(), allItems, usedNames);
            Hashtable<String, ArrayList<Item>> savedLocations = new Hashtable<>();

            if (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.startsWith("[") || !line.endsWith("]")) {
                    throw new IllegalArgumentException("Invalid saved locations.");
                }
                String remaining = line.substring(1, line.length() - 1);
                while (!remaining.isEmpty()) {
                    int divider = remaining.indexOf("|[");
                    int end = remaining.indexOf("]");
                    if (divider < 0 || end < divider) {
                        throw new IllegalArgumentException("Invalid location record.");
                    }
                    String locationName = remaining.substring(0, divider);

                    String itemList = remaining.substring(divider + 1, end + 1);
                    savedLocations.put(locationName, parseItems(itemList, allItems, usedNames));
                    remaining = remaining.substring(end + 1);
                    if (!remaining.isEmpty()) {
                        // set remaining to the location name one index post ,
                        remaining = remaining.substring(1);
                    }
                }
            }

            // Change the tour post processing.
            for (Location location : campus.getAllLocations().values()) {
                location.clearItems();
                for (Item item : savedLocations.get(location.getName())) {
                    location.addItem(item);
                }
            }
            tour.clearBackpack();
            for (Item item : savedBackpack) {
                tour.addToBackpack(item);
            }
            tour.setCurrentLocation(current);
            current.setHaveVisited(true);
            return profile;
        } catch (Exception e) {
            System.out.println("Could not load profile: " + e.getMessage());
            return null;
        }
    }

    /**
     * Looks up the items listed in a saved bracketed list.
     * @param text the comma-separated item names inside brackets
     * @param allItems the original items indexed by name
     * @param usedNames names already assigned to a backpack or location
     * @return the original item objects in the saved order
     * @throws IllegalArgumentException if a name is unknown or repeated
     */
    private static ArrayList<Item> parseItems(String text, Hashtable<String, Item> allItems, ArrayList<String> usedNames) {
        if (!text.startsWith("[") || !text.endsWith("]")) {
            throw new IllegalArgumentException("Invalid saved item list.");
        }
        ArrayList<Item> result = new ArrayList<>();
        String names = text.substring(1, text.length() - 1);
        if (!names.isEmpty()) {
            String[] parts = names.split(",");
            for (String name : parts) {
                name = name.trim();
                Item item = allItems.get(name);
                if (item == null || usedNames.contains(name)) {
                    throw new IllegalArgumentException("Unknown or repeated item: " + name);
                }
                result.add(item);
                //prevent item duplication when you have an item in backpack and spawned into a location.
                usedNames.add(name);
            }
        }
        return result;
    }

    private String formatString(){
        return "" +
                this.regularPoints + "\n" +
                this.enjoymentPoints + "\n" +
                this.distancePoints + "\n" +
                this.tourStatus.getCurrentLocation().getName() + "\n" +
                // backpack sample format
                // [ , , , ] use , delimiter after next line
                this.tourStatus.getBackpackItemsStringified() + "\n" +
                this.tourStatus.getAllLocationsAndTheirItems() + "\n";
     }
}
