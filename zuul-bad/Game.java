import java.util.Random;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private int bomb;
    private CommandWords cow;
    private Items items;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, bomb_trollroom;
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub, and there is a ");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        bomb_trollroom = new Room("still, sweat dripping from ur orphusis, there is a fucking bomb in this room :(");

        // initialise room exits
        outside.setExits(null, theater, lab, pub, null);
        theater.setExits(null, null, null, outside, null);
        pub.setExits(null, outside, null, null, null);
        lab.setExits(outside, office, null, null, null);
        office.setExits(null, null, null, lab, bomb_trollroom);
        bomb_trollroom.setExits(null, null, null, null, null);
        //set Items
        outside.setItem(new Items("Sword", "3kg"));
        pub.setItem(new Items("Beer", "500g"));
        lab.setItem(new Items("Laptop", "2kg"));
        office.setItem(new Items("Key", "50g"));
        bomb_trollroom.setItem(new Items("Detonator", "250g"));
        //add Items to rooms
        theater.addItem(new Items("sword", "3kg"));
        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printLocationInfo();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printLocationInfo()
    {
        System.out.println(currentRoom.getLongDescription());
        System.out.print("Exits: ");

        if(currentRoom.getExits("north") != null) {
            System.out.print("north ");
        }
        if(currentRoom.getExits("east") != null) {
            System.out.print("east ");
        }
        if(currentRoom.getExits("south") != null) {
            System.out.print("south ");
        }
        if(currentRoom.getExits("west") != null) {
            System.out.print("west ");
        }
        if(currentRoom.getExits("celler") != null) {
            System.out.print("celler ");
        }
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
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);

        } else if (commandWord.equals("look")) {
            look();

        } else if (commandWord.equals("eat")) {

            System.out.println("you grabbed some dirt from the ground and ate it");
        }
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
        System.out.println("You are lost. You are alone. you are naked and afraid. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        int shit = command.rolldatshit();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.getExits("north");
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.getExits("east");
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.getExits("south");        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.getExits("west");
        }
        if(direction.equals("celler")) {
            nextRoom = currentRoom.getExits("celler");
        }
        if (nextRoom == null) {
            System.out.println("There is no door! there is no hope!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            System.out.print("Exits: ");
            if(currentRoom.getExits("north") != null) {
                System.out.print("north ");
            }
            if(currentRoom.getExits("east") != null) {
                System.out.print("east ");
            }
            if(currentRoom.getExits("south") != null) {
                System.out.print("south ");
            }
            if(currentRoom.getExits("west") != null) {
                System.out.print("west ");
            }
            if(currentRoom.getExits("celler") != null) {
                System.out.print("celler ");
            }
            if(currentRoom.getExits("celler") == null) {
            }
            System.out.println();
        }
    }

    public void bomb(int shit) {
        Random random = new Random();
        int r = random.nextInt(1, 11);
        if (shit != r) {
            System.out.print("BOOOOM");
        }
        else {
            System.out.print("Counter terrorist win");
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

    private void look() {
        System.out.print(currentRoom.getLongDescription()); 
    }

    private void useItemNow(Command command) {

    }
}
