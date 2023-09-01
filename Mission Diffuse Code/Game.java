import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Random;
/**
 *  This class is the main class of the "Mission Diffuse" application. 
 *  "Mission Diffuse" is a text based adventure game. The player of this
 *  game is a bomb disposal specialist who has been sent by their
 *  department leader to a hotel where there is a bomber hiding
 *  on the rooftop terrace. Throught the course of the game, the player will 
 *  encounter challenges and interact with characters. To win the game,
 *  the player must find the key to unlock the terrace and then diffuse the bomb.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates a player and all
 *  rooms, items and characters. It also creates the parser and starts the game.
 *  It also evaluates and executes the commands that the parser returns. 
 * 
 * @author  Ishika Arora
 * @version 5.12.22
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private Room currentRoom;
    private Room previousRoom;
    private Room outside, reception, restaurant, terrace, spa, pool, transporter;
    private Character cat;

    /**
     * Creates the game, rooms, players and initializes the parser.
     */
    public Game() 
    {
        createRooms();
        createItems();
        createCharacters();
        createPlayer();
        parser = new Parser();
    }

    /**
     * Create the player.
     */
    private void createPlayer() 
    {
        player = new Player("Bomb Diffuser", outside, 1000);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        outside = new Room("outside", "outside the main entrance of the hotel");
        reception = new Room("reception", "at the reception desk of the hotel");
        restaurant = new Room("restaurant", "downstairs in the hotel restaurant");
        terrace = new Room("terrace", "on the hotel's rooftop terrace");
        spa = new Room("spa", "in the hotel spa room");
        pool = new Room("pool", "in the hotel swimming pool room");
        transporter = new Room("transporter", "You have entered a magic room ! You will be transported into a random room.");

        // initialise room exits
        outside.setExit("inside", reception);

        reception.setExit("outside", outside);
        reception.setExit("left", spa);
        reception.setExit("up", terrace);
        reception.setExit("down", restaurant);
        reception.setExit("transporter", transporter);

        transporter.setExit("outside", outside);
        transporter.setExit("left", spa);
        transporter.setExit("down", restaurant);
        
        restaurant.setExit("up", reception);
        restaurant.setExit("transporter", transporter);

        spa.setExit("right", reception);
        spa.setExit("left", pool);
        spa.setExit("transporter", transporter);

        pool.setExit("right", spa);
        pool.setExit("transporter", transporter);

        currentRoom = outside;  // start game outside
    }

    /**
     * Create all the items and link them to rooms. 
     */
    private void createItems()
    {
        Item key, knife, water, table, poolWater, tile, chair, bandage;

        //create the items.
        key = new Item("Key", "The terrace key needed to unlock the door to the rooftop where the bomber is hiding.", 300, true);
        knife = new Item("Knife", "Sharp silver knife that may be useful for defence against the bomber.", 200, true);
        water = new Item("Water", "Water bottle for hydration.", 400, true);
        table = new Item("Table", "Long dining table used by guests during open hours of the hotel restaurant.", 7000, false);
        chair = new Item("Chair", "Large and heavy massage chair.", 2000, false);
        bandage = new Item("Bandage", "Bandage that can save the manager's leg.", 400, true);
        poolWater = new Item("PoolWater", "All the water in the pool.", 10000, false);
        tile = new Item("Tile", "Broken tile with sharp edges.", 300, false);

        //add the items to the rooms.
        restaurant.addItem(knife);
        restaurant.addItem(water);
        restaurant.addItem(table);

        spa.addItem(chair);
        spa.addItem(bandage);

        pool.addItem(key);
        pool.addItem(poolWater);
        pool.addItem(tile);
    }

    /**
     * Create all the characters and link them to rooms. 
     */
    private void createCharacters()
    {
        Character receptionist, manager, bomber;

        //create the characters.
        receptionist = new Character("Receptionist", 
            "'The terrace key is missing from reception. Please find the manager,there is a duplicate key with him.'");
        manager = new Character("Manager", 
            "'Ahh my leg is bleeding so much, please give me the bandage to save me from bleeding out!" 
            + "\nOh and sorry, I left the duplicate key in the pool room!'");
        bomber = new Character("Bomber", "'I didn't want to do this! I was forced! Please diffuse the bomb!'");
        cat = new Character("Cat");
        cat.setRandomMove(true);

        //add the characters to the rooms.
        reception.addCharacter(receptionist);
        reception.addCharacter(cat);
        spa.addCharacter(manager);
        terrace.addCharacter(bomber);
    }

    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Mission Diffuse");
        System.out.println();
        System.out.println("You are bomb disposal specialist who has been sent by your department ");
        System.out.println("leader to a hotel where there is a bomber hiding on the rooftop terrace. To win the game,");
        System.out.println("you must find the bomber and diffuse the bomb. However, the terrace can only be accessed");
        System.out.println("with a key. You must find this key first. Beware, you may encounter challenges along the ");
        System.out.println("way! Tip: If there is a character in a room, type “talk” to receive a clue.");
        System.out.println();
        System.out.println("Type 'help' if you need help.");
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equalsIgnoreCase("help")) {
            printHelp();
        }
        else if (commandWord.equalsIgnoreCase("go")) {
            goRoom(command);
        }
        else if (commandWord.equalsIgnoreCase("pick")) {
            pickItem(command);
        }
        else if (commandWord.equalsIgnoreCase("drop")) {
            dropItem(command);
        }
        else if (commandWord.equalsIgnoreCase("inventory")) {
            inventory(command);
        }
        else if (commandWord.equalsIgnoreCase("back")) {
            back();
        }
        else if (commandWord.equalsIgnoreCase("talk")) {
            talk();
        }
        else if (commandWord.equalsIgnoreCase("diffuse")) {
            if (diffuse(command)) {
                wantToQuit = quit(command);
            }
        }
        else if (commandWord.equalsIgnoreCase("quit")) {
            wantToQuit = quit(command);
        }
        randomCharacterMove(cat);

        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Keep on going and don't give up!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * Player can only enter the terrace if they have the key item,
     * otherwise error message is printed prompting them to find the key.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } 
        else 
        {
            if (nextRoom.getName().equals("terrace"))
            {
                if (player.getItem("key") == null) {
                    System.out.println("Please find and pick the key to enter the terrace."); 
                    return;
                }
            } else if (nextRoom.getName().equals("transporter")) {
                nextRoom = getRandomRoom(nextRoom);
                System.out.println("You have entered a magic room ! You will be transported to " + nextRoom.getName());
            }
            currentRoom = nextRoom;
            player.setCurrentRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());

        }
    }

    /**
     * Move the character randomly in the rooms. This method uses the Java.Utils Random method to 
     * randomly select an exit from the room to move the character to.
     */
    private void randomCharacterMove(Character character) {
        boolean randomMove = character.getRandomMove();
        HashMap<String, Room> exits = reception.getExits();

        Set<String> keySet = exits.keySet();
        List<String> keyList = new ArrayList<>(keySet);

        int size = keyList.size();
        int randIdx = new Random().nextInt(size);

        String roomName = keyList.get(randIdx);
        Room room = exits.get(roomName);
        if(room.getCharacter(character.getName()) == null) {
            room.addCharacter(character);
            System.out.println(character.getName() + " has moved to " + roomName);
        }
    }
    
    /**
     * Returns the room randomly from the exits. This method uses the Java.Utils Random method to 
     * randomly select an exit from the room.
     */
    private Room getRandomRoom(Room room) {
        HashMap<String, Room> exits = room.getExits();

        Set<String> keySet = exits.keySet();
        List<String> keyList = new ArrayList<>(keySet);

        int size = keyList.size();
        int randIdx = new Random().nextInt(size);

        String roomName = keyList.get(randIdx);
        return exits.get(roomName);
    }
    
    /**
     * Try to pick item. If the item name entered exists then
     * pick the item up, otherwise print an error message. 
     */
    private void pickItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which item to pick up...
            System.out.println("Which item?");
            return;
        }

        String itemName = command.getSecondWord();        

        // Try to pick item.
        Item item = player.getCurrentRoom().getItem(itemName);
        if (item == null)
        {
            System.out.println("Item doesn't exist in this room.");
        } 
        else if (item.isPickable())
        {
            player.pickItem(item); 
        }
        else {
            System.out.println("This item is not pickable.");
        }
    }

    /**
     * Try to drop item. If the item name entered exists then
     * drop the item up, otherwise print an error message. 
     * 
     * The drop method requires either a two word or three word command. 
     * 
     * For one word commands, it will print, "which item?"
     * 
     * For two word commands, the first word is expected to be the
     * command word drop,the second word is expected to be an item
     * e.g. drop bandage.
     * 
     * For three word command The first word is expected to be the
     * command word drop,the second word is expected to be a character
     * and the third word is expected to be an item e.g. drop manager bandage.
     * 
     * It will also print that the item has been dropped to a character.
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which item to drop...
            System.out.println("Which item?");
            return;
        }
        String itemName = command.getSecondWord();

        String characterName = "";
        if (command.hasThirdWord()) {
            characterName = command.getSecondWord();
            itemName = command.getThirdWord();
        }

        // Try to drop item.
        Item item = player.getItem(itemName);
        if (item == null)
        {
            System.out.println("You are not carrying this item.");
        } else {
            player.dropItem(item);
            String info = item.getItemName() + " has been dropped";
            if (player.getCurrentRoom().getCharacter(characterName) != null)
            {
                info += " to " + characterName;
            }
            System.out.println(info + ".");

        }
    }

    /**
     * Print the inventory.
     */
    private void inventory(Command command)
    {
        player.printInventory();
    }

    /**
     * Take the player to the previous rooms.
     */
    private void back(){
        Stack<Room> roomPath = player.getRoomPath();
        if(roomPath.empty()) {
            System.out.println("You are outside the main entrance of the hotel," +
                " there is no previous room.");
        } else {
            roomPath.pop();
            currentRoom = roomPath.peek();
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * Enables player to interact with game characters.
     */
    private void talk() {
        List<Character> characters = player.getCurrentRoom().getCharacters();
        if (characters.size() > 0)
            System.out.println(characters.get(0).getDialogue());
    }

    /**
     * Enables player to diffues the bomb and win the game.
     */
    private boolean diffuse(Command command) {
        if (player.getCurrentRoom().getName().equalsIgnoreCase("terrace")) 
        {
            System.out.println("Congratulations, you have succesfully diffused the bomb and won the game!");
            return true;
        } else {
            System.out.println("Bomb is at the terrace, go there to diffuse the it!");
            return false;
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
