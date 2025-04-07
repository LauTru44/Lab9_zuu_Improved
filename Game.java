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
/* PART 1
 * 1)
 * - It's a game/text simulation that takes you to different rooms in a an university
 * - help, quit, go
 * - help, tells you the possible commands
 *   quit, stops the game
 *   go, it takes you to the doom that you decide to go to
 * - There's 4 rooms
 * PART 2
 * 19) it suggests to divide the program in three parts:
 * - model: manages the data and rules to follow
 * - controller: connects the model and view, processes user input.
 * - view: its the interface shown to the user. 
 * 
 * 21) The information should be present as text, the class game
 * has the string describing the item when initialized, the class 
 * prints it, but the method getlongdescription in class room returns it
 * It's suppose to work since whe did something similar with them room objects before.
 * 
 * 22) I added a hashmap to contain multiple items of a room
 * 
 * 23) I added the command back and the method goback
 * 
 * 24) It works at expected,if I type "back" and I am in the lab and then the
 * office, the player never returns to the outside, theres a loop between lab and office.
 * 
 * 25) It doesnt follow the original path taken, it can create a loop between rooms.
 * 
 * 26) A stack in Java is like a pile of plates, you can only add or remove the top plate.
 * 
 * PART 3
 * 27) player movement between rooms, right exits and interaction functionality.
 * 28) Creation of the player class.
 * 
 * 29) implemented take and drop commands
 * 30-33) working on it
 * 
 * 
 */
public class Game 
{
    private Parser parser;

    private Room previousRoom;
    private Player player;
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
        Room outside, theater, pub, lab, office;
        Item mirror,laptop;
        
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        mirror= new Item("mirror","You can see your reflection");
        laptop= new Item("laptop","you can do lots of things with it");
         
        player = new Player("Lau", outside);
        
        /**Exercise 20: display of an item
        mirror= new Item("mirror","You can see your reflection");
        lab.setItem(mirror); the room "lab" has one "mirror" in the field "item"
        */
       
        // initialise room exits
        outside.setExit("east", theater);  
        outside.setExit("south", lab);  
        outside.setExit("west", pub);  

        theater.setExit("west", outside);  

        pub.setExit("east", outside);  

        lab.setExit("north", outside);  
        lab.setExit("east", office);  

        office.setExit("west", lab);  
    
        /*--------items-----------------*/
        //exercise 22
        lab.addItem(mirror);
        lab.addItem(laptop);

    }

    /**
     *  Main play routine.  Loops until end of play.
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
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }
    
    public void printLocationInfo(){
        System.out.println(player.getCurrentRoom().getLongDescription());        
    }
    
    private void goBack() {
    if (previousRoom != null) {
        Room temp = player.getCurrentRoom();
        player.setCurrentRoom(previousRoom);
        previousRoom = temp;

        printLocationInfo();
    } else {
        System.out.println("You can't go back!");
    }
    }
    
    private void takeItem(Command command) {
        
    if (!command.hasSecondWord()) {
        System.out.println("Take what?"); //no second word, no item to specify
        return;
    }

    String itemName = command.getSecondWord();
    boolean success = player.takeItem(itemName); //checks if everything works fine
    if (success) {
        System.out.println("You took the " + itemName + ".");
    } else {
        System.out.println("You can't take that.");
    }
    }

    private void dropItem() {
    boolean success = player.dropItem();
    if (success) {
        System.out.println("You dropped the item.");
    } else {
        System.out.println("You're not carrying anything.");
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
        }
        else if (commandWord.equals("look")) {
           System.out.println("Looking around..."); 
        }
        else if (commandWord.equals("eat")) {
           System.out.println("You have eaten now and you are not hungry any more."); 
        }        
        else if (commandWord.equals("back")) { //Exercise 23
        goBack();       
        }
        else if (commandWord.equals("take")) {
        takeItem(command);
        }
        else if (commandWord.equals("drop")) {
        dropItem();
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showAllCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
    if (!command.hasSecondWord()) {
        System.out.println("Go where?");
        return;
    }

    String direction = command.getSecondWord();

    // Use the player's current room to find the next room
    Room current = player.getCurrentRoom();
    Room nextRoom = current.getExit(direction);

    if (nextRoom == null) {
        System.out.println("There is no door!");
    } else {
        // Optional: if you're still using "back", update previousRoom
        previousRoom = current;

        // Move the player to the new room
        player.setCurrentRoom(nextRoom);

        // Show where they are now
        printLocationInfo();
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
