import java.util.HashMap;
import java.util.Set;
import java.util.Random;

public class Items {
    private Items sten;  
    private Items hund;  

    public HashMap<Items, String> weapons;

    public Items () {
        weapons = new HashMap<>();
    }

    private void makeItem(Items items) {
        weapons.put(sten, "20kg");
        weapons.put(hund, "-1kg");
    }

    public void getInventory(String key) {

    }

    public void useItem() {

    }

}
