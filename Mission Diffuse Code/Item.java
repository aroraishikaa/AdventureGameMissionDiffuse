
/**
 * Class Item - an item in the game.
 * 
 * This class is part of the "Mission Diffuse" application. 
 * "Mission Diffuse" is a text based adventure game.
 * 
 * A "Item" represents one item of the game. Each item has a name,
 * description, weight and whether it can be picked or not.
 *
 * @author Ishika Arora
 * @version 5.12.22
 */
public class Item
{
    private String itemName = "";
    private String itemDescription;
    private int itemWeight;
    private boolean pickable;

    /**
     * Constructor for objects of class Item.
     * @param itemName Item's name.
     * @param itemDescription Item's description.
     * @param itemWeight Item's weight.
     * @param canBePicked Whether the item can be picked up or not.
     */
    public Item(String itemName, String itemDescription, int itemWeight, boolean pickable)
    {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
        this.pickable = pickable;
    }

    /**
     * @return The name of the item.  
     */
    public String getItemName()
    {
        return itemName;
    }

    /**
     * @return A description of the item.
     */
    public String getItemDescription()
    {
        return itemDescription;
    }

    /**
     * @return The weight of the item.
     */
    public int getItemWeight()
    {
        return itemWeight;
    }
    
    /**
     * @return True it the item can be pick otherwise false.
     */
    public boolean isPickable()
    {
        return pickable;
    }

    /**
     * Group all the information about the item.
     * @return All the information about the item.
     */
    public String getItemInfo() {
        if(itemName.equals("")) {
            return "";
        }
        else {
            String description = "\n"+ "Name: " + itemName + "\n"+ "Description: " + itemDescription + "\n" + "Weight: " + itemWeight + "\n";
            if (pickable) {
                return description + "This item can be picked up.";
            }
            else {
                return description + "This item can not be picked up.";
            }
        }
    }
}
