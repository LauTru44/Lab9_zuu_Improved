
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
/*
 * 
 * 
 */
public class Player //Exercise 28
{
    private Room currentRoom;
    private String name;
    private Item inventoryItem;
    /**
     * Constructor for objects of class Player
     */
    public Player(String name,Room currentRoom)
    {
        this.name = name;
        this.currentRoom = currentRoom;
        this.inventoryItem = null;
    }
    
    public String getName(){
        return this.name;    
    }
    
    public Room getCurrentRoom(){
        return this.currentRoom;
    }

    public void setCurrentRoom(Room room){
        currentRoom = room;
    }
    
    public boolean takeItem(String itemName) {
        if (inventoryItem != null) {
            return false;
        }

        itemName = itemName.toLowerCase();
        Item item = currentRoom.removeItem(itemName);
        if (item == null) {
            return false;
        }

        inventoryItem = item;
        return true;
    }
      
    public boolean dropItem() {
    if (inventoryItem != null) {
        currentRoom.addItem(inventoryItem);
        inventoryItem = null;
        return true;
    }
    return false;
    }    
}
    
    
    

