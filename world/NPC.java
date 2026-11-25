import java.util.ArrayList;

public class NPC {
    private String name;
    private ArrayList<String> dialogue;
    private boolean hasSpoken;
    private Item[] gift;
    private boolean giftAccepted;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private int questionIndex;
    private int quizEnjoymentPoints;

    public NPC(String name) {
        this.name = name;
        this.hasSpoken = false;
        this.giftAccepted = false;
        this.questionIndex = 0;
        this.quizEnjoymentPoints = 0;
    }

    public String speak() {
        return null;
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
    public int answerQuiz(String answer) {
        return 0;
    }
}
