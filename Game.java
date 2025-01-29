import java.util.Stack;
import java.util.HashMap;

public class Game 
{
    private Stack<Room> previousRooms; // Pilha para armazenar as salas anteriores

    private Parser parser;
    private Room currentRoom;
    private Player player;

    private Room zaynFarm;
    private Room louisHome;
    private Room dublinPark;
    private Room liamHome;
    private Room harryParty;

    private boolean hasZayn = false;
    private boolean hasLouis = false;
    private boolean hasNiall = false;
    private boolean hasLiam = false;
    private boolean hasHarry = false;

    private boolean hasTakeMeHome = true;  // O álbum Take Me Home está no início
    private boolean hasMidnightMemories = false;
    private boolean hasFour = false;
    private boolean hasUpAllNight = false;
    private boolean hasMitam = false;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRooms = new Stack<>();
        player = new Player("Você", new Room("no seu quarto"));
    }

    private void createRooms() {
        // Quarto do protagonista
        Room bedroom = new Room("no quarto do protagonista");

        // Fazenda do Zayn
        zaynFarm = new Room("na fazenda do Zayn");
        Room barn = new Room("no celeiro da fazenda");
        Room field = new Room("no campo ao lado da fazenda");
        Room house = new Room("dentro da casa do Zayn");
        Room zaynBedroom = new Room("no quarto do Zayn"); 

        // Casa de Louis em Doncaster
        louisHome = new Room("na casa de Louis em Doncaster");
        Room livingRoom = new Room("na sala de estar de Louis");
        Room kitchen = new Room("na cozinha da casa de Louis");
        Room backyard = new Room("no quintal da casa de Louis");
        Room bathroom = new Room("no banheiro do Louis");

        // Parque em Dublin com Niall
        dublinPark = new Room("em um parque em Dublin - Irlanda");
        Room lake = new Room("perto do lago do parque");
        Room forest = new Room("na trilha da floresta do parque");
        Room playground = new Room("no playground do parque");
        Room cave = new Room("Uma entrada escura entre as árvores. Parece assustador, mas você sente que algo importante está lá dentro");

        // Casa de Liam em Londres
        liamHome = new Room("na casa de Liam em Londres");
        Room studio = new Room("no estúdio de música do Liam");
        Room balcony = new Room("na varanda da casa de Liam");
        Room garden = new Room("no jardim da casa de Liam");
        Room library = new Room("Uma sala silenciosa cheia de livros e partituras.");
        

        // Festa do Harry em LA
        harryParty = new Room("em uma festa em Los Angeles");
        Room danceFloor = new Room("na pista de dança da festa");
        Room bar = new Room("no bar da festa");
        Room lounge = new Room("no lounge VIP da festa");
        Room rooftop = new Room("O terraço da festa é iluminado por luzes decorativas. De lá, você pode ver toda a cidade. Harry talvez tenha deixado algo aqui");

        // Conexões internas da fazenda do Zayn
        zaynFarm.setExit("north", barn);
        zaynFarm.setExit("east", field);
        zaynFarm.setExit("south", house);
        zaynFarm.setExit("west", zaynBedroom);

        // Conexões internas da casa de Louis
        louisHome.setExit("north", livingRoom);
        louisHome.setExit("east", kitchen);
        louisHome.setExit("south", backyard);
        louisHome.setExit("west", bathroom);

        // Conexões internas do parque de Niall
        dublinPark.setExit("north", lake);
        dublinPark.setExit("east", forest);
        dublinPark.setExit("south", playground);
        dublinPark.setExit("west", cave);

        // Conexões internas da casa de Liam
        liamHome.setExit("north", studio);
        liamHome.setExit("east", balcony);
        liamHome.setExit("south", garden);
        liamHome.setExit("west", library);

        // Conexões internas da festa do Harry
        harryParty.setExit("north", danceFloor);
        harryParty.setExit("east", bar);
        harryParty.setExit("south", lounge);
        harryParty.setExit("west", rooftop);

        // Define o quarto como o ponto inicial
        currentRoom = bedroom;

        //álbuns
        Item upAllNight = new Item("Álbum Up All Night - One Direction", 5);
        Item takeMeHome = new Item("Álbum Take Me Home - One Direction", 5);
        Item midnightMemories = new Item("Álbum Midnight Memories - One Direction", 5);
        Item four = new Item("Álbum Four - One Direction", 5);
        Item mitam = new Item("Álbum Made In the AM - One Direction", 5);

        //itens do quarto
        Item poster = new Item("Pôster de One Direction", 5);
        Item vinyl = new Item("Vinil de Made in the A.M.", 10);
        Item tickets = new Item("Ingressos para o show em Londres", 2);
        Item book = new Item("Livro de Memórias da Banda", 3);
        Item shirt = new Item("Camisa de Turnê 2014", 4);

        // Adiciona os itens ao quarto
        bedroom.addItem("poster", poster);
        bedroom.addItem("vinyl", vinyl);
        bedroom.addItem("tickets", tickets);
        bedroom.addItem("book", book);
        bedroom.addItem("shirt", shirt);

        // Criação do item mochila
        Item mochila = new Item("Mochila", 1);  // A mochila tem peso 1

        // Adiciona a mochila ao quarto
        bedroom.addItem("mochila", mochila);

        Item zaynItem = new Item("Zayn", 1); 
        Item louisItem = new Item("Louis", 1);
        Item niallItem = new Item("Niall", 1);
        Item liamItem = new Item("Liam", 1);
        Item harryItem = new Item("Harry", 1);

        house.addItem("zayn", zaynItem);       // Zayn está na casa da fazenda
        livingRoom.addItem("louis", louisItem); // Louis está na sala de estar
        lake.addItem("niall", niallItem);       // Niall está no lago do parque
        studio.addItem("liam", liamItem);       // Liam está no estúdio
        danceFloor.addItem("harry", harryItem); // Harry está na pista de dança

        player.setCurrentRoom(bedroom);
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
        System.out.println("Goodbye! Hope you reunite the band next time."); 
    }

    /**
     * Print the current location and exits.
     */
    private void printLocationInfo() {
        System.out.println(player.getCurrentRoom().getDescription());
        System.out.println(player.getCurrentRoom().getExitString());
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    private void printWelcome() {
        System.out.println("Bem-vindo ao 'Where We Are'!");
        System.out.println("Uma aventura inspirada na história de uma das maiores bandas do mundo: One Direction!");
        System.out.println("Os membros da banda estão espalhados pelo mundo, e cabe a você reuni-los para o grande comeback!");
        System.out.println("Você possui um MP3 mágico que pode tocar os álbuns da banda, mas antes você precisará encontrá-los e completar missões desafiadoras em cada cidade.");
        System.out.println("Explore os cenários, colete itens, e prepare-se para trazer a magia de volta ao palco.");
        System.out.println("Digite 'help' se precisar de ajuda com os comandos.");
        System.out.println();
        printLocationInfo(); // Exibe as informações da localização inicial
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("Não entendi o que você quer dizer...");
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
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
        else if (commandWord.equals("play")) {
            String album = command.getSecondWord();
            if (album == null) {
                System.out.println("Play o quê?");
                return false;
            }
            playAlbum(album);
        }
        else if (commandWord.equals("back")) {
            back();
        }else if (commandWord.equals("take")) {
            takeItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }else if (commandWord.equals("items")) {
            printItems();
        }else if (hasZayn && hasLouis && hasNiall && hasLiam && hasHarry) {
            System.out.println("Parabéns! Você reuniu todos os membros da One Direction! O comeback está garantido! 🎉");
            return true; // Finaliza o jogo
        }

        return wantToQuit;
    }

    private void printItems() {
        HashMap<String, Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Você não está carregando nenhum item.");
        } else {
            int totalWeight = 0;
            System.out.println("Você está carregando os seguintes itens: ");
            for (Item item : inventory.values()) {
                System.out.println("- " + item.getDescription() + " (Peso: " + item.getWeight() + ")");
                totalWeight += item.getWeight();
            }
            System.out.println("Peso total: " + totalWeight);
        }
    }

    // implementations of user commands:
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Você parece um pouco perdido. Não se preocupe, estou aqui para ajudar!");
        System.out.println("Sua missão é reunir os membros da banda One Direction e trazê-los de volta para o grande comeback.");
        System.out.println("Use o MP3 mágico para viajar entre as cidades e procure pelos álbuns escondidos.");
        System.out.println();
        System.out.println("Os comandos que você pode usar são:");
        System.out.println("   go [direção] - Para ir para outra sala ou localização.");
        System.out.println("   quit - Para sair do jogo.");
        System.out.println("   help - Para ver esta mensagem de ajuda novamente.");
        System.out.println("   look - Para observar melhor o ambiente ao seu redor.");
        System.out.println("   play [álbum] - Para tocar um álbum no MP3 mágico.");
        System.out.println("   back - Para retornar ao lugar anterior.");
        System.out.println("   take [item] - Para pegar um item do cenário.");
        System.out.println("   drop [item] - Para largar um item que você não precisa mais.");
        System.out.println("   items - Para ver os itens que você está carregando.");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Ir pra onde?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("Não tem saída!");
        } else {
            previousRooms.push(player.getCurrentRoom()); // Empilha a sala atual antes de mudar
            player.setCurrentRoom(nextRoom); // Atualiza a sala atual do jogador
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
            System.out.println("Sair de onde?!");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void playAlbum(String album) {
        if (album.equals("Take Me Home") && hasTakeMeHome) {
            System.out.println("Você toca 'Take Me Home' e é teletransportado para a fazenda do Zayn!");
            currentRoom = zaynFarm;
        } else if (album.equals("Midnight Memories") && hasMidnightMemories) {
            if (hasZayn) { // Precisa ter encontrado Zayn
                System.out.println("Você toca 'Midnight Memories' e vai para Doncaster!");
                currentRoom = louisHome;
            } else {
                System.out.println("Você precisa encontrar Zayn primeiro!");
            }
        } else if (album.equals("Four") && hasFour) {
            if (hasLouis) { // Precisa ter encontrado Louis
                System.out.println("Você toca 'Four' e vai para o parque de Dublin!");
                currentRoom = dublinPark;
            } else {
                System.out.println("Você precisa encontrar Louis primeiro!");
            }
        } else if (album.equals("Up All Night") && hasUpAllNight) {
            if (hasNiall) { // Precisa ter encontrado Niall
                System.out.println("Você toca 'Up All Night' e vai para a casa de Liam!");
                currentRoom = liamHome;
            } else {
                System.out.println("Você precisa encontrar Niall primeiro!");
            }
        } else if (album.equals("Made In The A.M.") && hasMitam) {
            if (hasLiam) { // Precisa ter encontrado Liam
                System.out.println("Você toca 'Made In The A.M.' e vai para a festa do Harry!");
                currentRoom = harryParty;
            } else {
                System.out.println("Você precisa encontrar Liam primeiro!");
            }
        } else {
            System.out.println("Você não pode tocar esse álbum agora. Verifique se você tem o álbum e o membro necessário.");
        }
    }

    private void back() {
        if (previousRooms.isEmpty()) {
            System.out.println("Você não pode voltar para trás. Não tem local anterior..");
        } else {
            player.setCurrentRoom(previousRooms.pop()); // Desempilha e atualiza a sala do jogador
            printLocationInfo();
        }
    }

    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Pegar o que?");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = player.getCurrentRoom().getItem(itemName);

        if (item == null) {
            System.out.println("Não tem " + itemName + " nesse lugar.");
        } else {
            // Verifica se o item é um álbum ou membro
            switch (item.getDescription()) {
                case "Álbum Take Me Home - One Direction":
                    hasTakeMeHome = true;
                    break;
                case "Álbum Midnight Memories - One Direction":
                    hasMidnightMemories = true;
                    break;
                case "Álbum Four - One Direction":
                    hasFour = true;
                    break;
                case "Álbum Up All Night - One Direction":
                    hasUpAllNight = true;
                    break;
                case "Álbum Made In the AM - One Direction":
                    hasMitam = true;
                    break;
                case "Zayn":
                    hasZayn = true;
                    System.out.println("Você encontrou Zayn! VAS HAPENNIN!");
                    break;
                case "Louis":
                    hasLouis = true;
                    System.out.println("Você encontrou Louis! OIOIIIIIIIIIIIIIIIII!");
                    break;
                case "Niall":
                    hasNiall = true;
                    System.out.println("Você encontrou Niall! LALALALALALALALA VAMOS!!");
                    break;
                case "Liam":
                    hasLiam = true;
                    System.out.println("Você encontrou Liam! Cuidado com as colheres!");
                    break;
                case "Harry":
                    hasHarry = true;
                    System.out.println("Você encontrou Harry! A BANDA ESTÁ COMPLETA!");
                    break;
            }

            player.addItemToInventory(item);
            player.getCurrentRoom().removeItem(itemName);
            System.out.println("Você pegou " + item.getDescription());
        }
    }

    private void dropItem(Command command) {
        String itemName = command.getSecondWord();
        if (itemName == null) {
            System.out.println("Dropar o que?");
            return;
        }

        // Verifica se o jogador tem o item no inventário
        if (player.hasItem(itemName)) {
            Item item = player.getInventory().get(itemName);
            currentRoom.addItem(itemName, item); // Adiciona o item de volta à sala
            player.removeItemFromInventory(itemName); // Remove o item do inventário
            System.out.println("Você dropou " + item.getDescription());
        } else {
            System.out.println("Você não tem " + itemName + ".");
        }
    }

}
