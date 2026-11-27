import java.util.ArrayList;
import java.util.Scanner;

public class NPC {
    private String name;
    private String description;
    private ArrayList<String> dialogue;
    private boolean hasSpoken;
    private Item[] gift;
    private boolean giftAccepted;


    public NPC(String name, String description) {
        this.name = name;
        this.description = description;
        this.hasSpoken = false;
        this.giftAccepted = false;
        this.dialogue = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasSpoken() {
        return hasSpoken;
    }

    public void setHasSpoken() {
        this.hasSpoken = true;
    }
    public void addDialogueSection(String section) {
        dialogue.add(section);
    }
    public ArrayList<String> getDialogueSections() {
        return dialogue;
    }

    public String speak() {
        if (dialogue.isEmpty()) {
            return "";
        }
        return dialogue.remove(0);
    }

    public Item offerItem() {
        if (gift != null && !giftAccepted) {
            return gift[0];
        }
        return null;
    }

    public Item giveItem() {
        if (gift != null && giftAccepted) {
            return gift[0];
        }
        return null;
    }

    public void setGift(Item[] gift) {
        this.gift = gift;
    }

    public void addDialogue(String line) {
        dialogue.add(line);
    }
    public String haveConversation(Scanner input) {
        int sectionIndex = 0;
        String npcDialogue = dialogue.get(sectionIndex);
        sectionIndex++;

        while (sectionIndex + 3 < dialogue.size()) {
            System.out.println(npcDialogue);

            String[] choices = dialogue.get(sectionIndex).split("\n");
            String[] numbers = dialogue.get(sectionIndex + 1).split("\n");
            String[] responses = dialogue.get(sectionIndex + 2).split("\n");
            String[] instructions = dialogue.get(sectionIndex + 3).split("\n");

            for (String choice : choices) {
                System.out.println(choice.trim());
            }
            int answer = -1;

            while(answer == -1) {
                System.out.println("Enter a choice: ");
                String choice = input.nextLine().trim();

                int i = 0;

                for (String number: numbers) {
                    if (choice.equals(number.trim())) {
                        answer = i;
                    }
                    i++;
                }
                if (answer == -1) {
                    System.out.println("Not a valid choice, try again.");
                }
            }

            npcDialogue = responses[answer].trim();

            if (instructions[answer].trim().equalsIgnoreCase("END"))  {
                System.out.println(npcDialogue);
                return "Conversation ended";
            }
            else if (instructions[answer].trim().equalsIgnoreCase("CONTINUE")) {
                    sectionIndex += 4;
            } else {
                return "Conversation is invalid";
            }
        }
        return "Conversation ended";
    }
}
