public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }


    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("Uden foran IT universitet ");
        theater = new Room("I forlæsningssalen");
        pub = new Room("I fredagsbaren");
        lab = new Room("I computer lab");
        office = new Room("I administrationen");
        
        // initialise room exits
        outside.setExit("øst", theater);
        outside.setExit("syd", lab);
        outside.setExit("vest", pub);

        theater.setExit("vest", outside);

        pub.setExit("øst", outside);

        lab.setExit("nord", outside);
        lab.setExit("øst", office);

        office.setExit("vest", lab);

        currentRoom = outside;  // start game outside
    }


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
        System.out.println("Tak fordi du spilled med");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Velkommen til påskejagt på IT universitetet");
        System.out.println("Du skal rundt i de foreksllige rum, hver gang du kommer ind i et rum samler du et æg op.");
        System.out.println("Skriv 'hjælp' hvis du har brug for hjælp.");
        Paaske.printPaaske();
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("Jeg ved ikke hvad du mener...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("hjælp")) {
            printHelp();
        }
        else if (commandWord.equals("gå")) {
            goRoom(command);
            Paaske.pusPaaske();
            Paaske.printPaaske();
        }
        else if (commandWord.equals("slut")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }


    private void printHelp() 
    {
        System.out.println("Du skal finde en dør og finde påskeæg");
        System.out.println();
        System.out.println("Du kan skrive følgende:");
        parser.showCommands();
    }

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Gå hvor hen?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Der er ingen dør!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Slut hvad?");
            return false;
        }
        else {
            if(Paaske.getTæller() > 4){
            System.out.println("Tillykke du har samlet mere end 4 påskeæg og kan nu sluttet spillet og påskehygge");
            return true;  // signal that we want to quit
            }
            else {
                System.out.println("Du har desværre ikke samlet nok påskeæg til at kunne sluttet spillet endnu.");
                System.out.println("Du skal mindst have samlet 4 påskeæg");

                return false;
            }
        }
    }
}
