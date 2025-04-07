import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private HashMap <String,Room> exits;
    
    private HashMap <String,Item> items;
    private Item item;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new HashMap<>();
    }
    
    public Room getExit(String direction){
        Room nextroom = exits.get(direction);
        return nextroom;    
    }
    
    /** * Return a description of the room’s exits, 
    * for example, "Exits: north west" . 
    * @return A description of the available exits. 
    */
    public String getLongDescription(){
    /*Exercise 20     
    String itemInfo = "";
        if (item != null) {
        itemInfo = "\nItem: " + item.getItemInfo();
        }
    */    
    return "You are " + description + "\n "+ getExitString()+ getItemString(); 
    }
    
    private String getExitString(){
    String exitString= "Exits: ";   
    for (String direction : exits.keySet()) {
    exitString +=  " "+ direction;
    }     
    return exitString;        
    }
    
    private String getItemString(){
        
    if (items.isEmpty()) {
        return "";
    }

    String itemString = "\nItems in the room:";
    for (Item item : items.values()) {
        itemString += "\n- " + item.getItemInfo();
    }
    return itemString;
    }
    
    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExit(String direction,Room neighbor) 
    {
         if (neighbor != null) { 
            exits.put(direction, neighbor);
        }
    }
    
    public void addItem(Item item) { //exercise 22
    items.put(item.getName().toLowerCase(), item);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
 
    /**
     * Exercise 20
     * Puts the item in the field "item" of the room.
     
    public void setItem(Item item) {
    this.item = item;
    }
    */
    
    /**
     * @return The item of the room.
     */
    public Item getItem()
    {
        return item;
    }    
    
    public Item removeItem(String itemName) {
     
    return items.remove(itemName.toLowerCase());
    
    }

}
