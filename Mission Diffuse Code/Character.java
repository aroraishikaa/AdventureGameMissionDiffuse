import java.util.ArrayList;
import java.util.Stack;
/**
 * Class Character - a character in the game.
 * 
 * This class is part of the "Mission Diffuse" application. 
 * "Mission Diffuse" is a text based adventure game.
 * 
 * A "Character" represents one character of the game. Each character has a name and dialogue.
 *
 * @author Ishika Arora
 * @version 5.12.22
 */
public class Character
{
    private String name;  // Character's name.
    private Stack<String> dialogues;
    private boolean randomMove;
    /**
     * Constructor for objects of class Character.
     * @param name Character's name.
     */
    public Character(String name)
    {
        this(name, "");
    }

    /**
     * Constructor for objects of class Character.
     * @param name Character's name.
     * @param name Character's dialogue.
     */
    public Character(String name, String dialogue)
    {
        this.name = name;
        this.dialogues = new Stack<>();
        dialogues.push(dialogue);
    }

    /**
     * @return Return character's name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return A dialogue for the character to interact with the player.
     * It will return an empty string if the character doesn't have any dialogue.
     */
    public String getDialogue()
    {
        if (dialogues.empty())
        {
            return "";
        }
        return dialogues.peek();
    }

    /**
     * Adds a dialogue for a character to interact with a player.
     */
    public void addDialogue(String dialogue)
    {
        dialogues.push(dialogue);
    }
    
    /**
     * @return true if the a character can randomly move in the rooms otherwise returns false.
     */
    public boolean getRandomMove() {
        return randomMove;
    }
    
    /**
     * Sets if the character can randomly move in the rooms
     * @param name randomMove.
     */
    public void setRandomMove(boolean randomMove) {
        this.randomMove = randomMove;
    }
}
