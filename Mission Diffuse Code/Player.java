import java.util.ArrayList;
import java.util.Stack;
/**
 * Class Player - a player in the game.
 * 
 * This class is part of the "Mission Diffuse" application. 
 * "Mission Diffuse" is a text based adventure game.
 * 
 * A "Player" represents one player of the game. Each player has a name, a starting room and 
 * a maximum amount of weight that they can carry. A player keeps track of which room it is in. 
 *
 * @author Ishika Arora
 * @version 5.12.22
 */
public class Player
{
    private String name;
    private Room currentRoom;
    //Private Room nextRoom; - HELP - WHY IS THIS COMMENTED?
    private int maxWeightCanCarry;
    private ArrayList<Item> items;
    private Stack<Room> roomPath;

    /**
     * Constructor for objects of class Player. HELP - DOES THIS NEED MORE DETAIL?
     * @param name Player's name.
     * @param startingRoom The room that the player starts the game off in.
     * @param maxWeightCanCarry The maximum amount of weight that the player can carry.
     */
    public Player(String name, Room startingRoom, int maxWeightCanCarry)
    {
        this.name = name;
        currentRoom = startingRoom;
        this.maxWeightCanCarry = maxWeightCanCarry;
        items = new ArrayList<>();
        roomPath = new Stack<>();
        roomPath.push(startingRoom);
    }
    
    /**
     * @return Player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The room the player is currently in. - HELP IS THIS CORRECT?
     */
    public Room getCurrentRoom() {
        return roomPath.peek();
    }
    
    /**
     * Keeps track of which room the player is in. HELP - IS THIS CORRECT?
     * @param currentRoom The room that the player is currently in.
     */
    public void setCurrentRoom(Room currentRoom)
    {
        roomPath.push(currentRoom);
        this.currentRoom = currentRoom;
    }
    
    /**
     * @return The stack of the rooms that the player has visited.
     */
    public Stack<Room> getRoomPath()
    {
        return roomPath;
    }

    /**
     * @return The maximum amount of weight that a player can carry.
     */
    public int getmaxWeightCanCarry() {
        return maxWeightCanCarry;
    }

    /**
     * Enables player to pick items.
     * If the total weight of all the items has exceeded the maximum amount of
     * weight that a player can carry, then an error message will be displayed.
     * @param item The item the player wants to pick.
     */
    public void pickItem(Item item) 
    {
        int totalItemWeight = calculateTotalItemWeight() + item.getItemWeight();
        if(totalItemWeight <= maxWeightCanCarry) 
        {
            items.add(item);
            System.out.println(item.getItemName() + " has been picked up.");
        }
        else
        {
            System.out.println("You can only carry maximum " + maxWeightCanCarry + " weight");
        }
    }
    
    /**
     * Enables player to drop items.
     * @param item The item the player wants to drop.
     */
    public void dropItem(Item item) 
    {
        items.remove(item);
    }

    /**
     * @return The item for the given item name. It returns null if the item is not found.
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
     * Prints inventory. The inventory displays all the current items that the player
     * is currently carrying and the total amount of weight the player currently holds.
     */
    public void printInventory()
    {   
        System.out.print("\nYou are carrying: " + "\n" + items.size() + " item(s) of total weight: " + calculateTotalItemWeight() );
        for(Item item : items)
        {
            System.out.print("\nItem Name: " + item.getItemName() + ", Item Weight: " + item.getItemWeight());
        }
    }

    /**
     * Calculates the total weight of all the items the player currently holds.
     * @return The total weight of all the items the player currently holds.
     */
    private int calculateTotalItemWeight() {
        int totalItemWeight = 0;   
        for(Item item : items)
        {
            totalItemWeight +=  item.getItemWeight();
        }
        return totalItemWeight;
    }
}


