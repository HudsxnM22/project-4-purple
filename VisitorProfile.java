public class VisitorProfile {

    private int regularPoints;
    private int enjoymentPoints;
    private int distancePoints;
    private TourStatus tourStatus;

    /**
     * Public vistor Profile
     */
    public VisitorProfile(TourStatus tourStatus){
        this.tourStatus = tourStatus;
    }

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

    }

    /**
     * static upon loading will return an instance of a visitorProfile
     *
     * @param fileName
     * @return VisitorProfile
     */
    public static VisitorProfile load(String fileName){

    }
}
