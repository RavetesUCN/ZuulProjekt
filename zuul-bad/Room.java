import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

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
    private Room cellerExit;
    private Items item;
    private List<Items> items;
    
    private HashMap<String, Room> exits;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        this.items = new ArrayList<>();
    }
    
    public void addItem(Items item){
        items.add(item);
    }
    public List<Items> getItems(){
        return items;
    }
    public Room getExits(String direction) {
        return exits.get(direction);
    }
     public String getItemsString() {
        if (items.isEmpty()) {
            return "No items here.";
        }
        String result = "Items here: ";
        for (Items item : items) {
             result += item.getName() + " " + item.getWeight();
        }
        return result;
    }
    
    public String getExitsString() {
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        
        return returnString;
    }

    public void setExits(Room north, Room east, Room south, Room west, Room celler) 
    {
        if(north != null) {
            exits.put("north", north);
        }
        if(east != null) {
            exits.put("east", east);
        }
        if(south != null) {
            exits.put("south", south);
        }
        if(west != null) {
            exits.put("west", west);
        }
        if(celler != null) {
            exits.put("celler", celler);
        }
    }
    public void setItem(Items item){
        this.item = item;
    }
    public Items getItem(){
        return this.item;
    }
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitsString() + "\n" + getItemsString();
    }
    

}
