
/**
 * The item class manages all items that can be stored in the rooms.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String description;
    private String name;
    
    /**
     * Constructor for objects of class Item
     */
    public Item( String name,String description)
    {
    this.description = description;
    this.name = name;
    }
    
     /**
     * It gives the name of an item
     */   
        public String getName() {
        return name;
    }

    /**
     * It gives the description of an item
     */ 
    public String getDescription() {
        return description;
    }

    /**
     * It gives both name followed by the description.
     */
    public String getItemInfo() {
        return name + ": " + description;
    }

}
