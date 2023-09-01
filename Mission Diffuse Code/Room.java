import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
/**
 * Class Room - a room in the game.
 *
 * This class is part of the "Mission Diffuse" application. 
 * "Mission Diffuse" is a text based adventure game. 
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits. For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Ishika Arora
 * @version 5.12.22
 */

public class Room 
{
    private String name;
    private String description;
    private HashMap<String, Room> exits;  // stores exits of this room.
    private ArrayList<Item> items;
    private ArrayList<Character> characters;

    /**
     * Create a room with a name and description. Initially, it has
     * no exits.
     * The description is something like "a kitchen" or "an open court yard". 
     * @param name The room's name.
     * @param description The room's description.
     */
    public Room(String name, String description) 
    {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        characters = new ArrayList<>();
    }

    /**
     * @return The room's name.
     */
    public String getName() {
        return name;    
    }

    /**
     * @return The short description of the room.
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Group information on all the items.
     * @return All the information of all the items.
     */
    public String getItemsInfo() {
        String itemsInfo = "";
        for(Item item : items) {
            itemsInfo += item.getItemInfo() + "\n";
        }
        return itemsInfo;
    }

    /**
     * Group information on all the characters.
     * @return All the information of all the characters.
     */
    public String getCharactersInfo() {
        String characterInfo = "";
        for(Character character : characters) {
            characterInfo += character.getName() + " ";
        }
        return characterInfo;
    }

    /**
     * Return a description of the room, the exits of the room, the items and characters of the room.
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        String longDescription = "";
        if (getItemsInfo().equals("")) {
            longDescription = "\nYou are " + description + ".\n" + getExitString();
        } else {
            longDescription =  "\nYou are " + description + ".\n" + getExitString() + "\n\n" 
            + "Items in this room:" + getItemsInfo();
        }

        if (!getCharactersInfo().equals("")) {
            longDescription += "\nCharacters in the room: " + getCharactersInfo() + "\nType talk to interact.";
        }
        return longDescription;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * @return Returns a map of possible exits of the room
     */
    public HashMap<String, Room> getExits() {
         return exits;
    }
    
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Finds the item for the given item name. It returns null if the item doesn't exist.
     * @param itemName The name of the item.
     * @return The item object for the given item name. It returns null if the item doesn't exist.
     */
    public Item getItem(String itemName) {
        for(Item item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Add a new item to the room.
     * @param item An instance of the Item class.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * @return The list of characters in the room.
     */
    public List<Character> getCharacters() 
    {   
        return characters;
    }
    
    /**
     * @return The character for the given character name. If the character does not exist, null will be returned.
     */
    public Character getCharacter(String name) 
    {   
            for(Character character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }
    
    /**
     * Add a new character to the room.
     * @param character An instance of the Character class.
     */
    public void addCharacter(Character character) {    
        characters.add(character);
    }
}
