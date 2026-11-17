import java.io.File;
import java.io.PrintWriter;

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
     * static upon loading will return an instance of a visitorProfile
     *
     * @param fileName
     * @return VisitorProfile
     */
    public static VisitorProfile load(String fileName){
        return null;
    }

    private String formatString(){
        return "" +
                this.regularPoints + "\n" +
                this.enjoymentPoints + "\n" +
                this.distancePoints + "\n" +
                this.tourStatus.getCurrentLocation().getName() + "\n" +
                // backpack sample format
                // [ , , , ] use , delimiter after next line
                this.tourStatus.getBackpackItemsStringified() + "\n";
     }
}
