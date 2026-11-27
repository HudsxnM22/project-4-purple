import java.util.Scanner;

public class SpeakCommand implements UserInputCommand {
    private String npcName;
    private Scanner input;

    public SpeakCommand(String npcName, Scanner input) {
        this.npcName = npcName;
        this.input = input;
    }
    @Override
    public String carryOut() {
        TourStatus tour = TourStatus.getInstance();
        Location location = tour.getCurrentLocation();

        if (location == null || location.getNPC() == null) {
            return "An NPC is not here";
        }
        NPC npc = location.getNPC();
        if (!npc.getName().equalsIgnoreCase(npcName)) {
            return "NPC is not at this location";
        }
        npc.setHasSpoken();
        return npc.haveConversation(input);
    }
}
