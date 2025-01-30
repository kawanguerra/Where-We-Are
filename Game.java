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

    private boolean hasTakeMeHome = false;
    private boolean hasMidnightMemories = false;
    private boolean hasFour = false;
    private boolean hasUpAllNight = false;
    private boolean hasMitam = false;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        previousRooms = new Stack<>();
        player = new Player("Você", new Room("no seu quarto"));
        createRooms(); // Agora `createRooms()` pode acessar `player` sem erro
    }

    private void createRooms() {
        // Quarto do protagonista
        Room bedroom = new Room("Você está no seu quarto... que bagunça!");

        // Fazenda do Zayn
        zaynFarm = new Room("Você está fazenda do Zayn");
        Room barn = new Room("Você está no celeiro da fazenda");
        Room field = new Room("Você está no campo ao lado da fazenda");
        Room house = new Room("Você está dentro da casa do Zayn");
        Room zaynBedroom = new Room("Você está no quarto do Zayn"); 

        // Casa de Louis em Doncaster
        louisHome = new Room("Você está na casa de Louis em Doncaster");
        Room livingRoom = new Room("Você está na sala de estar de Louis");
        Room kitchen = new Room("Você está na cozinha da casa de Louis");
        Room backyard = new Room("Você está no quintal da casa de Louis");
        Room bathroom = new Room("Você está no banheiro do Louis");

        // Parque em Dublin com Niall
        dublinPark = new Room("Você está em um parque em Dublin - Irlanda");
        Room lake = new Room("Você está perto do lago do parque");
        Room forest = new Room("Você está na trilha da floresta do parque");
        Room playground = new Room("Você está no playground do parque");
        Room cave = new Room("Você está em uma entrada escura entre as árvores. Parece assustador, mas você sente que algo importante está lá dentro");

        // Casa de Liam em Londres
        liamHome = new Room("Você está na casa de Liam em Londres");
        Room studio = new Room("Você está no estúdio de música do Liam");
        Room balcony = new Room("Você está na varanda da casa de Liam");
        Room garden = new Room("Você está no jardim da casa de Liam");
        Room library = new Room("Você está em uma sala silenciosa cheia de livros e partituras.");

        // Festa do Harry em LA
        harryParty = new Room("Em uma festa em Los Angeles você observa Harry Styles na pista de dança ao norte.");
        Room danceFloor = new Room("Você está na pista de dança da festa");
        Room bar = new Room("Você está no bar da festa");
        Room lounge = new Room("Você está no lounge VIP da festa");
        Room rooftop = new Room("Você está no terraço da festa. Ele é iluminado por luzes decorativas. De lá, você pode ver toda a cidade. Harry talvez tenha deixado algo aqui");

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
        Item upAllNight = new Item("álbum up all night", 5);
        Item takeMeHome = new Item("álbum take me home", 5);
        Item midnightMemories = new Item("álbum midnight memories", 5);
        Item four = new Item("álbum four", 5);
        Item mitam = new Item("álbum mitam", 5);

        //item do quarto
        Item poster = new Item("Pôster de One Direction", 5);

        // Adiciona os itens ao quarto
        bedroom.addItem("poster", poster);

        // Criação do item mochila
        Item mochila = new Item("Mochila", 1);  // A mochila tem peso 1

        // Adiciona a mochila ao quarto
        kitchen.addItem("mochila", mochila);

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

        bedroom.addItem("álbum take me home", takeMeHome);
        field.addItem("álbum up all night", upAllNight);
        cave.addItem("álbum mitam", mitam);
        library.addItem("álbum midnight memories", midnightMemories);
        backyard.addItem("álbum four", four);

        // Adiciona os itens necessários para interagir com o Zayn
        Item chaveiro = new Item("chaveiro", 1);
        Item chapeu = new Item("chapéu", 1);

        // Adiciona os itens à fazenda do Zayn
        zaynBedroom.addItem("chaveiro", chaveiro); // Chaveiro no celeiro
        barn.addItem("chapéu", chapeu);    // Chapéu na casa

        // Adiciona a pulseira VIP ao lounge
        Item pulseiraVIP = new Item("pulseira vip", 10);
        lounge.addItem("pulseira vip", pulseiraVIP);

        player.setCurrentRoom(bedroom);
    }

    public void play() 
    {            
        printWelcome();

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Obrigado por jogar :)"); 
    }

    private void printLocationInfo() {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    private void printWelcome() {
        System.out.println("Bem-vindo ao 'Where We Are'!");
        System.out.println("Uma aventura inspirada na história da banda One Direction!");
        System.out.println();
        System.out.println("Você começa sua jornada no seu quarto, onde algo inesperado aconteceu...");
        System.out.println("Um MP3 mágico apareceu misteriosamente! Ele contém músicas da banda e uma mensagem enigmática:");
        System.out.println("\"Os membros da banda desejam se reunir, mas eles precisam que alguém os ajudem a se reunir.\"");
        System.out.println();
        System.out.println("O MP3 mágico permitirá que você viaje para diferentes cidades associadas aos membros da banda.");
        System.out.println("Para ativar cada viagem, você precisará encontrar os álbuns escondidos e talvez completar missões específicas em cada cidade.");
        System.out.println("Lembre-se, você só poderá avançar para a próxima cidade após reunir o membro correspondente e o álbum!");
        System.out.println();
        System.out.println("Explore os cenários e prepare-se para reunir os membros da One Direction para o grande comeback!");
        System.out.println();
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
        }

        if (hasZayn && hasLouis && hasNiall && hasLiam && hasHarry) {
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
        // Normaliza o nome do álbum para comparação
        String normalizedAlbum = album.toLowerCase();

        // Verifica se o jogador tem o álbum no inventário
        if (player.hasItem("álbum " + normalizedAlbum)) {
            switch (normalizedAlbum) {
                case "take me home":
                    System.out.println("Você toca 'Take Me Home' e é teletransportado para a fazenda do Zayn!");
                    player.setCurrentRoom(zaynFarm);
                    printLocationInfo();
                    break;

                case "midnight memories":
                    if (hasLiam) { 
                        System.out.println("Você toca 'Midnight Memories' e vai para Doncaster!");
                        player.setCurrentRoom(louisHome);
                        printLocationInfo();
                    } else {
                        System.out.println("Você precisa encontrar Liam primeiro!");
                    }
                    break;

                case "four":
                    if (hasLouis) { // Precisa ter encontrado Louis
                        System.out.println("Você toca 'Four' e vai para o parque de Dublin!");
                        player.setCurrentRoom(dublinPark);
                        printLocationInfo();
                    } else {
                        System.out.println("Você precisa encontrar Louis primeiro!");
                    }
                    break;

                case "up all night":
                    if (hasZayn) { 
                        System.out.println("Você toca 'Up All Night' e vai para a casa de Liam!");
                        player.setCurrentRoom(liamHome);
                        printLocationInfo();
                    } else {
                        System.out.println("Você precisa encontrar Zayn primeiro!");
                    }
                    break;

                case "mitam":
                    if (hasNiall) { 
                        System.out.println("Você toca 'Made In The A.M.' e vai para a festa do Harry!");
                        player.setCurrentRoom(harryParty);
                        printLocationInfo();
                    } else {
                        System.out.println("Você precisa encontrar Niall primeiro!");
                    }
                    break;

                default:
                    System.out.println("Você não pode tocar esse álbum agora. Verifique se você tem o álbum e o membro necessário.");
                    break;
            }
        } else {
            System.out.println("Você não tem o álbum " + album + " no seu inventário.");
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

        String itemName = command.getSecondWord().toLowerCase();
        Item item = player.getCurrentRoom().getItem(itemName);

        if (item == null) {
            System.out.println("Não tem " + itemName + " nesse lugar.");
        } else {

            if (item.getDescription().equalsIgnoreCase("Mochila")) {
                player.increaseMaxWeight(20); // Aumenta o peso máximo em 20 unidades
            }

            if (item.getDescription().equalsIgnoreCase("Harry")) {
                // Verifica se o jogador tem a pulseira VIP
                if (player.hasItem("pulseira vip")) {
                    System.out.println("Você mostra a pulseira VIP para o segurança. Ele permite que você se aproxime do Harry!");
                    hasHarry = true;
                    System.out.println("Você encontrou Harry! A BANDA ESTÁ COMPLETA!");
                } else {
                    System.out.println("O segurança bloqueia seu caminho e diz: 'Você não pode entrar aqui sem uma pulseira VIP!'");
                    System.out.println("Talvez você precise encontrar uma pulseira VIP...");
                    return; // Impede que o Harry seja pego sem a pulseira VIP
                }
            }

            // Verifica se o item é o Zayn
            if (item.getDescription().equalsIgnoreCase("Zayn")) {
                // Verifica se o jogador tem os itens necessários
                if (player.hasItem("chaveiro") && player.hasItem("chapéu")) {
                    System.out.println("Você mostra os itens que coletou na fazenda. Zayn fica feliz e se junta a você!!");
                    player.addItemToInventory(item); // Adiciona o Zayn ao inventário
                    player.getCurrentRoom().removeItem(itemName); // Remove o Zayn da sala
                    hasZayn = true;
                    System.out.println("Você encontrou Zayn! VAS HAPENNIN!");
                    player.removeItemFromInventory("chaveiro");
                    player.removeItemFromInventory("chapéu");
                } else {
                    System.out.println("Zayn se recusa a sair até achar o chapéu dele e seu chaveiro. Procure pela fazenda!!");
                    if (!player.hasItem("chaveiro")) {
                        System.out.println("- Você ainda não tem o Chaveiro.");
                    }
                    if (!player.hasItem("chapéu")) {
                        System.out.println("- Você ainda não tem o Chapéu.");
                    }
                    return; // Impede que o Zayn seja pego sem os itens necessários
                }

            }else
            // Verifica se o item é um álbum ou membro
                switch (item.getDescription()) {
                    case "Álbum Take Me Home":
                        hasTakeMeHome = true;
                        break;
                    case "Álbum Midnight Memories":
                        hasMidnightMemories = true;
                        break;
                    case "Álbum Four":
                        hasFour = true;
                        break;
                    case "Álbum Up All Night":
                        hasUpAllNight = true;
                        break;
                    case "Álbum Mitam":
                        hasMitam = true;
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
